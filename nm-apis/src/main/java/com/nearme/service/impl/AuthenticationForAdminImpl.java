package com.nearme.service.impl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.jwk.RsaJsonWebKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.nearme.dao.AuthenticationRepo;
import com.nearme.dao.TokenDetailsRepo;
import com.nearme.entity.LoginCredentialsEntity;
import com.nearme.entity.TokenEntity;
import com.nearme.model.JwtModel;
import com.nearme.model.StatusDescriptionModel;
import com.nearme.request.LoginCredentialsRequest;
import com.nearme.response.LoginResponse;
import com.nearme.service.AuthenticationForAdmin;
import com.nearme.utilities.ConstantManager;
import com.nearme.utilities.DateTimeHelperUtils;
import com.nearme.utilities.KeyEncrypterDecrypter;
import com.nearme.utilities.SendRegistrationMail;

@Service
public class AuthenticationForAdminImpl implements AuthenticationForAdmin {

	private static final Logger logger = LogManager.getLogger(AuthenticationForUserImpl.class);

	@Autowired
	AuthenticationRepo adminAuthenticationRepo;

	@Autowired
	JwtFactoryServiceImpl jwtFactoryServiceImpl;

	@Autowired
	TokenDetailsRepo adminTokenDetailsRepo;
	
	@Autowired
	KeyEncrypterDecrypter keyEncrypterDecrypter;
	
	@Autowired
	SendRegistrationMail sendRegistrationMail;
	
	@Autowired
	Environment environment;
	

	@Override
	public LoginResponse checkAdminCredentials(LoginCredentialsRequest loginCredentials,
			RsaJsonWebKey senderJwk) throws Exception {

		logger.trace("Inside Function checkAdminCredentials");

		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		LoginResponse response = new LoginResponse();
		response.setDescriptionModel(statusDescriptionModel);

		LoginCredentialsEntity AdminLoginCredentialsEntity = adminAuthenticationRepo.findByEmailIdAndRole(loginCredentials.getEmailId() , loginCredentials.getRole());

		if (AdminLoginCredentialsEntity != null) {

			String decryptedpass =keyEncrypterDecrypter.KeyDecrypter(AdminLoginCredentialsEntity.getPassword());
			if (decryptedpass.equals(loginCredentials.getPassword())) {

				String transactionId = generateTransactionId();
				JwtModel jwtNewToken = jwtFactoryServiceImpl.generateJWT(AdminLoginCredentialsEntity, transactionId,
						senderJwk);

				JwtModel jwtRefereshToken = jwtFactoryServiceImpl.generateRefreshJWT(AdminLoginCredentialsEntity,
						transactionId, senderJwk);

				TokenEntity tokenEntity = updateJWTToken(AdminLoginCredentialsEntity, jwtNewToken,
						jwtRefereshToken, "WEB");

				statusDescriptionModel.setDescription(ConstantManager.LoginSuccesfull.getStatusDescription());
				statusDescriptionModel.setStatus(ConstantManager.LoginSuccesfull.getStatusCode());
				
				AdminLoginCredentialsEntity.setPassword(loginCredentials.getPassword());
				
				response.setLoginCred(AdminLoginCredentialsEntity);
				response.setTokenEntity(tokenEntity);
				
				return response;

			} else {
				statusDescriptionModel.setDescription(ConstantManager.InvalidPassword.getStatusDescription());
				statusDescriptionModel.setStatus(ConstantManager.InvalidPassword.getStatusCode());
				response.setDescriptionModel(statusDescriptionModel);
				return response;
			}
		} else {

			logger.debug("Admin Is Invalid");
			statusDescriptionModel.setDescription(ConstantManager.InvalidUserEmail.getStatusDescription());
			statusDescriptionModel.setStatus(ConstantManager.InvalidUserEmail.getStatusCode());
			response.setDescriptionModel(statusDescriptionModel);
			return response;
		}
	}

	public String generateTransactionId() {
		int otp = 0;
		try {
			otp = (int) (Math.random() * 9000) + 12163;

		} catch (Exception e) {
			e.printStackTrace();
		}
		Long txnid = System.currentTimeMillis() + otp;
		return "" + txnid;
	}

