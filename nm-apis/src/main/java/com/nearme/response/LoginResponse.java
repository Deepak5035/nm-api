package com.nearme.response;


import com.nearme.entity.LoginCredentialsEntity;
import com.nearme.entity.TokenEntity;
import com.nearme.model.StatusDescriptionModel;

public class LoginResponse {

	StatusDescriptionModel descriptionModel;
	LoginCredentialsEntity loginCred ;
	TokenEntity tokenEntity;
	
	public StatusDescriptionModel getDescriptionModel() {
		return descriptionModel;
	}
	public void setDescriptionModel(StatusDescriptionModel descriptionModel) {
		this.descriptionModel = descriptionModel;
	}
	public LoginCredentialsEntity getLoginCred() {
		return loginCred;
	}
	public void setLoginCred(LoginCredentialsEntity loginCred) {
		this.loginCred = loginCred;
	}
	public TokenEntity getTokenEntity() {
		return tokenEntity;
	}
	public void setTokenEntity(TokenEntity tokenEntity) {
		this.tokenEntity = tokenEntity;
	}
	
}
