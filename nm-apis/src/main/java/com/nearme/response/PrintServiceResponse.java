package com.nearme.response;


import com.nearme.model.StatusDescriptionModel;

public class PrintServiceResponse {

	StatusDescriptionModel statusDescriptionModel;
	String pdfReportPath;
	
	public StatusDescriptionModel getStatusDescriptionModel() {
		return statusDescriptionModel;
	}
	public void setStatusDescriptionModel(StatusDescriptionModel statusDescriptionModel) {
		this.statusDescriptionModel = statusDescriptionModel;
	}
	public String getPdfReportPath() {
		return pdfReportPath;
	}
	public void setPdfReportPath(String pdfReportPath) {
		this.pdfReportPath = pdfReportPath;
	}
	
}
