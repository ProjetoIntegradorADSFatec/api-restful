package api.restful.model.views;

public class ResponseToken {
    private int code;
    private String token;
	private String description;
	private UserCache user;

    public ResponseToken() { }

    public ResponseToken(int code, String token, UserCache user, String description) {
        this.code = code;
		this.token = token;
		this.user = user;
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

	public void setUser(UserCache user) {
		this.user = user;
	}

	public UserCache getUser() {
		return this.user;
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