package com.nearme.service.impl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.jwk.RsaJsonWebKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearme.dao.AuthenticationRepo;
import com.nearme.dao.TokenDetailsRepo;
import com.nearme.entity.LoginCredentialsEntity;
import com.nearme.entity.TokenEntity;
import com.nearme.model.JwtModel;
import com.nearme.model.StatusDescriptionModel;
import com.nearme.request.LoginCredentialsRequest;
import com.nearme.response.LoginResponse;
import com.nearme.service.AuthenticationForUser;
import com.nearme.utilities.ConstantManager;
import com.nearme.utilities.DateTimeHelperUtils;
import com.nearme.utilities.KeyEncrypterDecrypter;

@Service
public class AuthenticationForUserImpl implements AuthenticationForUser {

	private static final Logger logger = LogManager.getLogger(AuthenticationForUserImpl.class);
	
	
	@Autowired
	AuthenticationRepo authenticationRepo;
	
	@Autowired
	JwtFactoryServiceImpl jwtFactoryServiceImpl;
	
	@Autowired
	TokenDetailsRepo userTokenDetailsRepo;
	
	@Autowired
	KeyEncrypterDecrypter keyEncrypterDecrypter;
	
	@Override
	public LoginResponse checkUserCredentials(LoginCredentialsRequest userLoginCredentialsRequest , RsaJsonWebKey senderJwk ) throws Exception {
		
		logger.trace("Inside Function checkuserCredentials");
		
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		LoginResponse response = new LoginResponse();
		response.setDescriptionModel(statusDescriptionModel);
		
		LoginCredentialsEntity userLoginCredentialsEntity = authenticationRepo.findByEmailIdAndRole(userLoginCredentialsRequest.getEmailId() , userLoginCredentialsRequest.getRole());
		
		if(userLoginCredentialsEntity != null) {

				String decryptedpass = keyEncrypterDecrypter.KeyDecrypter(userLoginCredentialsEntity.getPassword());
		        if(decryptedpass.equals(userLoginCredentialsRequest.getPassword())){

		        	
		        	String transactionId = generateTransactionId();
					JwtModel jwtNewToken = jwtFactoryServiceImpl.generateJWT(userLoginCredentialsEntity, transactionId,
							senderJwk);

					JwtModel jwtRefereshToken = jwtFactoryServiceImpl.generateRefreshJWT(userLoginCredentialsEntity,
							transactionId, senderJwk);

					TokenEntity tokenEntity = updateJWTToken(userLoginCredentialsEntity, jwtNewToken,
							jwtRefereshToken, "WEB");

		        	
		        	
		        	statusDescriptionModel.setDescription(ConstantManager.LoginSuccesfull.getStatusDescription());
					statusDescriptionModel.setStatus(ConstantManager.LoginSuccesfull.getStatusCode());
					userLoginCredentialsEntity.setPassword(userLoginCredentialsRequest.getPassword());
					response.setLoginCred(userLoginCredentialsEntity);
					response.setTokenEntity(tokenEntity);
					return response;
					
		        }else {
		        	statusDescriptionModel.setDescription(ConstantManager.InvalidPassword.getStatusDescription());
					statusDescriptionModel.setStatus(ConstantManager.InvalidPassword.getStatusCode());
					return response;
		        }
		}
		else {
			
			logger.debug("User Is Invalid");
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

	private TokenEntity updateJWTToken(LoginCredentialsEntity loginCredentialsEntity, JwtModel jwtNewToken, JwtModel jwtRefereshToken,
			String source) {

		TokenEntity userTokenEntity = null;

		try {
			userTokenEntity = userTokenDetailsRepo.findOneByLid(loginCredentialsEntity.getId());

			if (userTokenEntity != null && jwtNewToken != null && jwtRefereshToken != null) {

				userTokenEntity.setCurrentDate(new Date());
				userTokenEntity.setJwtRefreshToken(jwtRefereshToken.getJwtRefreshToken());
				userTokenEntity.setRefreshExpiryDate(jwtRefereshToken.getRefreshExpiryDate());
				userTokenEntity.setRefreshCurrentDate(new Date());
				userTokenEntity.setJwtToken(jwtNewToken.getJwtToken());
				userTokenEntity.setExpiryDate(jwtNewToken.getExpiryDate());
				userTokenEntity.setTokenKey(jwtNewToken.getTokenKey());
				userTokenEntity.setExpireDate(jwtNewToken.getExpiryDate());
				userTokenEntity.setSource(source);
				userTokenEntity.setRefreshKey(jwtRefereshToken.getRefreshKey());
				userTokenEntity.setJwtTokenExpiryDateTime(
						DateTimeHelperUtils.convertDateToString(userTokenEntity.getExpiryDate()));
				userTokenEntity.setJwtRefreshTokenExpiryDateTime(
						DateTimeHelperUtils.convertDateToString(userTokenEntity.getRefreshExpiryDate()));
				userTokenEntity.setLoginCredentialsEntity(loginCredentialsEntity);
				
				userTokenDetailsRepo.save(userTokenEntity);
				
				return userTokenEntity;
				
			} else {
				TokenEntity userTokenEntity1 = new TokenEntity();

				if (jwtNewToken != null && jwtRefereshToken != null) {

					userTokenEntity1.setCurrentDate(new Date());
					userTokenEntity1.setJwtRefreshToken(jwtRefereshToken.getJwtRefreshToken());
					userTokenEntity1.setRefreshExpiryDate(jwtRefereshToken.getRefreshExpiryDate());
					userTokenEntity1.setRefreshCurrentDate(new Date());
					userTokenEntity1.setJwtToken(jwtNewToken.getJwtToken());
					userTokenEntity1.setExpiryDate(jwtNewToken.getExpiryDate());
					userTokenEntity1.setTokenKey(jwtNewToken.getTokenKey());
					userTokenEntity1.setExpireDate(jwtNewToken.getExpiryDate());
					userTokenEntity1.setSource(source);
					userTokenEntity1.setRefreshKey(jwtRefereshToken.getRefreshKey());

					userTokenEntity1.setJwtTokenExpiryDateTime(
							DateTimeHelperUtils.convertDateToString(userTokenEntity1.getExpiryDate()));
					userTokenEntity1.setJwtRefreshTokenExpiryDateTime(
							DateTimeHelperUtils.convertDateToString(userTokenEntity1.getRefreshExpiryDate()));
					userTokenEntity1.setLoginCredentialsEntity(loginCredentialsEntity);
				}

				userTokenDetailsRepo.save(userTokenEntity1);
				return userTokenEntity1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return userTokenEntity;
		}

	}
}
