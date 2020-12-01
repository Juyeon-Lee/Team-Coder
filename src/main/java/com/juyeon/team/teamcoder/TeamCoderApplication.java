package com.juyeon.team.teamcoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties(StorageProperties.class)
public class TeamCoderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamCoderApplication.class, args);
	}

	/*
	@Bean
	CommandLineRunner init(StorageService storageService){
		return (args )-> {
			storageService.deleteAll();
			storageService.init();
		};
	}*/
}
