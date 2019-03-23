package nl.djja.rpi.gpioservice.iopincontrol;

import nl.djja.rpi.gpioservice.exceptions.IOPinManipulationException;

public interface IOPinControl {
    int getPinNumber();
    boolean isOpen();

    void open() throws IOPinManipulationException;
    void close() throws IOPinManipulationException;
    void setDirection(IOPinDirection direction) throws IOPinManipulationException;
    IOPinState read() throws IOPinManipulationException;
    void write(IOPinState state) throws IOPinManipulationException;
}
