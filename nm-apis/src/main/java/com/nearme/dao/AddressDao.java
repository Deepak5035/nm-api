package com.nearme.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nearme.entity.AddressEntity;

public interface AddressDao extends  JpaRepository<AddressEntity, Long>{

}
