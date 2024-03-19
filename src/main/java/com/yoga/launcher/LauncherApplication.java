package com.yoga.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LauncherApplication {

	public static void main(String[] args) {
		SpringApplication.run(LauncherApplication.class, args);
	}

}
