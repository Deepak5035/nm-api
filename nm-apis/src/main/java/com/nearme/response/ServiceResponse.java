package com.nearme.response;

import java.util.List;

import com.nearme.entity.ServiceEntity;
import com.nearme.model.StatusDescriptionModel;

public class ServiceResponse {

	StatusDescriptionModel statusDescriptionModel;
	List<ServiceEntity> ServiceEntity;
	
	public StatusDescriptionModel getStatusDescriptionModel() {
		return statusDescriptionModel;
	}
	public void setStatusDescriptionModel(StatusDescriptionModel statusDescriptionModel) {
		this.statusDescriptionModel = statusDescriptionModel;
	}
	public List<ServiceEntity> getServiceEntity() {
		return ServiceEntity;
	}
	public void setServiceEntity(List<ServiceEntity> cleaningServiceEntity) {
		this.ServiceEntity = cleaningServiceEntity;
	}
}
