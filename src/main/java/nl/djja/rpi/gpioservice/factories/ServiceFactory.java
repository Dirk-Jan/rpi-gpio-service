package nl.djja.rpi.gpioservice.factories;

import nl.djja.rpi.gpioservice.services.GPIOService;
import nl.djja.rpi.gpioservice.services.GPIOServiceImpl;

public class ServiceFactory {
    public static GPIOService getGPIOService() {
        return new GPIOServiceImpl();
    }
}
