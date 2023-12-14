package com.example.app.login_and_registration.filter;

import com.example.app.login_and_registration.model.User;
import com.example.app.login_and_registration.service.JwtService;
import com.example.app.login_and_registration.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtService jwtService;
    private final UserService userService;

    public JWTAuthorizationFilter(AuthenticationManager authManager,JwtService jwtService,UserService userService) {
        super(authManager);
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(jwtService.getHeaderName());
        if (header == null || !header.startsWith(jwtService.getHeaderPrefix())) {
            // Authorization header not found continue along the responsibility chain.
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(header);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        System.out.println(authenticationToken.toString());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        String token = header.replace(jwtService.getHeaderPrefix(),"");
        if (jwtService.isTokenExpired(token)) {
            return null;
        }
        String email = jwtService.extractEmail(token);
        if(email == null) {
            return null;
        }
        Optional<User> user = userService.findByEmail(email);
        return user.map(value -> new UsernamePasswordAuthenticationToken(
                        value.getEmail(),
                        value.getPassword(),
                        null))
                .orElse(null);
    }
}