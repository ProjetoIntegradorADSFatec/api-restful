package api.restful.model.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {
    @Query(value = "select user from Authorization aut inner join aut.user user order by user.id")
    List<AuthUser> listAll();

    @Query(value = "select user from Authorization aut inner join aut.user user where user.id = :id order by user.id")
    List<AuthUser> find(Long id);

    @Query(value = "select user from Authorization aut inner join aut.user user where user.username = :username order by user.id")
    List<AuthUser> findUser(String username);
}