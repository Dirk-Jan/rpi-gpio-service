package nl.djja.rpi.gpioservice.rest.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.djja.rpi.gpioservice.rest.util.ErrorResponse;

import javax.ws.rs.core.Response;

public class RESTFactory {

    public static ObjectMapper getObjectMapper() { return new ObjectMapper(); }

    private static ErrorResponse getErrorResponseObj(int statusCode, String errorMessage) {
        return new ErrorResponse(statusCode, errorMessage);
    }

    private static String toJson(Object object) throws JsonProcessingException {
        String result;
        result = getObjectMapper().writeValueAsString(object);
        return result;
    }

    public static Response getResponse(int statusCode, Object entity) {
        String json;

        try {
            json = toJson(entity);
        } catch (JsonProcessingException e) {
            System.out.println("Creating response with status 500; Cannot convert to JSON.");
            return Response.status(500).build();
        }

        System.out.println("Creating response with status " + statusCode);
        return Response.status(statusCode).entity(json).build();
    }

    public static Response getErrorResponse(int statusCode, String errorMessage) {
        return getResponse(statusCode, getErrorResponseObj(statusCode, errorMessage));
    }

    public static Response getGenericErrorResponse() {
        return getErrorResponse(500, "Something unexpectedly went wrong.");
    }

    public static Response get204Response(){
        System.out.println("Creating response with status 204");
        return Response.status(204).build();
    }
}
