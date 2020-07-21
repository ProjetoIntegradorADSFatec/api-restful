package api.restful.model.views;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class UserCache {
    @JsonView(Views.Public.class)
    private String username;

    @JsonView(Views.Internal.class)
    private String password;

    @JsonView(Views.Public.class)
    private List<String> role;

    public UserCache(String username, String password, List<String> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRole() {
        return this.role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}