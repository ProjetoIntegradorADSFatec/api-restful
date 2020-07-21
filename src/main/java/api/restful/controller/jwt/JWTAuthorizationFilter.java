package api.restful.controller.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import api.restful.model.user.AuthUser;
import api.restful.model.user.Authorization;
import api.restful.model.views.UserCache;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import static api.restful.controller.security.SecurityConstants.HEADER_STRING;
import static api.restful.controller.security.SecurityConstants.SECRET;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        this.getAuthentication(req);
        chain.doFilter(req, res);
    }

    private void getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String jsonUser = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                    .verify(token)
                        .getSubject();
            UserCache user = new Gson().fromJson(jsonUser, UserCache.class);
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
            for (String role : user.getRole()) {
                updatedAuthorities.add(new SimpleGrantedAuthority(role));
            }
            if (user != null) {
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), updatedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }
}