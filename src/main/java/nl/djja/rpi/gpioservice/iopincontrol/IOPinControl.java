package nl.djja.rpi.gpioservice.iopincontrol;

public interface IOPinControl {
    int getPinNumber();
    boolean isOpen();

    void open();
    void close();
    void setDirection(IOPinDirection direction);
    IOPinState read();
    void write(IOPinState state);
}
