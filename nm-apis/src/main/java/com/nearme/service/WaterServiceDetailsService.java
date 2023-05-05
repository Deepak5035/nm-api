package com.nearme.service;

import com.nearme.request.ServiceRequest;
import com.nearme.response.ServiceResponse;

public interface WaterServiceDetailsService {

	public ServiceResponse waterServiceDetails(String srviceType);

	public ServiceResponse waterVendorDetails(ServiceRequest waterServiceRequest);

}
