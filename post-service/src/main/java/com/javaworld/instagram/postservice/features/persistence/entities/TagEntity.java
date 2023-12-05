package com.javaworld.instagram.postservice.features.persistence.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tag")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor

//TODO: update it to extend the BaseEntity
public class TagEntity {
 
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
	@Column(unique = true)
    private String name;
 
    @OneToMany(
        mappedBy = "tag",
        cascade = CascadeType.ALL,
        orphanRemoval = true  //TODO: i think this deletes all postTagAssignments if
                              // the tag is deleted
    )
    private List<PostTagAssignment> postTagAssignmentList = new ArrayList<>();
 

    public TagEntity(String name) {
        this.name = name;
    }
   
}