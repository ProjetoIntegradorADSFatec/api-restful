package api.restful.model.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import api.restful.model.views.Views;

@Entity
@Table(name = "users")
public class AuthUser {
    @Id
    @JsonView(Views.Internal.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonView(Views.Public.class)
    private String username;

    @JsonView(Views.Internal.class)
    private String password;

    @JsonView(Views.Public.class)
    private Boolean admin;

    public long getId() {
        return this.id;
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

    public Boolean isAdmin() {
        return this.admin;
    }

    public void updateAdminState(Boolean admin) {
        this.admin = admin;
    }
}