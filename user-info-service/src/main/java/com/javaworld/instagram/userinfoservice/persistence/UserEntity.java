package com.javaworld.instagram.userinfoservice.persistence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
//TODO: crate an abstract entity that holds the version property and each entity shall extend that 
//abstract entity
public class UserEntity implements Serializable {
	
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Type(type = "org.hibernate.type.UUIDCharType")
	@Column(unique = true)
	private UUID userUuid;
	
	@Column(unique = true)
	private String mobileNumber;

	@Column(unique = true)
	private String email;
	
	@Column
	private String fullName;

	@Column(unique = true)
	private String username;
	
	private String password;

	@Column
	private String profileImageName;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FollowerEntity> followers = new HashSet<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FollowerEntity> following = new HashSet<>();
		
	@Version
	private int version;

}
