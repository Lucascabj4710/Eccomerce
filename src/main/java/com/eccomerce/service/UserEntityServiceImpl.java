package com.eccomerce.service;

import com.eccomerce.persistence.dto.request.UserEntityDto;
import com.eccomerce.persistence.entity.RoleEntity;
import com.eccomerce.persistence.entity.UserEntity;
import com.eccomerce.persistence.repository.RoleEntityRepository;
import com.eccomerce.persistence.repository.UserEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserEntityServiceImpl implements UserEntityService{

    private final UserEntityRepository userEntityRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, RoleEntityRepository roleEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.roleEntityRepository = roleEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> createUserEntity(UserEntityDto userEntityDto) {

        RoleEntity roleEntity = roleEntityRepository.findById(1L)
                .orElseThrow(()-> new NoSuchElementException("Error el rol no existe"));

        String password = passwordEncoder.encode(userEntityDto.getPassword());

        UserEntity user = UserEntity.builder()
                .roleEntities(Set.of(roleEntity))
                .isEnabled(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .password(password)
                .username(userEntityDto.getUsername())
                .build();

        userEntityRepository.save(user);

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateUserEntity(Long id, UserEntityDto userEntityDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteUserEntity(Long id) {
        return null;
    }
}
