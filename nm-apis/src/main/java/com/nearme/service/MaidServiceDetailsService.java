package com.nearme.service;

import com.nearme.request.ServiceRequest;
import com.nearme.response.ServiceResponse;

public interface MaidServiceDetailsService {

	public ServiceResponse maidServiceDetails(String serviceType);

	public ServiceResponse maidVendorDetails(ServiceRequest maidServiceRequest);
	

}
