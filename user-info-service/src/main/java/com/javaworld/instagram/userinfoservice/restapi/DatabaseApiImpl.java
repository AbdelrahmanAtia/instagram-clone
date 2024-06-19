package com.javaworld.instagram.userinfoservice.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaworld.instagram.userinfoservice.service.DatabaseServiceImpl;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

@RestController
@RequestMapping("/db")
public class DatabaseApiImpl {
	
	private final SpringLiquibase liquibase;
	
	@Autowired
	private DatabaseServiceImpl databaseService;

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
	
	@GetMapping("/clear")
	public ResponseEntity<String> clearDatabase()  {
		databaseService.clearDatabase();
		return ResponseEntity.ok("Database cleared successfully.");

	}
	
}
