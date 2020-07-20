package api.restful.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import api.restful.model.user.AuthUser;

public interface UserDetailsService {
    public List<AuthUser> listAll();
    public UserDetails loadUserByUsername(String username);
}