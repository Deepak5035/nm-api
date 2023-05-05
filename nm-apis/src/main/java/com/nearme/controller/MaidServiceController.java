package com.nearme.controller;

import javax.servlet.http.HttpServletRequest;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nearme.model.StatusDescriptionModel;
import com.nearme.request.ServiceRequest;
import com.nearme.response.ServiceResponse;
import com.nearme.service.impl.JwtFactoryServiceImpl;
import com.nearme.service.impl.MaidServiceDetailsServiceImpl;
import com.nearme.utilities.ConstantManager;

@RestController
@CrossOrigin("*")
@RequestMapping("/maid")
public class MaidServiceController {

	static RsaJsonWebKey senderJwk = null;

	static {
		try {
			senderJwk = RsaJwkGenerator.generateJwk(2048);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	MaidServiceDetailsServiceImpl maidServiceDetailsServiceImpl;

	@Autowired
	JwtFactoryServiceImpl jwtFactoryServiceImpl;

	@GetMapping(value = "/w1/maidService")
	public ResponseEntity<ServiceResponse> maidService(@RequestHeader("id") Long id,
			@RequestHeader("serviceType") String serviceType,
			@RequestHeader("Authorization") String token, HttpServletRequest req) {

		ServiceResponse response = new ServiceResponse();
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();

		boolean isValid = jwtFactoryServiceImpl.validateToken(senderJwk, token, id, "WEB", "", "", req);

		if (isValid) {
			response = maidServiceDetailsServiceImpl.maidServiceDetails(serviceType);

		} else {
			statusDescriptionModel.setStatus(ConstantManager.UnAuhorization.getStatusCode());
			statusDescriptionModel.setDescription(ConstantManager.UnAuhorization.getStatusDescription());
			response.setStatusDescriptionModel(statusDescriptionModel);
		}

		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/w2/maidVendor")
	public ResponseEntity<ServiceResponse> maidVendor(@RequestBody ServiceRequest maidServiceRequest,
			@RequestHeader("Authorization") String token, @RequestHeader("id") Long id,
			HttpServletRequest req) {

		ServiceResponse response = new ServiceResponse();
		
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();

		boolean isValid = jwtFactoryServiceImpl.validateToken(senderJwk, token, id, "WEB", "", "", req);

		if (isValid) {
			response = maidServiceDetailsServiceImpl.maidVendorDetails(maidServiceRequest);

		} else {
			statusDescriptionModel.setStatus(ConstantManager.UnAuhorization.getStatusCode());
			statusDescriptionModel.setDescription(ConstantManager.UnAuhorization.getStatusDescription());
			response.setStatusDescriptionModel(statusDescriptionModel);
		}

		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}

}
