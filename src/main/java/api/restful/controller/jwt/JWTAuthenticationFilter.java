package api.restful.controller.jwt;

import com.auth0.jwt.JWT;
import api.restful.model.user.AuthUser;
import api.restful.model.views.UserCache;
import api.restful.model.user.Authorization;
import api.restful.model.user.AuthorizationRepository;
import api.restful.model.views.ResponseToken;
import api.restful.services.AuthorizationServiceImpl;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collection;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static api.restful.controller.security.SecurityConstants.EXPIRATION_TIME;
import static api.restful.controller.security.SecurityConstants.HEADER_STRING;
import static api.restful.controller.security.SecurityConstants.SECRET;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            AuthUser creds = new ObjectMapper().readValue(req.getInputStream(), AuthUser.class);
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
            updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(),
                    updatedAuthorities
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        List<Authorization> authorizations = (List<Authorization>) auth.getAuthorities();
        List<String> aut = new ArrayList<String>();
        for (Authorization autho : authorizations) {
            aut.add(autho.getAuthority());
        }
        String username = ((User) auth.getPrincipal()).getUsername();
        String password = ((User) auth.getPrincipal()).getPassword();
        UserCache user = new UserCache(username, password, aut);
        String jsonUser = new Gson().toJson(user);
        String token = JWT.create()
            .withSubject(jsonUser)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(HMAC512(SECRET.getBytes()));
        ResponseToken response = new ResponseToken(200, token, user, "Use this token to API CRUD options");
        String jsonString = new Gson().toJson(response);
        PrintWriter out = res.getWriter();
        res.addHeader(HEADER_STRING, token);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}