package nl.djja.rpi.gpioservice.rest;

import nl.djja.rpi.gpioservice.rest.filters.CorsFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RESTEmbeddedJetty implements Runnable {
    public static final int SERVER_PORT = 8181;
    public static final String RESOURCE_DIR = "nl.djja.rpi.gpioservice.rest.resources";

    public static Server configuredServer(int port) {
        ResourceConfig config = createResourceConfig();

        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");

        return server;
    }

    protected static ResourceConfig createResourceConfig() {
        final ResourceConfig rc = new ResourceConfig();
        rc.packages(RESOURCE_DIR);
//        rc.packages(false, RESOURCE_DIR)
        rc.register(new CorsFilter());
        return rc;
    }

    @Override
    public void run() {
        Server server = configuredServer(SERVER_PORT);

        try {
            server.start();
            server.join();

        } catch (Exception ex) {
            Logger.getLogger(RESTEmbeddedJetty.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            server.destroy();
        }
    }
}
