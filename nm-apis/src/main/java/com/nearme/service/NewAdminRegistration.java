package com.nearme.service;

import com.nearme.request.LoginCredentialsRequest;
import com.nearme.request.NewProfileRequest;
import com.nearme.response.NewLoginResponse;
import com.nearme.response.NewProfileResponse;

public interface NewAdminRegistration {

	public NewProfileResponse processProfileRequest(NewProfileRequest newAdmin, long adminid);

	public NewLoginResponse processNewAdmin(LoginCredentialsRequest adminLoginCredentialsRequest)
			throws Exception;
}
