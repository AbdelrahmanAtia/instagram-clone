package com.javaworld.instagram.postservice.features.restapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

@RestController
@RequestMapping("/db")
public class DatabaseApiImpl {

	private final SpringLiquibase liquibase;

	public DatabaseApiImpl(SpringLiquibase liquibase) {
		this.liquibase = liquibase;
	}

	@PostMapping("/recreate")
	public ResponseEntity<String> recreateDatabase() throws LiquibaseException {
		liquibase.setDropFirst(true);
		liquibase.afterPropertiesSet(); // rebuilds the database with the new configuration

		// disables the rebuild behavior again as this is the default
		// we only need the rebuild behavior when this end-point is called
		liquibase.setDropFirst(false); // TODO: what are the side effects of not setting this flag to false ??!!

		return ResponseEntity.ok("Database recreated successfully.");

	}
}
