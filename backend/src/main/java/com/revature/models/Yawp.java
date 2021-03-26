package com.revature.models;

import java.time.LocalDateTime;

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
@Table(name="Yawps")
public class Yawp {

	@Id
	@Column(name="yawp_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private int yawpId;

	@Column(name = "message", nullable = false)
	private String message;
	
	@Column(name = "author_id", nullable = false)
	private int authorId;
	
	@Column(name = "yawp_time")
	private LocalDateTime yawpTime;
	
	@Column(name = "likes_count")
	private int likesCount;


	public Yawp(String message, int authorId, LocalDateTime yawpTime, int likesCount) {
		super();
		this.message = message;
		this.authorId = authorId;
		this.yawpTime = yawpTime;
		this.likesCount = likesCount;
	}

	public Yawp(String message, int authorId) {
		super();
		this.message = message;
		this.authorId = authorId;
	}

}
