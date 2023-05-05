package com.nearme.model;

import java.util.Date;

public class JwtModel {

	private Long id;
	private String username;
	private String jwtToken;
	private Date expiryDate;
	private String jwtRefreshToken;
	private Date refreshExpiryDate;
	private String transactionId;
	private String tokenKey;
	private String refreshKey;
	private Long userId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getJwtRefreshToken() {
		return jwtRefreshToken;
	}
	public void setJwtRefreshToken(String jwtRefreshToken) {
		this.jwtRefreshToken = jwtRefreshToken;
	}
	public Date getRefreshExpiryDate() {
		return refreshExpiryDate;
	}
	public void setRefreshExpiryDate(Date refreshExpiryDate) {
		this.refreshExpiryDate = refreshExpiryDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTokenKey() {
		return tokenKey;
	}
	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}
	public String getRefreshKey() {
		return refreshKey;
	}
	public void setRefreshKey(String refreshKey) {
		this.refreshKey = refreshKey;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
