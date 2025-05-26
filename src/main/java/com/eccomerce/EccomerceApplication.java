package com.eccomerce;

import com.eccomerce.persistence.entity.RoleEntity;
import com.eccomerce.persistence.entity.RoleEnum;
import com.eccomerce.persistence.entity.UserEntity;
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

	@Bean
	CommandLineRunner commandLineRunner(UserEntityRepository userRepository){
		return args -> {

			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.build();

			UserEntity userLucas = UserEntity.builder()
					.username("lucas")
					.password("$2a$10$OdCM4qb4u4ZKcrP8Un3E3uoK9znxE0Vg6DCEUQlOn1YCkCXuoZjDC")
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.isEnabled(true)
					.roleEntities(Set.of(roleAdmin))
					.build();

			userRepository.save(userLucas);
		};
	}

}
