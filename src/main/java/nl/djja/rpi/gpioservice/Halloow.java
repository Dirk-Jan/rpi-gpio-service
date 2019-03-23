package nl.djja.rpi.gpioservice;

import nl.djja.rpi.gpioservice.exceptions.IOPinManipulationException;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinState;
import nl.djja.rpi.gpioservice.services.GPIOService;
import nl.djja.rpi.gpioservice.services.GPIOServiceImpl;

public class Halloow {

    public static void main(String[] args) {
        GPIOService gpioService = new GPIOServiceImpl();

        int count = 0;
        while (count < 15) {
            try {
                gpioService.write(26, IOPinState.HIGH);
            } catch (IOPinManipulationException e) {
                System.out.println(e.getMessage());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                gpioService.write(26, IOPinState.LOW);
            } catch (IOPinManipulationException e) {
                System.out.println(e.getMessage());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            count++;
        }
    }
}
