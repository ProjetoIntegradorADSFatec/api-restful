package api.restful.model.views;

public class ResponseToken {
    private int code;
    private String token;
    private String description;

    public ResponseToken() { }

    public ResponseToken(int code, String token, String description) {
        this.code = code;
        this.token = token;
        this.description = description;
    }

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}