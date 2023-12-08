package com.taskmanagement.system;

import com.taskmanagement.system.entity.User;
import com.taskmanagement.system.repository.UserRepository;
import com.taskmanagement.system.security.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class TaskManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}


	@Bean
	public CommandLineRunner CommandLineRunnerBean(
			UserRepository userRepository,
			PasswordEncoder encoder
	) {
		return (args) -> {
			if(userRepository.count() == 0) {
				userRepository.saveAll(
						List.of(
								User.builder()
										.email("user1@gmail.com")
										.password(encoder.encode("123456"))
										.role(Role.USER)
										.build(),
								User.builder()
										.email("user2@gmail.com")
										.password(encoder.encode("123456"))
										.role(Role.USER)
										.build(),
								User.builder()
										.email("user3@gmail.com")
										.password(encoder.encode("123456"))
										.role(Role.USER)
										.build()
						)
				);
			}
		};
	}

}
