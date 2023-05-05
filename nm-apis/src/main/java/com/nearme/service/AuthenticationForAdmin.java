package com.nearme.service;

import org.jose4j.jwk.RsaJsonWebKey;

import com.nearme.request.LoginCredentialsRequest;
import com.nearme.response.LoginResponse;

public interface AuthenticationForAdmin {

	public LoginResponse checkAdminCredentials(LoginCredentialsRequest loginCredentials , RsaJsonWebKey senderJwk ) throws Exception;
	
}
