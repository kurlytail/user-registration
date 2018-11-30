package com.bst.user.registration.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class RegistrationToken {

	@Temporal(TemporalType.DATE)
	private Date createdDate = new Date();

	@Column(unique = true)
	private final String email;

	@Transient
	private String hash;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public RegistrationToken() {
		this.email = null;
	}

	public RegistrationToken(final String email) {
		this.email = email;
	}

	@PostLoad
	@PostPersist
	public void computeHash() {
		this.hash = DigestUtils.sha512Hex(this.getEmail() + this.getCreatedDate() + this.getId().toString());
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final RegistrationToken other = (RegistrationToken) obj;
		if (this.email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!this.email.equals(other.email)) {
			return false;
		}
		return true;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public String getEmail() {
		return this.email;
	}

	public String getHash() {
		return this.hash;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.email == null) ? 0 : this.email.hashCode());
		return result;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "RegistrationToken [id=" + this.id + ", createdDate=" + this.createdDate + ", email=" + this.email
				+ ", hash=" + this.hash + "]";
	}
}
