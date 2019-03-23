package nl.djja.rpi.gpioservice.exceptions;

public class IOPinNotRegisteredException extends Exception {
    public IOPinNotRegisteredException(int pinNumber) {
        super("Pin with id " + pinNumber + " could not be found");
    }
}
