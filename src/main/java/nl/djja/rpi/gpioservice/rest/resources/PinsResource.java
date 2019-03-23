package nl.djja.rpi.gpioservice.rest.resources;

import nl.djja.rpi.gpioservice.factories.ServiceFactory;
import nl.djja.rpi.gpioservice.dtos.IOPinStateDTO;
import nl.djja.rpi.gpioservice.exceptions.IOPinManipulationException;
import nl.djja.rpi.gpioservice.iopincontrol.IOPinState;
import nl.djja.rpi.gpioservice.rest.factory.RESTFactory;
import nl.djja.rpi.gpioservice.rest.util.Util;
import nl.djja.rpi.gpioservice.rest.util.exceptions.StringIsNotANumberException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

// TODO Define interface for other protocols

@Path("pins")
public class PinsResource {

    @GET
    @Path("{pinNumber}/state")
    @Produces("application/json")
    public Response getPinValue(@PathParam("pinNumber") String pinNumberAsString) {
        int pinNumber;
        try {
            pinNumber = Util.stringToInteger(pinNumberAsString);
        } catch (StringIsNotANumberException e) {
            return RESTFactory.getErrorResponse(400, "Provided path parameter pinNumber is not a number.");
        }

        try {
            IOPinState pinState = ServiceFactory.getGPIOService().read(pinNumber);
            IOPinStateDTO dto = new IOPinStateDTO();
            dto.pinNumber = pinNumber;
            dto.pinState = pinState;

            return RESTFactory.getResponse(200, dto);
        } catch (IOPinManipulationException e) {
            return RESTFactory.getErrorResponse(500, e.getMessage());
        } catch (Exception e) {
            return RESTFactory.getGenericErrorResponse();
        }
    }

    @POST
    @Path("{pinNumber}/state")
    @Consumes("application/json")
    @Produces("application/json")
    public Response setPinValue(@PathParam("pinNumber") String pinNumberAsString, String ioPinStateDTOJSON) {
        int pinNumber;
        try {
            pinNumber = Util.stringToInteger(pinNumberAsString);
        } catch (StringIsNotANumberException e) {
            return RESTFactory.getErrorResponse(400, "Provided path parameter pinNumberAsString is not a number.");
        }

        IOPinStateDTO ioPinStateDTO;
        try {
            ioPinStateDTO = RESTFactory.getObjectMapper().readValue(ioPinStateDTOJSON, IOPinStateDTO.class);
        } catch (IOException e) {
            return RESTFactory.getErrorResponse(422, "Unprocessable Entity");
        }

        try {
            ServiceFactory.getGPIOService().write(pinNumber, ioPinStateDTO.pinState);
            return RESTFactory.get204Response();
        } catch (IOPinManipulationException e) {
            return RESTFactory.getErrorResponse(500, e.getMessage());
        } catch (Exception e) {
            return RESTFactory.getGenericErrorResponse();
        }
    }
}
