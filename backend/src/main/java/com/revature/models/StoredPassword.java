package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	// Columns in the StoredPassword Table
	@Id
	@Column(name = "password_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private int passwordId;
	
	// Password after encryption
	@Column(name = "hashed")
	private String hashedPassword;

	// StoredPassword constructor
	public StoredPassword(String hashedPassword) {
		super();
		this.hashedPassword = hashedPassword;
	}
	
}
