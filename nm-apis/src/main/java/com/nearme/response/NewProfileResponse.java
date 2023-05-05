package com.nearme.response;

import java.util.List;

import com.nearme.entity.NewProfileEntity;
import com.nearme.model.StatusDescriptionModel;

public class NewProfileResponse {

	StatusDescriptionModel descriptionModel;
	List<NewProfileEntity> newProfile ;
	
	public StatusDescriptionModel getDescriptionModel() {
		return descriptionModel;
	}
	public void setDescriptionModel(StatusDescriptionModel descriptionModel) {
		this.descriptionModel = descriptionModel;
	}
	public List<NewProfileEntity> getNewProfile() {
		return newProfile;
	}
	public void setNewProfile(List<NewProfileEntity> newProfile) {
		this.newProfile = newProfile;
	}
	
	
	
}
