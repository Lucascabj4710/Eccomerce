package com.eccomerce.userEntity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    public UserDetailService(UserEntityRepository userEntityRepository){
        this.userEntityRepository = userEntityRepository;
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
}
