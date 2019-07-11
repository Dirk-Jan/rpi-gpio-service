package nl.djja.rpi.gpioservice.factories;

import nl.djja.rpi.gpioservice.iopincontrol.FictionalIOPin;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinControl;
import nl.djja.rpi.gpioservice.iopincontrol.RPIGPIOPin;

public class GPIOFactory {
    public static IOPinControl getIOPinControl(int pinNumber) {
//        return new RPIGPIOPin(pinNumber);
        return new FictionalIOPin(pinNumber);
    }
}
