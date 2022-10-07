package com.javaworld.instagram.postservice.features.persistence.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Tag")
@Table(name = "tag")
public class TagEntity {
 
    @Id
    @GeneratedValue
    private int id;
 
    private String name;
 
    @OneToMany(
        mappedBy = "tag",
        cascade = CascadeType.ALL,
        orphanRemoval = true  //TODO: i think this deletes all postTagAssignments if
                              // the tag is deleted
    )
    private List<PostTagAssignment> postTagAssignmentList = new ArrayList<>();
 
    public TagEntity() {
    
    }
 
    public TagEntity(String name) {
        this.name = name;
    }
     
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<PostTagAssignment> getPostTagAssignmentList() {
		return postTagAssignmentList;
	}

	public void setPostTagAssignmentList(List<PostTagAssignment> postTagAssignmentList) {
		this.postTagAssignmentList = postTagAssignmentList;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagEntity tag = (TagEntity) o;
        return Objects.equals(name, tag.name);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}