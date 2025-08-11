package com.eccomerce.userEntity;

import com.eccomerce.role.RoleEntity;
import com.eccomerce.role.RoleEntityRepository;
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
    public UserEntity createUserEntity(String username, String password) {
        RoleEntity roleEntity = roleEntityRepository.findById(1L)
                .orElseThrow(()-> new NoSuchElementException("Error el rol no existe"));

        password = passwordEncoder.encode(password);

        return UserEntity.builder()
                .roleEntities(Set.of(roleEntity))
                .isEnabled(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .password(password)
                .username(username)
                .build();
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
