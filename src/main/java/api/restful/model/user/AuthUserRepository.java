package api.restful.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    @Query(value = "select user from AuthUser user")
    List<AuthUser> listUsers();

    AuthUser findByUsername(String username);
}