package com.javaworld.instagram.userinfoservice.persistence;

import java.util.UUID;

import javax.persistence.Entity; 
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	private String username;
	
	@Version
	private int version;
	
	private UUID userUuid;

	private String email;

	private String name;

	private String password;	
	
	private int postsCount;
	
	public UserEntity() {
		
	}

	public UserEntity(String username, String email, String name, String password) {
		this.username = username;
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UUID getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(UUID userUuid) {
		this.userUuid = userUuid;
	}

	public int getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(int postsCount) {
		this.postsCount = postsCount;
	}	
	
	

}
