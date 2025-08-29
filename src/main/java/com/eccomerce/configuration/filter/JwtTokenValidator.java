package com.eccomerce.configuration.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.eccomerce.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {

            token = token.substring(7);

            DecodedJWT decodedJWT = jwtUtils.decodeJWT(token);
            String roles = jwtUtils.getSpecificClaim(decodedJWT, "roles").asString();
            String username = jwtUtils.extractUsername(decodedJWT);
            Collection<? extends GrantedAuthority> rolesAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, rolesAuthorities);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

        }

        filterChain.doFilter(request,response);

    }
}
