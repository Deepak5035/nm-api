package com.nearme.model;

import java.util.List;

public class JasonResponseTextModel {

	private String id;
    private String object;
    private long created;
    private String model;
    private List<ChoiceModel> choices;
    private UsageModel usage;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public List<ChoiceModel> getChoices() {
		return choices;
	}
	public void setChoices(List<ChoiceModel> choices) {
		this.choices = choices;
	}
	public UsageModel getUsage() {
		return usage;
	}
	public void setUsage(UsageModel usage) {
		this.usage = usage;
	}
	
}
