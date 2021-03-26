package com.revature.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@Entity
//@Table(name = "FFHolder")
public class StoredFF {
//
//	@Id
//	@Column(name = "ff_id")
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Setter(AccessLevel.NONE)
//	private int ffId;
//
//	@JsonBackReference
//	@OneToMany(mappedBy = "ffHolder", fetch = FetchType.EAGER)
//	private Set<User> followers;
//
//	@JsonBackReference
//	@OneToMany(mappedBy = "ffHolder", fetch = FetchType.EAGER)
//	private Set<User> following;
//
//	public StoredFF(Set<User> followers, Set<User> following) {
//		super();
//		this.following = following;
//		this.followers = followers;
//	}
//	

}
