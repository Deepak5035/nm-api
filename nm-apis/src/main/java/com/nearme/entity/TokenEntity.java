package com.nearme.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "tbl_token_detail")
public class TokenEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "current_date1")
	private Date currentDate;

	@Column(name = "expire_date")
	private Date expireDate;

	@Column(name = "expiry_date")
	private Date expiryDate;

	@Column(name = "jwt_refresh_token")
	private String jwtRefreshToken;

	@Column(name = "jwt_token")
	private String jwtToken;

	@Column(name = "refresh_current_date")
	private Date refreshCurrentDate;

	@Column(name = "refresh_expiry_date")
	private Date refreshExpiryDate;

	@Column(name = "refresh_key")
	private String refreshKey;

	@Column(name = "source")
	private String source;

	@Column(name = "token_key")
	private String tokenKey;

	@Transient
	private String jwtTokenExpiryDateTime;
	@Transient
	private String jwtRefreshTokenExpiryDateTime;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lid" , referencedColumnName = "id")
	private LoginCredentialsEntity loginCredentialsEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
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

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public Date getRefreshCurrentDate() {
		return refreshCurrentDate;
	}

	public void setRefreshCurrentDate(Date refreshCurrentDate) {
		this.refreshCurrentDate = refreshCurrentDate;
	}

	public Date getRefreshExpiryDate() {
		return refreshExpiryDate;
	}

	public void setRefreshExpiryDate(Date refreshExpiryDate) {
		this.refreshExpiryDate = refreshExpiryDate;
	}

	public String getRefreshKey() {
		return refreshKey;
	}

	public void setRefreshKey(String refreshKey) {
		this.refreshKey = refreshKey;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public String getJwtTokenExpiryDateTime() {
		return jwtTokenExpiryDateTime;
	}

	public void setJwtTokenExpiryDateTime(String jwtTokenExpiryDateTime) {
		this.jwtTokenExpiryDateTime = jwtTokenExpiryDateTime;
	}

	public String getJwtRefreshTokenExpiryDateTime() {
		return jwtRefreshTokenExpiryDateTime;
	}

	public void setJwtRefreshTokenExpiryDateTime(String jwtRefreshTokenExpiryDateTime) {
		this.jwtRefreshTokenExpiryDateTime = jwtRefreshTokenExpiryDateTime;
	}

	public LoginCredentialsEntity getLoginCredentialsEntity() {
		return loginCredentialsEntity;
	}

	public void setLoginCredentialsEntity(LoginCredentialsEntity loginCredentialsEntity) {
		this.loginCredentialsEntity = loginCredentialsEntity;
	}

	}
