package com.nearme.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearme.dao.AddressDao;
import com.nearme.dao.ServiceDetailsServiceRepo;
import com.nearme.entity.AddressEntity;
import com.nearme.entity.ServiceEntity;
import com.nearme.model.StatusDescriptionModel;
import com.nearme.request.ServiceRequest;
import com.nearme.response.ServiceResponse;
import com.nearme.service.GroceryMartServiceDetailsService;
import com.nearme.utilities.ConstantManager;

@Service
public class GroceryMartServiceDetailsServiceImpl implements GroceryMartServiceDetailsService {

	@Autowired
	ServiceDetailsServiceRepo groceryMartServiceDetailsServiceRepo;
	
	@Autowired
	AddressDao addressDao;

	@Override
	public ServiceResponse groceryMartServiceDetails(String serviceType) {

		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		ServiceResponse response = new ServiceResponse();
		response.setStatusDescriptionModel(statusDescriptionModel);

		try {
			List<ServiceEntity> groceryMartServiceEntities = groceryMartServiceDetailsServiceRepo.getServiceData(serviceType);

			if (groceryMartServiceEntities != null) {

				response.setServiceEntity(groceryMartServiceEntities);
				statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
				statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());

			} else {
				response.setServiceEntity(groceryMartServiceEntities);
				statusDescriptionModel.setDescription(ConstantManager.NoDataFound.getStatusDescription());
				statusDescriptionModel.setStatus(ConstantManager.NoDataFound.getStatusCode());
			}
		} catch (Exception e) {
			statusDescriptionModel.setDescription(ConstantManager.ServerError.getStatusDescription());
			statusDescriptionModel.setStatus(ConstantManager.ServerError.getStatusCode());
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public ServiceResponse groceryMartVendorDetails(ServiceRequest groceryMartServiceRequest) {
		
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		ServiceResponse response = new ServiceResponse();
		response.setStatusDescriptionModel(statusDescriptionModel);
		
		ServiceEntity groceryMartServiceEntity1 = new ServiceEntity();
		
		groceryMartServiceEntity1 =  groceryMartServiceDetailsServiceRepo.findByMobileNumAndServiceType(groceryMartServiceRequest.getMobileNum(),groceryMartServiceRequest.getServiceType());
		
		if(groceryMartServiceEntity1!=null) {
           statusDescriptionModel.setDescription(ConstantManager.RecordWithSameNumThere.getStatusDescription());
           statusDescriptionModel.setStatus(ConstantManager.RecordWithSameNumThere.getStatusCode());
		}
		else {
			ServiceEntity groceryMartServiceEntity = new ServiceEntity();
			AddressEntity addressEntity = new AddressEntity();
			
			groceryMartServiceEntity.setName(groceryMartServiceRequest.getName());
			groceryMartServiceEntity.setMobileNum(groceryMartServiceRequest.getMobileNum());
			groceryMartServiceEntity.setAvailable(groceryMartServiceRequest.isAvailable());
			groceryMartServiceEntity.setServiceType(groceryMartServiceRequest.getServiceType());
			
			groceryMartServiceDetailsServiceRepo.save(groceryMartServiceEntity);
			
			//setting Address
            addressEntity.setHouseNumber(groceryMartServiceRequest.getAddress().getHouseNumber());
            addressEntity.setCity(groceryMartServiceRequest.getAddress().getCity());
			addressEntity.setPinCode(groceryMartServiceRequest.getAddress().getPinCode());
			addressEntity.setState(groceryMartServiceRequest.getAddress().getState());
			addressEntity.setCountry(groceryMartServiceRequest.getAddress().getCountry());
			addressEntity.setService(groceryMartServiceEntity);

			addressDao.save(addressEntity);
			
			statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
	        statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());

	        groceryMartServiceEntity.setAddress(addressEntity);
	        List<ServiceEntity> entities = new ArrayList<>();
	        entities.add(groceryMartServiceEntity);
	        response.setServiceEntity(entities);
		}
		
		return response;
	}
}
