package com.nearme.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nearme.entity.LoginCredentialsEntity;

@Repository
@Transactional
public interface AuthenticationRepo extends JpaRepository<LoginCredentialsEntity, Long> {

	//@Query(value = "Select * from tbl_login_details where emailId = : emailId and role : = role ", nativeQuery = true)
	public LoginCredentialsEntity findByEmailIdAndRole(String emailId , String role);
	
}
