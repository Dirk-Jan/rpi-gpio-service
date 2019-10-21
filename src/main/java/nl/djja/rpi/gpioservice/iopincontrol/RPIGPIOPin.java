package nl.djja.rpi.gpioservice.iopincontrol;

import nl.djja.rpi.gpioservice.exceptions.IOPinManipulationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RPIGPIOPin implements IOPinControl {
    private static final String gpioRootFolder = "/sys/class/gpio/";

    private int pinNumber;

    public RPIGPIOPin(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    @Override
    public int getPinNumber() {
        return pinNumber;
    }

    @Override
    public boolean isOpen() {
        File pin = new File(gpioRootFolder + "gpio" + pinNumber);
        return pin.exists() && pin.isDirectory();
    }

    public void open() throws IOPinManipulationException {
        if (!isOpen()) {
            try {
                writeInteger(gpioRootFolder + "export", pinNumber);
            } catch (IOPinManipulationException e) {
                throw new IOPinManipulationException("Cannot open pin " + pinNumber + "; " + e.getMessage());
            }

            try {
                Thread.sleep(500);              // FIXME This does not feel right; Perhaps wait the direction file in the pin folder exists with a timeout
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Per default the direction is to output
        setDirection(IOPinDirection.OUTPUT);                // FIXME This is bad design, the services relies on it setting the output, but other implementations of IOPinControl might not do that.
    }

    public void close() throws IOPinManipulationException {
        if (isOpen()) {
            try {
                writeInteger(gpioRootFolder + "unexport", pinNumber);
            } catch(IOPinManipulationException e) {
                throw new IOPinManipulationException("Cannot close pin " + pinNumber + "; " + e.getMessage());
            }
        }
    }

    public void setDirection(IOPinDirection direction) throws IOPinManipulationException {
        writeString(getGPIOPinDirectionPath(), direction == IOPinDirection.OUTPUT ? "out" : "in");
    }

    public IOPinState read() throws IOPinManipulationException {
        IOPinState state = null;

        try (
            FileInputStream file = new FileInputStream(getGPIOPinValuePath());
        ) {
            state = ((char)file.read() == '1' ? IOPinState.HIGH : IOPinState.LOW);
        } catch (IOException e) {
            throw new IOPinManipulationException("Cannot read value from path '" + getGPIOPinValuePath() + "'");
        }

        return state;
    }

    public void write(IOPinState state) throws IOPinManipulationException {
        writeInteger(getGPIOPinValuePath(), state == IOPinState.LOW ? 0 : 1);
    }

    private void writeInteger(String path, int value) throws IOPinManipulationException {
        writeString(path, Integer.toString(value));
    }

    private void writeString(String path, String value) throws IOPinManipulationException {
        try (
            FileOutputStream file = new FileOutputStream(path)
        ) {
            file.write(value.getBytes());
        } catch (IOException e) {
            throw new IOPinManipulationException("Cannot write value '" + value + "' to '" + path + "'");
        }
    }

    private String getGPIOPinValuePath(){
        return getGPIOPinValuePath(pinNumber);
    }

    private String getGPIOPinDirectionPath() {
        return getGPIOPinDirectionPath(pinNumber);
    }

    private String getGPIOPinValuePath(int pinNumber) {
        return gpioRootFolder + "gpio" + pinNumber + "/value";
    }

    private String getGPIOPinDirectionPath(int pinNumber) {
        return gpioRootFolder + "gpio" + pinNumber + "/direction";
    }
}
