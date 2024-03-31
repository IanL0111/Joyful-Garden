package com.joyfulgarden.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MEMBERS")
@Component
public class Members {

	@Id
	@Column(name = "MEMBERID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memberId;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "NICKNAME")
	private String nickName;

	@Column(name = "MEMBERPICTURE")
	private String memberPicture;

	@Column(name = "PHONENUMBER")
	private String phoneNumber;

	@Column(name = "BIRTHDATE")
	private String birthdate;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "VERIFICATIONCODE")
	private String verificationCode;

	@Column(name = "VERIFIED")
	private boolean verified;

	@Column(name = "DELETED")
	private boolean deleted;

	@Column(name = "MEMBERLEVEL")
	private int memberLevel;

	public Members() {
	}

	// 構造函數（Constructor），用於初始化 Members 類的新對象。

	public Members(int memberId, String username, String password, String nickName, String memberPicture,
			String phoneNumber, String birthdate, String address, String verificationCode, boolean verified,
			boolean deleted, int memberLevel) {
		super();
		this.memberId = memberId;
		this.username = username;
		this.password = password;
		this.nickName = nickName;
		this.memberPicture = memberPicture;
		this.phoneNumber = phoneNumber;
		this.birthdate = birthdate;
		this.address = address;
		this.verificationCode = verificationCode;
		this.verified = verified;
		this.deleted = deleted;
		this.memberLevel = memberLevel;

	}

	public Members(String username, String password, String nickName, String memberPicture, String phoneNumber,
			String birthdate, String address, String verificationCode, boolean verified, boolean deleted,
			int memberLevel) {
		super();
		this.username = username;
		this.password = password;
		this.nickName = nickName;
		this.memberPicture = memberPicture;
		this.phoneNumber = phoneNumber;
		this.birthdate = birthdate;
		this.address = address;
		this.verificationCode = verificationCode;
		this.verified = verified;
		this.deleted = deleted;
		this.memberLevel = memberLevel;
	}

	
	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMemberPicture() {
		return memberPicture;
	}

	public void setMemberPicture(String memberPicture) {
		this.memberPicture = memberPicture;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}

}
