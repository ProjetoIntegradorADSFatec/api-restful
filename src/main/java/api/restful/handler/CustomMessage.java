package api.restful.handler;

public class CustomMessage {
    public int code;
    public String description;

    public CustomMessage() {}

    public CustomMessage(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}