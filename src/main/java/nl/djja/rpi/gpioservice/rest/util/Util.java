package nl.djja.rpi.gpioservice.rest.util;

import nl.djja.rpi.gpioservice.rest.util.exceptions.StringIsNotANumberException;

public class Util {
    public static Long stringToLong(String value) throws StringIsNotANumberException {
        Long result;
        try {
            result = Long.parseLong(value);
        } catch (Exception ex) {
            throw new StringIsNotANumberException();
        }
        return result;
    }

    public static int stringToInteger(String value) throws StringIsNotANumberException {
        int result;
        try {
            result = Integer.parseInt(value);
        } catch (Exception ex) {
            throw new StringIsNotANumberException();
        }
        return result;
    }
}
