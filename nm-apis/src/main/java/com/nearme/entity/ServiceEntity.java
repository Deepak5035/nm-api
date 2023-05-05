package com.nearme.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name = "tbl_service")
public class ServiceEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "mobileNum")
	private Long mobileNum;

	@Column(name = "available")
	private boolean available;
	
	@Column(name = "service_type")
	private String serviceType;

	//@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "service")
	@Fetch(FetchMode.JOIN)
	private AddressEntity address;

	public ServiceEntity() {
		super();
	}

	public ServiceEntity(Long id, String name, Long mobileNum, boolean available, String serviceType,
			AddressEntity address) {
		super();
		this.id = id;
		this.name = name;
		this.mobileNum = mobileNum;
		this.available = available;
		this.serviceType = serviceType;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(Long mobileNum) {
		this.mobileNum = mobileNum;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

}
