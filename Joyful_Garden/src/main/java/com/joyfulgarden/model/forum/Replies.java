package com.joyfulgarden.model.forum;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "REPLIES")
@SQLDelete(sql = "UPDATE REPLIES SET isDeleted = 1 WHERE REPLYID = ?") //@SQLDelete註解用來覆寫delete指令，每次我們執行delete指令時，我們會將其轉換成清單3.1.2中的UPDATE語句，這條指令將isDeleted欄位改為true，而不是永久刪除資料。
@Where(clause = "isDeleted = false")
public class Replies {

	@Id
	@Column(name = "REPLYID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer replyID;
	
	@Column(name = "REPLYCONTENT")
	private String replyContent;

	@Column(name = "AUTHORNICKNAME")
	private String authorNickname;
	
	@Column(name = "POSTID")
	private Integer postID;
	
	@Column(name = "LIKESCOUNT")
	private Integer likesCount;
	
	@Column(name = "REPLYTIME")
	private LocalDateTime replyTime;
	
	@Column(name = "ISDELETED")
	private boolean isDeleted = Boolean.FALSE;
	
	//@JsonIgnoreProperties({ "" })
	@JsonIgnore //但我不知道為何要加這行
	@ManyToOne
	@JoinColumn(name = "AUTHORNICKNAME", referencedColumnName = "FNICKNAME" , insertable = false, updatable = false)
	private Forummembers forummembers;
	
	//@JsonIgnoreProperties({ "" })
	@JsonIgnore //但我不知道為何要加這行
	@ManyToOne
	@JoinColumn(name = "POSTID", insertable = false, updatable = false)
	private Posts posts;
	
	@OneToMany(mappedBy = "replies",cascade = CascadeType.ALL)
	private List<Comments> comments;
	
	/*建構子
	 * 多對多
	 * 多對一
	 * 一對多*/
	
	public Replies() {}


	public Integer getReplyID() {
		return replyID;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public String getAuthorNickname() {
		return authorNickname;
	}
	
	public Integer getPostID() {
		return postID;
	}

	public Integer getLikesCount() {
		return likesCount;
	}

	public LocalDateTime getReplyTime() {
		return replyTime;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public Posts getPosts() {
		return posts;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setReplyID(Integer replyID) {
		this.replyID = replyID;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public void setAuthorNickname(String authorNickname) {
		this.authorNickname = authorNickname;
	}
	
	public void setPostID(Integer postID) {
		this.postID = postID;
	}

	public void setLikesCount(Integer likesCount) {
		this.likesCount = likesCount;
	}

	public void setReplyTime(LocalDateTime replyTime) {
		this.replyTime = replyTime;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}


	public Forummembers getForummembers() {
		return forummembers;
	}
	public void setForummembers(Forummembers forummembers) {
		this.forummembers = forummembers;
	}
	
	
	
	
	
	
}
