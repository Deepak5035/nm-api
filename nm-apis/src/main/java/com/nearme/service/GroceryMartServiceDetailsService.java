package com.nearme.service;

import com.nearme.request.ServiceRequest;
import com.nearme.response.ServiceResponse;

public interface GroceryMartServiceDetailsService {

	public ServiceResponse groceryMartServiceDetails(String serviceType);

	public ServiceResponse groceryMartVendorDetails(ServiceRequest waterServiceRequest);

	
}
