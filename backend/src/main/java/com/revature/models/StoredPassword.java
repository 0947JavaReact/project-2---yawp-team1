package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Password")
public class StoredPassword {

	@Id
	@Column(name = "password_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private int passwordId;
	
	//@OneToOne(mappedBy = "passwordHolder", fetch = FetchType.EAGER)
	
	@Column(name = "plain_text")
	private String plainTextPassword;
	
	@Column(name = "hashed")
	private String hashedPassword;

	public StoredPassword(String plainTextPassword, String hashedPassword) {
		super();
		this.plainTextPassword = plainTextPassword;
		this.hashedPassword = hashedPassword;
	}
	
	
}
