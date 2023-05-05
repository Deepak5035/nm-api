package com.nearme.response;

import com.nearme.model.StatusDescriptionModel;

public class ThoughtOfTheDayResponse {

	StatusDescriptionModel statusDescriptionModel;
	
	String thoughtOfTheDay;

	public StatusDescriptionModel getStatusDescriptionModel() {
		return statusDescriptionModel;
	}

	public void setStatusDescriptionModel(StatusDescriptionModel statusDescriptionModel) {
		this.statusDescriptionModel = statusDescriptionModel;
	}

	public String getThoughtOfTheDay() {
		return thoughtOfTheDay;
	}

	public void setThoughtOfTheDay(String thoughtOfTheDay) {
		this.thoughtOfTheDay = thoughtOfTheDay;
	}
}
