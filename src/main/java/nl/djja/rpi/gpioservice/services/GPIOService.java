package nl.djja.rpi.gpioservice.services;

import nl.djja.rpi.gpioservice.exceptions.IOPinManipulationException;
import nl.djja.rpi.gpioservice.exceptions.IOPinNotRegisteredException;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinDirection;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinState;

public interface GPIOService {

    void openPin(int pinNumber) throws IOPinManipulationException;
    void closePin(int pinNumber) throws IOPinManipulationException, IOPinNotRegisteredException;
    void setDirection(int pinNumber, IOPinDirection direction) throws IOPinManipulationException, IOPinNotRegisteredException;
    IOPinState read(int pinNumber) throws IOPinManipulationException;
    void write(int pinNumber, IOPinState state) throws IOPinManipulationException;
}
