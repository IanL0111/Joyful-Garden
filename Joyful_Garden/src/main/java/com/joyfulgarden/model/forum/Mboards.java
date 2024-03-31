package com.joyfulgarden.model.forum;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "Mboards")
@SQLDelete(sql = "UPDATE product SET isDeleted = true WHERE id = ?") //@SQLDelete註解用來覆寫delete指令，每次我們執行delete指令時，我們會將其轉換成清單3.1.2中的UPDATE語句，這條指令將isDeleted欄位改為true，而不是永久刪除資料。
@Where(clause = "isDeleted = false")
public class Mboards {

	@Id
	@Column(name = "MBOARDID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer mboardID;
	
	@Column(name = "MBOARDTITLE")
	private String mboardTitle;
	
	@Column(name = "MBULLETIN")
	private String mbulletin;

	@Column(name = "CREATETIME")
	private String createTime;
	
	@Column(name = "ISDELETED")
	private boolean isDeleted = Boolean.FALSE;
	
	
	/*一對多 多對一 多對多*/
	
	@OneToMany(mappedBy = "mboards", cascade = CascadeType.ALL)
	private List<Sboards> sboards;
	
	
	/*建構子
	 * 多對多
	 * 多對一
	 * 一對多*/
	
	public Mboards() {}


	public Integer getMboardID() {
		return mboardID;
	}

	public String getMboardTitle() {
		return mboardTitle;
	}

	public String getMbulletin() {
		return mbulletin;
	}

	public String getCreateTime() {
		return createTime;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public List<Sboards> getSboards() {
		return sboards;
	}

	public void setMboardID(Integer mboardID) {
		this.mboardID = mboardID;
	}

	public void setMboardTitle(String mboardTitle) {
		this.mboardTitle = mboardTitle;
	}

	public void setMbulletin(String mbulletin) {
		this.mbulletin = mbulletin;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setSboards(List<Sboards> sboards) {
		this.sboards = sboards;
	}

	
	
	
	
	
	

	
	
	

}
