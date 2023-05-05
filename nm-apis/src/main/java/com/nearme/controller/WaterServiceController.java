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
import com.nearme.service.impl.WaterServiceDetailsServiceImpl;
import com.nearme.utilities.ConstantManager;

@RestController
@CrossOrigin("*")
@RequestMapping("/water")
public class WaterServiceController {

	
	static RsaJsonWebKey senderJwk = null;

	static {
		try {
			senderJwk = RsaJwkGenerator.generateJwk(2048);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	WaterServiceDetailsServiceImpl serviceDetailsServiceImpl;
	
	@Autowired
	JwtFactoryServiceImpl jwtFactoryServiceImpl;
	
	@GetMapping(value = "/w1/waterService")
	public ResponseEntity<ServiceResponse> waterService(@RequestHeader("id") long id,@RequestHeader("Authorization") String token , 
			@RequestHeader("serviceType") String serviceType,
			HttpServletRequest req) {

		ServiceResponse response = new ServiceResponse();
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();

		boolean isValid = jwtFactoryServiceImpl.validateToken(senderJwk, token, id, "WEB", "", "", req);
		
		if(isValid) {
			response = serviceDetailsServiceImpl.waterServiceDetails(serviceType);
		}else {
			statusDescriptionModel.setStatus(ConstantManager.UnAuhorization.getStatusCode());
			statusDescriptionModel.setDescription(ConstantManager.UnAuhorization.getStatusDescription());
			response.setStatusDescriptionModel(statusDescriptionModel);
		}

		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/w2/waterVendor")
	public ResponseEntity<ServiceResponse> waterVendor(@RequestBody ServiceRequest waterServiceRequest,
			@RequestHeader("Authorization") String token , @RequestHeader("aId") Long aId , HttpServletRequest req) {

		ServiceResponse response = new ServiceResponse();
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		
		boolean isValid = jwtFactoryServiceImpl.validateToken(senderJwk, token, aId, "WEB", "", "", req);
		
		
		if(isValid) {
			response = serviceDetailsServiceImpl.waterVendorDetails(waterServiceRequest);
		}else {
			statusDescriptionModel.setStatus(ConstantManager.UnAuhorization.getStatusCode());
			statusDescriptionModel.setDescription(ConstantManager.UnAuhorization.getStatusDescription());
			response.setStatusDescriptionModel(statusDescriptionModel);
		}
		
		
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}
}
