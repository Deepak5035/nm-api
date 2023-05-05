package com.nearme.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearme.dao.ServiceDetailsServiceRepo;
import com.nearme.model.StatusDescriptionModel;
import com.nearme.response.PrintServiceResponse;
import com.nearme.service.PrintServices;
import com.nearme.utilities.ConstantManager;
import com.nearme.utilities.GeneratePdf;

@Service
public class PrintServiceImpl implements PrintServices {

	@Autowired
	ServiceDetailsServiceRepo waterServiceDetailsServiceRepo;
	
	@Autowired
	ServiceDetailsServiceRepo maidServiceDetailsServiceRepo;
	
	@Autowired
	ServiceDetailsServiceRepo cleaningServiceDetailsServiceRepo;
	
	@Autowired
	ServiceDetailsServiceRepo groceryMartServiceDetailsServiceRepo;
	
	@Autowired
	GeneratePdf generatePdf;
	
	@Override
	public PrintServiceResponse printServices(String typeOfService) {
		
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		PrintServiceResponse response = new PrintServiceResponse();
		response.setStatusDescriptionModel(statusDescriptionModel);
		
		String pdfPath=null;
		
		switch(typeOfService) {
		
		case "w" :  pdfPath = generatePdf.generatePdfReport(waterServiceDetailsServiceRepo.findAll(),"w");
		            break;
		case "m" :  pdfPath = generatePdf.generatePdfReport(maidServiceDetailsServiceRepo.findAll(),"m");
		            break;
		case "c" :  pdfPath = generatePdf.generatePdfReport(cleaningServiceDetailsServiceRepo.findAll(),"c");
	                break;
		case "g" :  pdfPath = generatePdf.generatePdfReport(groceryMartServiceDetailsServiceRepo.findAll(),"g");
		
		default :   ;
		}
		
		if(pdfPath != "" || pdfPath != null) {
			
			statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
			statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());
			response.setPdfReportPath(pdfPath);
		}
		else {
			statusDescriptionModel.setDescription(ConstantManager.ServerError.getStatusDescription());
			statusDescriptionModel.setStatus(ConstantManager.ServerError.getStatusCode());
		}
		return response;
	}
}
