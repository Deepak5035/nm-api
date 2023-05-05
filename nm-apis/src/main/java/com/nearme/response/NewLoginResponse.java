package com.nearme.response;


import com.nearme.entity.LoginCredentialsEntity;
import com.nearme.model.StatusDescriptionModel;

public class NewLoginResponse {
	
	StatusDescriptionModel descriptionModel;
	LoginCredentialsEntity loginCred ;
	
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
	

}
