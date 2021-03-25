package com.revature.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Yawps")
public class Yawp {

	@Id
	@Column(name="yawp_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int yawpId;

	@Column(name = "message", nullable = false)
	private String message;
	
	@Column(name = "author_id", nullable = false)
	private int authorId;
	
	@Column(name = "yawp_time")
	private LocalDateTime yawpTime;
	
	@Column(name = "likes_count")
	private int likesCount;

	public Yawp() {
		super();
	}

	public Yawp(String message, int authorId, LocalDateTime yawpTime, int likesCount) {
		super();
		this.message = message;
		this.authorId = authorId;
		this.yawpTime = yawpTime;
		this.likesCount = likesCount;
	}

	public Yawp(int yawpId, String message, int authorId, LocalDateTime yawpTime, int likesCount) {
		super();
		this.yawpId = yawpId;
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

	public int getYawpId() {
		return yawpId;
	}

	public void setYawpId(int yawpId) {
		this.yawpId = yawpId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public LocalDateTime getYawpTime() {
		return yawpTime;
	}

	public void setYawpTime(LocalDateTime yawpTime) {
		this.yawpTime = yawpTime;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	@Override
	public String toString() {
		return "Yawp [yawpId=" + yawpId + ", message=" + message + ", authorId=" + authorId + ", yawpTime=" + yawpTime
				+ ", likesCount=" + likesCount + "]";
	}
	
}
