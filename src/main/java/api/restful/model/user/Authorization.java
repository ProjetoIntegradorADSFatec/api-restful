package api.restful.model.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonView;
import api.restful.model.views.Views;

@Entity
@Table(name = "user_authorizations")
public class Authorization implements GrantedAuthority {

  private static final long serialVersionUID = 3078636239920155012L;

  @Id
  @JsonView(Views.Internal.class)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonView(Views.Public.class)
  private String type;

  @ManyToOne
  @JsonView(Views.Internal.class)
  @JoinColumn(name = "user_id")
  private AuthUser user;

  public Authorization() { }

  public Long getId() {
    return this.id;
  }

  @Override
  public String getAuthority() {
    return this.type;
  }

  public void setAuthority(String type) {
		this.type = type;
  }

  public void setUser(AuthUser user) {
    this.user = user;
  }

  public AuthUser getUser() {
		return this.user;
	}
}