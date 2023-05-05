package com.nearme.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nearme.entity.TokenEntity;


@Repository
@Transactional
public interface TokenDetailsRepo extends JpaRepository<TokenEntity, Long> {

	@Query(value ="Select * from tbl_token_detail where lid =:lid ", nativeQuery = true)
	TokenEntity findOneByLid(Long lid);
}
