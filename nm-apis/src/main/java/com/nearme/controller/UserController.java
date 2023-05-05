package com.nearme.controller;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nearme.request.LoginCredentialsRequest;
import com.nearme.request.NewProfileRequest;
import com.nearme.response.LoginResponse;
import com.nearme.response.NewLoginResponse;
import com.nearme.response.NewProfileResponse;
import com.nearme.service.impl.AuthenticationForUserImpl;
import com.nearme.service.impl.NewUserRegistrationImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserController {

	static RsaJsonWebKey senderJwk = null;
	static {
		try {
			senderJwk = RsaJwkGenerator.generateJwk(2048);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	AuthenticationForUserImpl authenticationForUserImpl;

	@Autowired
	NewUserRegistrationImpl newUserRegistration;

	@PostMapping("/n1/newPReq")
	public ResponseEntity<NewProfileResponse> newProfileRequest(@RequestBody NewProfileRequest user,
			@RequestHeader("userId") Long userid, @RequestHeader("Authorization") String token) {

		// TODO validate token

		NewProfileResponse response = newUserRegistration.processProfileRequest(user, userid);

		return new ResponseEntity<NewProfileResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/n1/newUserReq")
	public ResponseEntity<NewLoginResponse> newUserRequest(
			@RequestBody LoginCredentialsRequest userLoginCredentialsRequest) throws Exception {

		NewLoginResponse response = newUserRegistration.processNewUser(userLoginCredentialsRequest);

		return new ResponseEntity<NewLoginResponse>(response, HttpStatus.OK);
	}


	@PostMapping("/n1/userLogin")
	public ResponseEntity<LoginResponse> checkUserLogin(
			@RequestBody LoginCredentialsRequest loginCredentials) throws Exception {

		LoginResponse response = authenticationForUserImpl.checkUserCredentials(loginCredentials, senderJwk);

		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);

	}

}
