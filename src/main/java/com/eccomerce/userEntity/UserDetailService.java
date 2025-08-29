package com.eccomerce.userEntity;

import com.eccomerce.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserDetailService(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils){
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        UserEntity user = userEntityRepository.findUserEntityByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Error el usuario " + username + " no existe"));

        user.getRoleEntities().forEach(roleEntity -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(roleEntity.getRoleEnum().name()))));


        return new User(user.getUsername(),
                user.getPassword(),
                user.getIsEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                authorities);
    }


    public Map<String, String> login(UserEntityDto userEntityDto){

        String username = userEntityDto.getUsername();
        String password = userEntityDto.getPassword();

        Authentication authentication = authentication(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.createToken(authentication);

        return Map.of("token", token);
    }

    public Authentication authentication(String username, String password){

        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Nombre de usuario o contraseña invalidos"));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

}
