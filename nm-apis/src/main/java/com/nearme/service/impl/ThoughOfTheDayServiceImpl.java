package com.nearme.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearme.model.ChoiceModel;
import com.nearme.model.JasonResponseTextModel;
import com.nearme.model.StatusDescriptionModel;
import com.nearme.response.ThoughtOfTheDayResponse;
import com.nearme.service.ThoughtOfTheDayService;
import com.nearme.utilities.ConstantManager;



@Service
public class ThoughOfTheDayServiceImpl implements ThoughtOfTheDayService {

	@Override
	public ThoughtOfTheDayResponse getThoughtOfTheDay() throws Exception {
		
		String thoughtOfTheDay = null;
		
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		ThoughtOfTheDayResponse response = new ThoughtOfTheDayResponse();
		response.setStatusDescriptionModel(statusDescriptionModel);
		
		thoughtOfTheDay = getThoughtOfTheDayfromOpenAi();
		
		if(thoughtOfTheDay != null ) {
            
			statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
			statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());
		    response.setThoughtOfTheDay(thoughtOfTheDay);
		}else {
			statusDescriptionModel.setDescription(ConstantManager.ServerError.getStatusDescription());
			statusDescriptionModel.setStatus(ConstantManager.ServerError.getStatusCode());
		    response.setThoughtOfTheDay(thoughtOfTheDay);
			
		}
		
		return response;
	}
	
	
	public String getThoughtOfTheDayfromOpenAi() throws Exception {

		String thoughtOfTheDay = null;
		String token = "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";
		String model = "text-davinci-003";
		String prompt = "Thought For The Day Diffrent From Previous One";
		int maxTokens = 4000;
		double temperature = 0;
		String apiUrl = "https://api.openai.com/v1/completions";
		String jsonInputString = String.format(
				"{\"model\":\"%s\",\"prompt\":\"%s\",\"max_tokens\":%d,\"temperature\":%f}", model, prompt, maxTokens,
				temperature);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<String> request = new HttpEntity<>(jsonInputString, headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, request, String.class);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			String jsonResponse = responseEntity.getBody();

			ObjectMapper objectMapper = new ObjectMapper();
			JasonResponseTextModel jasonResponseTextModel = objectMapper.readValue(jsonResponse,JasonResponseTextModel.class);
			
			for(ChoiceModel choiceModel :jasonResponseTextModel.getChoices()) {
				
				thoughtOfTheDay = choiceModel.getText();
			}

		} else {
			System.err.println("Error while making API request. Response code: " + responseEntity.getStatusCode());
		}

		return thoughtOfTheDay;
	}

}
