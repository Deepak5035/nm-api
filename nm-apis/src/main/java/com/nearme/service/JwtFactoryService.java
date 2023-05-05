package com.nearme.service;

import javax.servlet.http.HttpServletRequest;

import org.jose4j.jwk.RsaJsonWebKey;

import com.nearme.entity.LoginCredentialsEntity;
import com.nearme.model.JwtModel;


public interface JwtFactoryService {

	JwtModel generateJWT(LoginCredentialsEntity loginCredentialsEntity, String transactionId, RsaJsonWebKey senderJwk);

	JwtModel generateRefreshJWT(LoginCredentialsEntity loginCredentialsEntity, String transactionId, RsaJsonWebKey senderJwk);

	public boolean validateToken(RsaJsonWebKey senderJwk, String token, Long id, String source, String resource,
			String ip, HttpServletRequest req) ;

}
