package com.nearme.service;

import com.nearme.request.LoginCredentialsRequest;
import com.nearme.request.NewProfileRequest;
import com.nearme.response.NewLoginResponse;
import com.nearme.response.NewProfileResponse;


public interface NewUserRegistration {
    
	public NewProfileResponse processProfileRequest(NewProfileRequest newUser , long userid);
	
	public NewLoginResponse processNewUser(LoginCredentialsRequest userLoginCredentialsRequest)throws Exception;
}
