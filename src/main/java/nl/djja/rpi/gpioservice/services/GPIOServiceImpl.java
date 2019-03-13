package nl.djja.rpi.gpioservice.services;

import nl.djja.rpi.gpioservice.factories.GPIOFactory;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinControl;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinDirection;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinState;

import java.util.HashMap;

public class GPIOServiceImpl implements GPIOService {
    private HashMap<Integer, IOPinControl> ioPinControls = new HashMap<>();

    @Override
    public void openPin(int pinNumber) {    // Why do this, happens automatically when reading or writing
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
    public void closePin(int pinNumber) {
        IOPinControl ioPinControl = getIOPinControlByPinNumber(pinNumber);
        if (ioPinControl != null) {
            if (ioPinControl.isOpen()) {
                ioPinControl.close();
            }
        } else {
            // TODO Throw error
        }
    }

    @Override
    public void setDirection(int pinNumber, IOPinDirection direction) {
        IOPinControl ioPinControl = getIOPinControlByPinNumber(pinNumber);
        if (ioPinControl != null) {
            ioPinControl.setDirection(direction);
        } else {
            // TODO Throw error
        }
    }

    @Override
    public IOPinState read(int pinNumber) {
        IOPinControl ioPinControl = getIOPinControlByPinNumber(pinNumber);
        if (ioPinControl == null) {
            ioPinControl = openNewIOPinControl(pinNumber);
            ioPinControls.put(pinNumber, ioPinControl);
        }
        return ioPinControl.read();
    }

    @Override
    public void write(int pinNumber, IOPinState state) {
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

    private IOPinControl openNewIOPinControl(int pinNumber) {
        IOPinControl ioPinControl = GPIOFactory.getIOPinControl(pinNumber);
        ioPinControl.open();
        return ioPinControl;
    }
}
