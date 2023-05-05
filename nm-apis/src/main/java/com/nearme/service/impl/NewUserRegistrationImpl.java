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
import com.nearme.service.NewUserRegistration;
//import com.nearme.utilities.KeyEncrypterDecrypter;
import com.nearme.utilities.ConstantManager;
import com.nearme.utilities.EmailValidator;
import com.nearme.utilities.KeyEncrypterDecrypter;
import com.nearme.utilities.SendRegistrationMail;

@Service
public class NewUserRegistrationImpl implements NewUserRegistration {

	@Autowired
	NewProfileDetailsRepo newUserDetailsRepo;

	@Autowired
	AuthenticationRepo authenticationRepo;

	@Autowired
	EmailValidator emailValidator;

	@Autowired
	KeyEncrypterDecrypter keyEncrypterDecrypter;
	
	@Autowired
	SendRegistrationMail sendRegistrationMail;
	
	@Autowired
	Environment environment;

	@Override
	public NewProfileResponse processProfileRequest(NewProfileRequest newUser, long userid) {

		StatusDescriptionModel description = new StatusDescriptionModel();
		NewProfileResponse response = new NewProfileResponse();

		List<NewProfileEntity> nearMeUserEntity = newUserDetailsRepo.SearchByEmailId(newUser.getEmailId());

		if (nearMeUserEntity != null) {
			description.setDescription(ConstantManager.UserAlreadyThere.getStatusDescription());
			description.setStatus(ConstantManager.UserAlreadyThere.getStatusCode());
			response.setDescriptionModel(description);
			response.setNewProfile(nearMeUserEntity);
			// return
		} else {

			// TODO
			// Enter user Details in DataBase

		}

		return response;
	}

	@Override
	public NewLoginResponse processNewUser(LoginCredentialsRequest userLoginCredentialsRequest)
			throws Exception {

		StatusDescriptionModel description = new StatusDescriptionModel();
		NewLoginResponse response = new NewLoginResponse();
		response.setDescriptionModel(description);

		LoginCredentialsEntity userLoginCredentialsEntity = authenticationRepo
				.findByEmailIdAndRole(userLoginCredentialsRequest.getEmailId(),userLoginCredentialsRequest.getRole());

		if (userLoginCredentialsEntity != null) {

			description.setDescription(ConstantManager.UserAlreadyThere.getStatusDescription());
			description.setStatus(ConstantManager.UserAlreadyThere.getStatusCode());
			response.setLoginCred(userLoginCredentialsEntity);
			return response;
		}

		else {
			boolean isValidEmail = emailValidator.validateEmail(userLoginCredentialsRequest.getEmailId());
			LoginCredentialsEntity loginCredentials1 = new LoginCredentialsEntity();

			if (isValidEmail) {
				String encryptedPass = keyEncrypterDecrypter.KeyEncrypte(userLoginCredentialsRequest.getEmailId(),
						userLoginCredentialsRequest.getPassword());
				
				loginCredentials1.setEmailId(userLoginCredentialsRequest.getEmailId());
				loginCredentials1.setPassword(encryptedPass);
				loginCredentials1.setRole(userLoginCredentialsRequest.getRole());
				
				authenticationRepo.save(loginCredentials1);
				
				description.setDescription(ConstantManager.UserRegistered.getStatusDescription());
				description.setStatus(ConstantManager.UserRegistered.getStatusCode());
				
				sendRegistrationMail.sendMail( userLoginCredentialsRequest.getEmailId(), 
						environment.getProperty("sender.from.mail") , 
						environment.getProperty("sender.from.pass"),
						environment.getProperty("mail.subject"),
				        environment.getProperty("mail.body")
						);
				
				loginCredentials1.setPassword(userLoginCredentialsRequest.getPassword());
				response.setLoginCred(loginCredentials1);
				return response;
			} else {
				return response;
			}
		}

	}

}
