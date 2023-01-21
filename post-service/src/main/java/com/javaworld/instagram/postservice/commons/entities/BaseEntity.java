package com.javaworld.instagram.postservice.commons.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	
	@Version
	private int version;
	
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime  createdAt;   //TODO: shall it be of type OffsetDateTime ?

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;  //TODO: shall it be of type OffsetDateTime ?
	
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy = "System";   //TODO: remove default value when applying spring security so that it will automatically
                                           //fill it with the current user 

    @LastModifiedBy
    @Column(nullable = false)
    private String updatedBy = "System"; //TODO: remove default value when applying spring security so that it will automatically
                                         //fill it with the current user 

    
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
    
}