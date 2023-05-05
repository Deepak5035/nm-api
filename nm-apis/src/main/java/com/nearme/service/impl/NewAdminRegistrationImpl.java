package com.nearme.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.nearme.dao.AuthenticationRepo;
import com.nearme.dao.NewProfileDetailsRepo;
import com.nearme.entity.LoginCredentialsEntity;
import com.nearme.entity.NewProfileEntity;
import com.nearme.model.StatusDescriptionModel;
import com.nearme.request.LoginCredentialsRequest;
import com.nearme.request.NewProfileRequest;
import com.nearme.response.NewLoginResponse;
import com.nearme.response.NewProfileResponse;
import com.nearme.service.NewAdminRegistration;
import com.nearme.utilities.ConstantManager;
import com.nearme.utilities.EmailValidator;
import com.nearme.utilities.KeyEncrypterDecrypter;
import com.nearme.utilities.SendRegistrationMail;

@Service
public class NewAdminRegistrationImpl implements NewAdminRegistration {

	@Autowired
	NewProfileDetailsRepo newAdminDetailsRepo;

	@Autowired
	AuthenticationRepo authenticationRepo;

	@Autowired
	EmailValidator emailValidator;
	
	@Autowired
	SendRegistrationMail sendRegistrationMail;
	
	@Autowired
	KeyEncrypterDecrypter keyEncrypterDecrypter;
	
	@Autowired
	Environment environment;

	@Override
	public NewProfileResponse processProfileRequest(NewProfileRequest newProfileRequest, long adminid) {

		StatusDescriptionModel description = new StatusDescriptionModel();
		NewProfileResponse response = new NewProfileResponse();

		List<NewProfileEntity> nearMeAdminEntity = newAdminDetailsRepo.SearchByEmailId(newProfileRequest.getEmailId());

		if (nearMeAdminEntity != null) {
			description.setDescription(ConstantManager.AdminAlreadyThere.getStatusDescription());
			description.setStatus(ConstantManager.AdminAlreadyThere.getStatusCode());
			response.setDescriptionModel(description);
			response.setNewProfile(nearMeAdminEntity);
			return response;
		} else {

			// TODO
			// Enter admin Details in DataBase

		}

		return null;
	}

	@Override
	public NewLoginResponse processNewAdmin(LoginCredentialsRequest adminLoginCredentialsRequest)
			throws Exception {

		StatusDescriptionModel description = new StatusDescriptionModel();
		NewLoginResponse response = new NewLoginResponse();
		response.setDescriptionModel(description);

		LoginCredentialsEntity adminLoginCredentialsEntity = authenticationRepo.findByEmailIdAndRole(adminLoginCredentialsRequest.getEmailId(),adminLoginCredentialsRequest.getRole());

		if (adminLoginCredentialsEntity != null) {

			description.setDescription(ConstantManager.AdminAlreadyThere.getStatusDescription());
			description.setStatus(ConstantManager.AdminAlreadyThere.getStatusCode());
			response.setLoginCred(adminLoginCredentialsEntity);
			return response;
		}

		else {

			boolean isValidEmail = emailValidator.validateEmail(adminLoginCredentialsRequest.getEmailId());
			LoginCredentialsEntity loginCredentials = new LoginCredentialsEntity();
			
			if (isValidEmail) {

				String encryptedPass =keyEncrypterDecrypter.KeyEncrypte(adminLoginCredentialsRequest.getEmailId(),adminLoginCredentialsRequest.getPassword());
				
				loginCredentials.setEmailId(adminLoginCredentialsRequest.getEmailId());
				loginCredentials.setPassword(encryptedPass);
				loginCredentials.setRole(adminLoginCredentialsRequest.getRole());
				
				authenticationRepo.save(loginCredentials);
				
				description.setDescription(ConstantManager.AdminRegistered.getStatusDescription());
				description.setStatus(ConstantManager.AdminRegistered.getStatusCode());
				response.setLoginCred(loginCredentials);
				
				sendRegistrationMail.sendMail( adminLoginCredentialsRequest.getEmailId(), 
						environment.getProperty("sender.from.mail") , 
						environment.getProperty("sender.from.pass"),
						environment.getProperty("mail.subject"),
				        environment.getProperty("mail.body")
						);
				
				return response;
			}else {
				description.setDescription(ConstantManager.EmailIsNotCorrct.getStatusDescription());
				description.setStatus(ConstantManager.EmailIsNotCorrct.getStatusCode());
				response.setLoginCred(loginCredentials);
				return response;
			}
		}

	}

}
