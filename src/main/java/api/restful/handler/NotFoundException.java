package api.restful.handler;

public class NotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NotFoundException(String param) {
        super("Data not found for " + param);
    }
}