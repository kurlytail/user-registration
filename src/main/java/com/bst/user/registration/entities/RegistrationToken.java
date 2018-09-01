package com.bst.user.registration.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RegistrationToken {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private Date createdDate = new Date();
    @Column(unique=true)
    private String email;
    
    public RegistrationToken() {
    	this.email = null;
    }
    
    public RegistrationToken(String email) {
    	this.email = email;
    }

	public Long getId() {
		return id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getEmail() {
		return email;
	}
}
