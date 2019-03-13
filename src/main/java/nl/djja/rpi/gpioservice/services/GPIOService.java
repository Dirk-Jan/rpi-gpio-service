package nl.djja.rpi.gpioservice.services;

import nl.djja.rpi.gpioservice.iopincontrol.IOPinDirection;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinState;

public interface GPIOService {

    void openPin(int pinNumber);
    void closePin(int pinNumber);
    void setDirection(int pinNumber, IOPinDirection direction);
    IOPinState read(int pinNumber);
    void write(int pinNumber, IOPinState state);
}
