package api.restful.services;

import java.util.List;

import api.restful.model.user.*;

public interface AuthorizationService {
    List<AuthUser> listUsers();
    List<AuthUser> findById(Long id);
    AuthUser createUser(AuthUser user);
    AuthUser updateUser(Long id, AuthUser user);
    AuthUser deleteUser(Long id);
}