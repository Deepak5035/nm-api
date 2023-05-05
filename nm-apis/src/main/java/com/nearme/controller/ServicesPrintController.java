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
import com.nearme.response.PrintServiceResponse;
import com.nearme.service.impl.JwtFactoryServiceImpl;
import com.nearme.service.impl.PrintServiceImpl;
import com.nearme.utilities.ConstantManager;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/print")
public class ServicesPrintController {

	static RsaJsonWebKey senderJwk = null;

	static {
		try {
			senderJwk = RsaJwkGenerator.generateJwk(2048);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	PrintServiceImpl printServiceImpl;

	@Autowired
	JwtFactoryServiceImpl jwtFactoryServiceImpl;

	@GetMapping(value = "/p/printService")
	public ResponseEntity<PrintServiceResponse> serviceDetailsPrint(@RequestHeader("Authorization") String token,
			HttpServletRequest req, @RequestHeader("id") Long id, @RequestHeader("serviceType") String typeOfService) {

		PrintServiceResponse response = new PrintServiceResponse();
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel(); 

		boolean isValid = jwtFactoryServiceImpl.validateToken(senderJwk, token, id, "WEB", "", "", req);

		
		if (isValid) {
			response = printServiceImpl.printServices(typeOfService);
		}
		else {
			statusDescriptionModel.setStatus(ConstantManager.UnAuhorization.getStatusCode());
			statusDescriptionModel.setDescription(ConstantManager.UnAuhorization.getStatusDescription());
			response.setStatusDescriptionModel(statusDescriptionModel);
		}
		return new ResponseEntity<PrintServiceResponse>(response, HttpStatus.OK);
	}
}
