package nl.djja.rpi.gpioservice.factories;

import nl.djja.rpi.gpioservice.iopincontrol.IOPinControl;
import nl.djja.rpi.gpioservice.iopincontrol.RPIGPIOPin;

public class GPIOFactory {
    public static IOPinControl getIOPinControl(int pinNumber) {
        return new RPIGPIOPin(pinNumber);
    }
}
