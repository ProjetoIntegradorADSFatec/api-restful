package api.restful.handler;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String param) {
        super("Data not found for " + param);
    }
}