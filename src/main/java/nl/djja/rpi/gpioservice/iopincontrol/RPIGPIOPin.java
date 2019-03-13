package nl.djja.rpi.gpioservice.iopincontrol;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RPIGPIOPin implements IOPinControl {
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
        return false;
    }

    public void open() {
        writeInteger("/sys/class/gpio/export", pinNumber);
    }

    public void close() {
        writeInteger("/sys/class/gpio/unexport", pinNumber);
    }

    public void setDirection(IOPinDirection direction) {
        writeString("/sys/class/gpio/gpio" + pinNumber + "/direction", direction == IOPinDirection.OUTPUT ? "out" : "in");
    }

    public IOPinState read() {
        IOPinState state = null;

        try (
            FileInputStream file = new FileInputStream("/sys/class/gpio/gpio" + pinNumber + "/value");
        ) {
            state = (file.read() == 1 ? IOPinState.LOW : IOPinState.HIGH);
        } catch (IOException e) {   // TODO Throw errors!
//            Logger.printThrowable(e);
        }

        return state;
    }

    public void write(IOPinState state) {
        writeInteger("/sys/class/gpio/gpio" + pinNumber + "/value", state == IOPinState.HIGH ? 0 : 1);
    }

    private void writeInteger(String path, int value) {
        try (
                FileOutputStream file = new FileOutputStream(path)
        ) {
            file.write(value);
        } catch (IOException e) {
//            Logger.printThrowable(e);
        }
    }

    private void writeString(String path, String value) {
        try (
                FileOutputStream file = new FileOutputStream(path)
        ) {
            file.write(value.getBytes());
        } catch (IOException e) {
//            Logger.printThrowable(e);
        }
    }
}
