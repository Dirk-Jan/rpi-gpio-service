package nl.djja.rpi.gpioservice;

import nl.djja.rpi.gpioservice.rest.RESTEmbeddedJetty;

public class ServiceInitializer {
    public static void main(String[] args) {
        RESTEmbeddedJetty restEmbeddedJetty = new RESTEmbeddedJetty();
        restEmbeddedJetty.run();
    }
}
