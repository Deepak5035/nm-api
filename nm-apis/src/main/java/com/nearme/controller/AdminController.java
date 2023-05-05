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
import com.nearme.service.impl.AuthenticationForAdminImpl;
import com.nearme.service.impl.NewAdminRegistrationImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/admin")
public class AdminController {

	static RsaJsonWebKey senderJwk = null;

	static {
		try {
			senderJwk = RsaJwkGenerator.generateJwk(2048);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	NewAdminRegistrationImpl newAdminRegistration;

	@Autowired
	AuthenticationForAdminImpl authenticationForAdminImpl;

	@PostMapping("/n1/newPReq")
	public ResponseEntity<NewProfileResponse> newProfileRequest(@RequestBody NewProfileRequest admin,
			@RequestHeader("adminId") Long adminid, @RequestHeader("Authorization") String token) {

		// TODO validate token

		NewProfileResponse response = newAdminRegistration.processProfileRequest(admin, adminid);

		return new ResponseEntity<NewProfileResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/n1/newAdminReq")
	public ResponseEntity<NewLoginResponse> newAdminRequest(@RequestBody LoginCredentialsRequest adminLoginCredentialsRequest) throws Exception {

		NewLoginResponse response = newAdminRegistration.processNewAdmin(adminLoginCredentialsRequest);

		return new ResponseEntity<NewLoginResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/n1/adminLogin")
	public ResponseEntity<LoginResponse> checkAdminLogin(@RequestBody LoginCredentialsRequest loginCredentials) throws Exception {

		LoginResponse response = authenticationForAdminImpl.checkAdminCredentials(loginCredentials, senderJwk);

		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);

	}
	
	@PostMapping("/n1/forgetPass")
	public ResponseEntity<LoginResponse> forgetPassword(@RequestBody LoginCredentialsRequest loginCredentialsRequest) {
		
		LoginResponse response = authenticationForAdminImpl.forgetPassword(loginCredentialsRequest);
		
		return new ResponseEntity<LoginResponse>(response , HttpStatus.OK);
	}

}
