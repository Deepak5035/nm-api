package com.nearme.service.impl;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.jose4j.base64url.Base64;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearme.dao.TokenDetailsRepo;
import com.nearme.entity.LoginCredentialsEntity;
import com.nearme.entity.TokenEntity;
import com.nearme.model.JwtModel;
import com.nearme.service.JwtFactoryService;
import com.nearme.utilities.Configurator;


@Service
public class JwtFactoryServiceImpl implements JwtFactoryService{


	private final String JWT_ISSUER_NEW_TOKEN = "Nm_Cms_Validate_Token";
	private final String JWT_ISSUER_REFERESH_TOKEN = "Nm_Cms_Referesh_Token";
	private float JWT_EXPIRY_TIME = 30000000;
	private Configurator config = Configurator.getInstance();
	private float JWT_REFRESH_EXPIRY_TIME = 525610;

	
	@Autowired
	TokenDetailsRepo userTokenDetailsRepo;
	
	@Override
	public JwtModel generateJWT(LoginCredentialsEntity loginCredentials, String transactionId, RsaJsonWebKey senderJwk) {
		String jwt = "";
		JwtModel jwtReqInstance = null;
		try {

			JwtClaims claims = new JwtClaims();
			claims.setIssuer(JWT_ISSUER_NEW_TOKEN);
			claims.setExpirationTimeMinutesInTheFuture(JWT_EXPIRY_TIME);
			claims.setGeneratedJwtId();
			claims.setIssuedAtToNow();
			claims.setNotBeforeMinutesInThePast(2);
			claims.setSubject(loginCredentials.getId().toString());
			
			JsonWebSignature jws = new JsonWebSignature();

			jws.setHeader("type", "JWT");
			jws.setHeader("kid", transactionId);

			jws.setPayload(claims.toJson());

			jws.setKey(senderJwk.getRsaPrivateKey());

			jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA512);

			jwt = jws.getCompactSerialization();

			Date expiryDate = new Date(claims.getExpirationTime().getValueInMillis());

			String key = Base64.encode(senderJwk.getRsaPublicKey().getEncoded());

			jwtReqInstance = new JwtModel();
			jwtReqInstance.setUsername(loginCredentials.getEmailId());
			jwtReqInstance.setTransactionId(transactionId);
			jwtReqInstance.setExpiryDate(expiryDate);
			jwtReqInstance.setJwtToken(jwt);
			jwtReqInstance.setTokenKey(key);

			return jwtReqInstance;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public JwtModel generateRefreshJWT(LoginCredentialsEntity loginCredentialsEntity, String transactionId, RsaJsonWebKey senderJwk) {
		String jwt = "";
		JwtModel jwtReqInstance = null;
		try {

			JwtClaims claims = new JwtClaims();
			claims.setIssuer(JWT_ISSUER_REFERESH_TOKEN);
			claims.setExpirationTimeMinutesInTheFuture(JWT_REFRESH_EXPIRY_TIME);
			claims.setGeneratedJwtId();
			claims.setIssuedAtToNow();
			claims.setNotBeforeMinutesInThePast(2);
			claims.setSubject(loginCredentialsEntity.getId().toString());

			JsonWebSignature jws = new JsonWebSignature();

			jws.setHeader("type", "JWT");
			jws.setHeader("kid", transactionId);

			jws.setPayload(claims.toJson());

			jws.setKey(senderJwk.getRsaPrivateKey());

			jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA512);

			jwt = jws.getCompactSerialization();

			Date refreshExpiryDate = new Date(claims.getExpirationTime().getValueInMillis());

			String refreshKey = Base64.encode(senderJwk.getRsaPublicKey().getEncoded());

			jwtReqInstance = new JwtModel();
			jwtReqInstance.setUsername(loginCredentialsEntity.getEmailId());
			jwtReqInstance.setTransactionId(transactionId);
			jwtReqInstance.setRefreshExpiryDate(refreshExpiryDate);
			jwtReqInstance.setJwtRefreshToken(jwt);
			jwtReqInstance.setRefreshKey(refreshKey);
			return jwtReqInstance;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean validateToken(RsaJsonWebKey senderJwk, String token, Long id, String source, String resource,
			String ip, HttpServletRequest req) {
		boolean isValidToken = false, isAllowed = true;
		try {
			long startTime = System.currentTimeMillis();

			if (isAllowed) {

				RsaJsonWebKey validateJwk = validateInDBToken(senderJwk, token, id, source);
				if (validateJwk.getAlgorithm().contains("Token Verified Successfully")) {
					isValidToken = true;
				} else {
					// requestsInterceptorsService.save(userId, token, source);
				}
			}
			long duration = (System.currentTimeMillis() - startTime);
             
			System.out.println(duration);

		} catch (Exception e) {
			 e.printStackTrace();
		}
		return isValidToken;
	}
	
	public boolean filterIP(HttpServletRequest req) {
		boolean isAllowed=false;
		try {
			String allowedIPs=config.getProperty("allowedIPs");
		
			
			String ipInHeader=req.getHeader("x-forwarded-for");
			
			String ipInRequest=req.getRemoteAddr();
		
			if(allowedIPs.contains(ipInRequest)) {
				isAllowed=true;
			}
			
			if(ipInHeader!=null) {
				
				if(allowedIPs.contains(ipInHeader.trim())) {
					isAllowed=true;
				}
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return isAllowed;
	}
	
	private final RsaJsonWebKey validateInDBToken(RsaJsonWebKey senderJwk, String token, Long id, String source) {

		JwtConsumer jwtConsumer = null;
		JwtClaims jwtClaims = null;
		// For handling jwt invalid signature when tomcat restarts
		try {
			
			TokenEntity userTokenEntity = userTokenDetailsRepo.findOneByLid(id);

			if (userTokenEntity != null) {

				if (userTokenEntity.getSource().equalsIgnoreCase(source) && userTokenEntity.getJwtToken().equals(token)) {
					X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decode(userTokenEntity.getTokenKey()));
					KeyFactory keyFactory = KeyFactory.getInstance("RSA");
					RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);

					senderJwk = new RsaJsonWebKey(publicKey);
					jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime().setAllowedClockSkewInSeconds(1)
							.setRequireSubject().setExpectedIssuer(JWT_ISSUER_NEW_TOKEN)
							.setVerificationKey(senderJwk.getRsaPublicKey()).build();
					jwtClaims = jwtConsumer.processToClaims(token);

					System.out.println("jwtClaims.getIssuer(): " + jwtClaims.getIssuer());

					if (!jwtClaims.getIssuer().equalsIgnoreCase("")) {
						System.out.println("Inside Block ");

						senderJwk.setAlgorithm("JWT Token Verified Successfully !!");
					}

					System.out.println("Verification Successful Catc :: " + jwtClaims);
				} else {
					senderJwk.setAlgorithm("The JWT is no longer valid");
				}
			} else {
				senderJwk.setAlgorithm("The JWT is no longer valid");
			}
		} catch (InvalidJwtException invalidJwtException) {
			System.out.println(invalidJwtException.getMessage());
			if (invalidJwtException.getMessage().contains("The JWT is no longer valid - the evaluation time")
					|| invalidJwtException.getMessage().contains("JWS signature is invalid")
					|| invalidJwtException.getMessage().contains("rejected due to invalid claims")) {
				senderJwk.setAlgorithm("JWT Token is no longer valid");
			}

		} catch (Exception exdb) {

			exdb.printStackTrace();
			senderJwk.setAlgorithm(exdb.getMessage());
		}

		return senderJwk;

	}

}
