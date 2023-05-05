package com.nearme.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nearme.entity.NewProfileEntity;
 

@Repository
@Transactional
public interface NewProfileDetailsRepo extends JpaRepository<NewProfileEntity, Long> {
    
	@Query(value = "Select * from tbl_login_details where user_name=:emailId",nativeQuery = true)
	public List<NewProfileEntity> SearchByEmailId(String emailId);
	
	
}
