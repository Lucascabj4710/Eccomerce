package com.eccomerce;

import com.eccomerce.persistence.entity.RoleEntity;
import com.eccomerce.persistence.entity.RoleEnum;
import com.eccomerce.persistence.entity.UserEntity;
import com.eccomerce.persistence.repository.RoleEntityRepository;
import com.eccomerce.persistence.repository.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class EccomerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EccomerceApplication.class, args);
	}




}
