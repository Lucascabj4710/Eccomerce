package com.eccomerce.userEntity;

import org.springframework.http.ResponseEntity;

public interface UserEntityService {

    ResponseEntity<?> createUserEntity(UserEntityDto userEntityDto);
    UserEntity createUserEntity(String username, String password);
    ResponseEntity<?> updateUserEntity(Long id,UserEntityDto userEntityDto);
    ResponseEntity<?> deleteUserEntity(Long id);

}
