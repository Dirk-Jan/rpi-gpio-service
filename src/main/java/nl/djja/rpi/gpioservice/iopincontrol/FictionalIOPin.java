package nl.djja.rpi.gpioservice.iopincontrol;

public class FictionalIOPin implements IOPinControl {
    private int pinNumber;
    private IOPinState state = IOPinState.LOW;

    public FictionalIOPin(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    @Override
    public int getPinNumber() {
        return pinNumber;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    public void open() {

    }

    public void close() {

    }

    public void setDirection(IOPinDirection direction) {

    }

    public IOPinState read() {
        return state;
    }

    public void write(IOPinState state) {
        this.state = state;
    }
}
