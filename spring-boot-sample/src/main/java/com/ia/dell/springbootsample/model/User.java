package com.ia.dell.springbootsample.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotEmpty
	@Size(max=50)
	@Column(nullable=false, length=50)
	private String name;
	
	@NotEmpty
	@Size(max=50)
	@Column(nullable=false, length=50, unique=true)
	private String login;
	
	@NotEmpty
	@Size(max=50)
	@Column(nullable=false, length=60)
	private String password;	
	
	@Setter(AccessLevel.NONE)
	@Column(nullable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Setter(AccessLevel.NONE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@Column(nullable=false, length=50)
	@Size(max=50)
	@Email
	private String email;
	
	@NotNull
	private Boolean admin;
	
	@PrePersist
	public void onPrePersist() {
		createdDate = new Date();
		onPreUpdate();
	}
	
	@PreUpdate
	public void onPreUpdate() {
		updatedDate = new Date();
	}
}
