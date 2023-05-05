package com.nearme.service;

import com.nearme.request.ServiceRequest;
import com.nearme.response.ServiceResponse;

public interface CleaningServiceDetailsService {

	public ServiceResponse cleaningServiceDetails(String serviceType);

	public ServiceResponse cleaningVendorDetails(ServiceRequest waterServiceRequest);
	
}
