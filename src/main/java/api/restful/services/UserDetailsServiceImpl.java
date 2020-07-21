package api.restful.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.restful.model.user.AuthUser;
import api.restful.model.user.AuthUserRepository;
import api.restful.model.user.AuthorizationRepository;

import java.util.List;
import java.util.ArrayList;

import static java.util.Collections.emptyList;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private AuthUserRepository userRepository;
    private AuthorizationRepository authRepository;

    public UserDetailsServiceImpl(AuthUserRepository userRepository, AuthorizationRepository authRepository) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    public List<AuthUser> listAll() {
        try {
            return this.authRepository.listAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<AuthUser>();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = this.authRepository.findUser(username).get(0);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}