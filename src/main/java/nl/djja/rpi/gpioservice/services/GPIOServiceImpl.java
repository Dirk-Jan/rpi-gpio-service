package nl.djja.rpi.gpioservice.services;

import nl.djja.rpi.gpioservice.exceptions.IOPinManipulationException;
import nl.djja.rpi.gpioservice.exceptions.IOPinNotRegisteredException;
import nl.djja.rpi.gpioservice.factories.GPIOFactory;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinControl;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinDirection;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinState;

import java.util.HashMap;

public class GPIOServiceImpl implements GPIOService {
    private static HashMap<Integer, IOPinControl> ioPinControls = new HashMap<>();

    @Override
    public void openPin(int pinNumber) throws IOPinManipulationException {    // Why do this, happens automatically when reading or writing
        IOPinControl ioPinControl = getIOPinControlByPinNumber(pinNumber);
        if (ioPinControl == null) {
            ioPinControl = openNewIOPinControl(pinNumber);
            ioPinControls.put(pinNumber, ioPinControl);
        } else {
            if (!ioPinControl.isOpen()) {
                ioPinControl.open();
            }
        }
    }

    @Override
    public void closePin(int pinNumber) throws IOPinManipulationException, IOPinNotRegisteredException { // Perhaps you shouldn't load every pin in the memory but read directly from the linux file system
        IOPinControl ioPinControl = getIOPinControlByPinNumber(pinNumber);                                  // Perhaps add functionality to sync the memory with the file systme?
        if (ioPinControl != null) {                                                                         // Maybe it'' useful to keep it in memory to prevent concurrency issues
            if (ioPinControl.isOpen()) {
                ioPinControl.close();
                ioPinControls.remove(ioPinControl.getPinNumber());
            }
        } else {
            throw new IOPinNotRegisteredException(pinNumber);
        }
    }

    @Override
    public void setDirection(int pinNumber, IOPinDirection direction) throws IOPinManipulationException, IOPinNotRegisteredException {
        IOPinControl ioPinControl = getIOPinControlByPinNumber(pinNumber);
        if (ioPinControl != null) {
            ioPinControl.setDirection(direction);
        } else {
            throw new IOPinNotRegisteredException(pinNumber);
        }
    }

    @Override
    public IOPinState read(int pinNumber) throws IOPinManipulationException {
        IOPinControl ioPinControl = getIOPinControlByPinNumber(pinNumber);
        if (ioPinControl == null) {
            ioPinControl = openNewIOPinControl(pinNumber);
            ioPinControls.put(pinNumber, ioPinControl);
        }
        return ioPinControl.read();
    }

    @Override
    public void write(int pinNumber, IOPinState state) throws IOPinManipulationException {
        IOPinControl ioPinControl = getIOPinControlByPinNumber(pinNumber);
        if (ioPinControl == null) {
            ioPinControl = openNewIOPinControl(pinNumber);
            ioPinControls.put(pinNumber, ioPinControl);
        }
        ioPinControl.write(state);
    }

    private IOPinControl getIOPinControlByPinNumber(int pinNumber) {
        return ioPinControls.get(pinNumber);
    }

    private IOPinControl openNewIOPinControl(int pinNumber) throws IOPinManipulationException {
        IOPinControl ioPinControl = GPIOFactory.getIOPinControl(pinNumber);
        ioPinControl.open();
        return ioPinControl;
    }
}
