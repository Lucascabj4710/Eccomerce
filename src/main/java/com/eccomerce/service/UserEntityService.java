package com.eccomerce.service;

import com.eccomerce.persistence.dto.request.UserEntityDto;
import org.springframework.http.ResponseEntity;

public interface UserEntityService {

    ResponseEntity<?> createUserEntity(UserEntityDto userEntityDto);
    ResponseEntity<?> updateUserEntity(Long id,UserEntityDto userEntityDto);
    ResponseEntity<?> deleteUserEntity(Long id);

}
