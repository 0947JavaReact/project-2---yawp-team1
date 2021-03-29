package com.revature.models;

import java.time.LocalDateTime;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="Yawps")
public class Yawp implements Comparable<Yawp>{

	@Id
	@Column(name="yawp_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private int yawpId;

	@Column(name = "message", nullable = false)
	private String message;
	
	@Column(name = "author_id", nullable = false)
	private int authorId;
	
	@Column(name = "author_uname")
	private String authorUsername;
	
	@Column(name = "author_pic")
	private String authorPic;
	
	@Column(name = "yawp_time")
	private LocalDateTime yawpTime;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<User> likes;

	@Override
	public int compareTo(Yawp o) {
		return getYawpTime().compareTo(o.getYawpTime());
	}

	public Yawp(String message, int authorId, String authorUsername, String authorPic, LocalDateTime yawpTime,
			Set<User> likes) {
		super();
		this.message = message;
		this.authorId = authorId;
		this.authorUsername = authorUsername;
		this.authorPic = authorPic;
		this.yawpTime = yawpTime;
		this.likes = likes;
	}
	

	public void addLike(User u) {
		likes.add(u);
	}
	
	public void removeLike(User u) {
		likes.remove(u);
	}
}
