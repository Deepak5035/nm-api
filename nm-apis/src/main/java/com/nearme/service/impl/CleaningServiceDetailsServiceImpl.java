package com.nearme.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearme.dao.AddressDao;
import com.nearme.dao.ServiceDetailsServiceRepo;
import com.nearme.entity.AddressEntity;
import com.nearme.entity.ServiceEntity;
import com.nearme.model.StatusDescriptionModel;
import com.nearme.request.ServiceRequest;
import com.nearme.response.ServiceResponse;
import com.nearme.service.CleaningServiceDetailsService;
import com.nearme.utilities.ConstantManager;

@Service
public class CleaningServiceDetailsServiceImpl implements CleaningServiceDetailsService {

	@Autowired
	ServiceDetailsServiceRepo cleaningServiceDetailsServiceRepo;
	
	@Autowired
	AddressDao addressDao;

	@Override
	public ServiceResponse cleaningServiceDetails(String serviceType) {

		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		ServiceResponse response = new ServiceResponse();
		response.setStatusDescriptionModel(statusDescriptionModel);

		try {
			List<ServiceEntity> cleaningServiceEntities = cleaningServiceDetailsServiceRepo.getServiceData(serviceType);

			if (cleaningServiceEntities != null) {

				response.setServiceEntity(cleaningServiceEntities);
				statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
				statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());

			} else {
				response.setServiceEntity(cleaningServiceEntities);
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
	public ServiceResponse cleaningVendorDetails(ServiceRequest cleaningServiceRequest) {
		
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		ServiceResponse response = new ServiceResponse();
		response.setStatusDescriptionModel(statusDescriptionModel);
		
		ServiceEntity cleaningServiceEntity1 = new ServiceEntity();
		cleaningServiceEntity1 =  cleaningServiceDetailsServiceRepo.findByMobileNumAndServiceType(cleaningServiceRequest.getMobileNum(),cleaningServiceRequest.getServiceType());
		
		if(cleaningServiceEntity1!=null) {
           statusDescriptionModel.setDescription(ConstantManager.RecordWithSameNumThere.getStatusDescription());
           statusDescriptionModel.setStatus(ConstantManager.RecordWithSameNumThere.getStatusCode());
		}
		else {
			ServiceEntity cleaningServiceEntity = new ServiceEntity();
			AddressEntity addressEntity = new AddressEntity();
			
			cleaningServiceEntity.setName(cleaningServiceRequest.getName());
			cleaningServiceEntity.setMobileNum(cleaningServiceRequest.getMobileNum());
			cleaningServiceEntity.setAvailable(cleaningServiceRequest.isAvailable());
			cleaningServiceEntity.setServiceType(cleaningServiceRequest.getServiceType());
			
			cleaningServiceDetailsServiceRepo.save(cleaningServiceEntity);
			
			//setting Address
            addressEntity.setHouseNumber(cleaningServiceRequest.getAddress().getHouseNumber());
            addressEntity.setCity(cleaningServiceRequest.getAddress().getCity());
			addressEntity.setPinCode(cleaningServiceRequest.getAddress().getPinCode());
			addressEntity.setState(cleaningServiceRequest.getAddress().getState());
			addressEntity.setCountry(cleaningServiceRequest.getAddress().getCountry());
			addressEntity.setService(cleaningServiceEntity);

			addressDao.save(addressEntity);
			 
			statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
	        statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());

	        cleaningServiceEntity.setAddress(addressEntity);
	        List<ServiceEntity> entities = new ArrayList<>();
	        entities.add(cleaningServiceEntity);
	        response.setServiceEntity(entities);
		}
		
		return response;
	}
	
	public Long getRandomId() {
		
		 int digitCount = 6; 
	        long min = (long) Math.pow(10, digitCount - 1); 
	        long max = (long) Math.pow(10, digitCount) - 1; 

	        Random random = new Random();
	        long randomLong = min + ((long) (random.nextDouble() * (max - min)));
		
		return randomLong;
	}
}
