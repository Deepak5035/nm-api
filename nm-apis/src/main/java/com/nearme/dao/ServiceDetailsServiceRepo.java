package com.nearme.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nearme.entity.ServiceEntity;

@Repository
@Transactional
public interface ServiceDetailsServiceRepo extends JpaRepository<ServiceEntity, Long> {

	@Query(value = "Select * from tbl_service where service_type =:serviceType limit 50",nativeQuery = true)
	List<ServiceEntity> getServiceData(String serviceType);
	
	ServiceEntity findByMobileNumAndServiceType(Long mobileNum , String serviceType);
	
}
