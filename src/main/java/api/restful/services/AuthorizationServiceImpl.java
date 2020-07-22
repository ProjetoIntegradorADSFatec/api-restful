package api.restful.services;

import api.restful.model.user.*;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("UserService")
public class AuthorizationServiceImpl {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AuthorizationRepository authorizationRepository;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<AuthUser> listAll() {
        return this.authorizationRepository.listAll();
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public AuthUser findById(Long id) {
        return this.authorizationRepository.find(id).get(0);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public AuthUser findByUsername(String username) {
        return this.authorizationRepository.findUser(username.toLowerCase()).get(0);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AuthUser createUser(AuthUser user) {
        AuthUser usr = new AuthUser();
        usr.setUsername(user.getUsername());
        usr.setPassword(user.getPassword());
        usr.setAuthorizations(new ArrayList<Authorization>());
        this.authUserRepository.save(usr);
        for (Authorization aut : user.getAuthorizations()) {
            Authorization authorization = new Authorization();
            authorization.setAuthority(aut.getAuthority());
            authorization.setUser(usr);
            usr.getAuthorizations().add(authorization);
        }
        this.authorizationRepository.saveAll(usr.getAuthorizations());
        return user;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AuthUser updateUser(Long id, AuthUser user) {
        AuthUser usr = this.authorizationRepository.find(id).get(0);
        usr.setUsername(user.getUsername());
        usr.setPassword(user.getPassword());
        usr.setAuthorizations(new ArrayList<Authorization>());
        this.authUserRepository.save(usr);
        for (Authorization aut : user.getAuthorizations()) {
            Authorization authorization = new Authorization();
            authorization.setAuthority(aut.getAuthority());
            authorization.setUser(usr);
            usr.getAuthorizations().add(authorization);
        }
        this.authorizationRepository.saveAll(usr.getAuthorizations());
        return usr;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AuthUser deleteUser(Long id) {
        AuthUser usr = this.authorizationRepository.find(id).get(0);
        this.authUserRepository.delete(usr);
        for (Authorization aut : usr.getAuthorizations() ) {
            this.authorizationRepository.delete(aut);
        }
        return usr;
    }
}