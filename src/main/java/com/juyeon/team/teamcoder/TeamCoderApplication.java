package com.juyeon.team.teamcoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TeamCoderApplication {

	public static void main(String[] args) {

		SpringApplication.run(TeamCoderApplication.class, args);
	}

}
