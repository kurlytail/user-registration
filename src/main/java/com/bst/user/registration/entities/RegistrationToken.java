package com.bst.user.registration.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;

@Entity
public class RegistrationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date createdDate = new Date();
	@Column(unique = true)
	private String email;
	@Transient
	private String hash;
	@PostLoad
	@PostPersist
	public void computeHash() {
		this.hash = DigestUtils.sha512Hex(getEmail() + getCreatedDate() + getId().toString());
	}
	
	public String getHash() {
		return this.hash;
	}

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