	private TokenEntity updateJWTToken(LoginCredentialsEntity loginCredentials, JwtModel jwtNewToken, JwtModel jwtRefereshToken,
			String source) {

		TokenEntity adminTokenEntity = null;

		try {
			adminTokenEntity = adminTokenDetailsRepo.findOneByLid(loginCredentials.getId());

			if (adminTokenEntity != null && jwtNewToken != null && jwtRefereshToken != null) {

				adminTokenEntity.setCurrentDate(new Date());
				adminTokenEntity.setJwtRefreshToken(jwtRefereshToken.getJwtRefreshToken());
				adminTokenEntity.setRefreshExpiryDate(jwtRefereshToken.getRefreshExpiryDate());
				adminTokenEntity.setRefreshCurrentDate(new Date());
				adminTokenEntity.setJwtToken(jwtNewToken.getJwtToken());
				adminTokenEntity.setExpiryDate(jwtNewToken.getExpiryDate());
				adminTokenEntity.setTokenKey(jwtNewToken.getTokenKey());
				adminTokenEntity.setExpireDate(jwtNewToken.getExpiryDate());
				adminTokenEntity.setSource(source);
				adminTokenEntity.setRefreshKey(jwtRefereshToken.getRefreshKey());
				adminTokenEntity.setJwtTokenExpiryDateTime(
						DateTimeHelperUtils.convertDateToString(adminTokenEntity.getExpiryDate()));
				adminTokenEntity.setJwtRefreshTokenExpiryDateTime(
						DateTimeHelperUtils.convertDateToString(adminTokenEntity.getRefreshExpiryDate()));
				
				adminTokenEntity.setLoginCredentialsEntity(loginCredentials);
				
				adminTokenDetailsRepo.save(adminTokenEntity);
				
				return adminTokenEntity;
				
			} else {
				TokenEntity adminTokenEntity1 = new TokenEntity();

				if (jwtNewToken != null && jwtRefereshToken != null) {

					adminTokenEntity1.setCurrentDate(new Date());
					adminTokenEntity1.setJwtRefreshToken(jwtRefereshToken.getJwtRefreshToken());
					adminTokenEntity1.setRefreshExpiryDate(jwtRefereshToken.getRefreshExpiryDate());
					adminTokenEntity1.setRefreshCurrentDate(new Date());
					adminTokenEntity1.setJwtToken(jwtNewToken.getJwtToken());
					adminTokenEntity1.setExpiryDate(jwtNewToken.getExpiryDate());
					adminTokenEntity1.setTokenKey(jwtNewToken.getTokenKey());
					adminTokenEntity1.setExpireDate(jwtNewToken.getExpiryDate());
					adminTokenEntity1.setSource(source);
					adminTokenEntity1.setRefreshKey(jwtRefereshToken.getRefreshKey());

					adminTokenEntity1.setJwtTokenExpiryDateTime(
							DateTimeHelperUtils.convertDateToString(adminTokenEntity1.getExpiryDate()));
					adminTokenEntity1.setJwtRefreshTokenExpiryDateTime(
							DateTimeHelperUtils.convertDateToString(adminTokenEntity1.getRefreshExpiryDate()));
					adminTokenEntity1.setLoginCredentialsEntity(loginCredentials);
				}

				adminTokenDetailsRepo.save(adminTokenEntity1);
				return adminTokenEntity1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return adminTokenEntity;
		}

	}

    public LoginResponse forgetPassword(LoginCredentialsRequest loginCredentialsRequest) {
    	
    	StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
    	LoginResponse response = new LoginResponse();
    	
    	LoginCredentialsEntity loginCredentialsEntity = adminAuthenticationRepo.findByEmailIdAndRole(loginCredentialsRequest.getEmailId(),loginCredentialsRequest.getRole());
    	
		if (loginCredentialsEntity != null) {

			String emailSubject = "Near me Portel Password";
			
			String decryptedPassword = keyEncrypterDecrypter.KeyDecrypter(loginCredentialsEntity.getPassword());
			
			String emailBody = "Your Password is " + decryptedPassword + "Don't Share Your Password With Any One";

			sendRegistrationMail.sendMail(loginCredentialsEntity.getEmailId(),
					environment.getProperty("sender.from.mail"), environment.getProperty("sender.from.pass"),
					emailSubject, emailBody);
			
			statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
			statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());
			response.setLoginCred(loginCredentialsEntity);
			
			
		}else {
			statusDescriptionModel.setDescription(ConstantManager.InvalidUserEmail.getStatusDescription());
			statusDescriptionModel.setStatus(ConstantManager.InvalidUserEmail.getStatusCode());
			response.setLoginCred(loginCredentialsEntity);
		}

    	return response;
    }
}
