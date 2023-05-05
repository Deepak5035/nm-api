package com.nearme.controller;

import javax.servlet.http.HttpServletRequest;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nearme.model.StatusDescriptionModel;
import com.nearme.response.ThoughtOfTheDayResponse;
import com.nearme.service.impl.JwtFactoryServiceImpl;
import com.nearme.service.impl.ThoughOfTheDayServiceImpl;
import com.nearme.utilities.ConstantManager;


@RestController
@RequestMapping(value ="/thought")
@CrossOrigin("*")
public class ThoughtOfTheDayController {

	static RsaJsonWebKey senderJwk = null;

	@Autowired
	JwtFactoryServiceImpl jwtFactoryServiceImpl;
	
	@Autowired
	ThoughOfTheDayServiceImpl thoughOfTheDayServiceImpl;
	
	static {
		try {
			senderJwk = RsaJwkGenerator.generateJwk(2048);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@GetMapping(value = "/t1/thoughtOf")
	public ResponseEntity<ThoughtOfTheDayResponse> thoughtOfTheDay(@RequestHeader("Authorization") String token , 
			HttpServletRequest req , @RequestHeader("id") Long id){
		
		ThoughtOfTheDayResponse response = new ThoughtOfTheDayResponse();
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		
		boolean isValid = jwtFactoryServiceImpl.validateToken(senderJwk, token, id, "WEB", "", "", req);
		
		if(isValid) {
			  try {
				response  = thoughOfTheDayServiceImpl.getThoughtOfTheDay();
			} catch (Exception e) {
		
				statusDescriptionModel.setStatus(ConstantManager.ServerError.getStatusCode());
				statusDescriptionModel.setDescription(ConstantManager.ServerError.getStatusDescription());
				response.setStatusDescriptionModel(statusDescriptionModel);
				e.printStackTrace();
			}
		}
		else {
			statusDescriptionModel.setStatus(ConstantManager.UnAuhorization.getStatusCode());
			statusDescriptionModel.setDescription(ConstantManager.UnAuhorization.getStatusDescription());
			response.setStatusDescriptionModel(statusDescriptionModel);
		}
		return new ResponseEntity<ThoughtOfTheDayResponse>( response , HttpStatus.OK);
	}
	
}
