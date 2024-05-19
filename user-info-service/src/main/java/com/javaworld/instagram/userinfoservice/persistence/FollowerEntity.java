package com.javaworld.instagram.userinfoservice.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "follower")
@Getter
@Setter
@ToString
//TODO: crate an abstract entity that holds the version property and each entity shall extend that 
//abstract entity
public class FollowerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "userUuid")
    private UserEntity follower;

    @ManyToOne
    @JoinColumn(name = "followed_id", referencedColumnName = "userUuid")
    private UserEntity followed;
	
	@Version
	private int version;


}
