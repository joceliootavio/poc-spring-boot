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
import javax.persistence.SequenceGenerator;
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
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SQ_USER")
	@SequenceGenerator(name="SQ_USER", sequenceName="SQ_USER")
	private Long id;
	
	@NotEmpty
	@Size(max=50)
	@Column(name="NAME", nullable=false, length=50)
	private String name;
	
	@NotEmpty
	@Size(max=50)
	@Column(name="LOGIN", nullable=false, length=50, unique=true)
	private String login;
	
	@NotEmpty
	@Size(max=50)
	@Column(name="PASSWORD", nullable=false, length=60)
	private String password;	
	
	@Setter(AccessLevel.NONE)
	@Column(name="CREATED_DATE", nullable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name="UPDATED_DATE")
	@Setter(AccessLevel.NONE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@Column(name="EMAIL", nullable=false, length=50)
	@Size(max=50)
	@Email
	private String email;
	
	@Column(name="ADMIN")
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
