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
import com.nearme.service.WaterServiceDetailsService;
import com.nearme.utilities.ConstantManager;

@Service
public class WaterServiceDetailsServiceImpl implements WaterServiceDetailsService {

	@Autowired
	ServiceDetailsServiceRepo waterServiceDetailsServiceRepo;

	@Autowired
	AddressDao addressDao;
	
	@Override
	public ServiceResponse waterServiceDetails(String serviceType) {

		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		ServiceResponse response = new ServiceResponse();
		response.setStatusDescriptionModel(statusDescriptionModel);

		try {
			List<ServiceEntity> waterServiceEntities = waterServiceDetailsServiceRepo.getServiceData(serviceType);

			if (waterServiceEntities != null) {

				response.setServiceEntity(waterServiceEntities);
				statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
				statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());

			} else {
				response.setServiceEntity(waterServiceEntities);
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

	public ServiceResponse waterVendorDetails(ServiceRequest waterServiceRequest) {

		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		ServiceResponse response = new ServiceResponse();
		response.setStatusDescriptionModel(statusDescriptionModel);

		ServiceEntity waterServiceEntity1 = new ServiceEntity();

		waterServiceEntity1 = waterServiceDetailsServiceRepo.findByMobileNumAndServiceType(waterServiceRequest.getMobileNum(),waterServiceRequest.getServiceType());

		if (waterServiceEntity1 != null) {
			statusDescriptionModel.setDescription(ConstantManager.RecordWithSameNumThere.getStatusDescription());
			statusDescriptionModel.setStatus(ConstantManager.RecordWithSameNumThere.getStatusCode());
		} else {
			ServiceEntity waterServiceEntity = new ServiceEntity();
			AddressEntity addressEntity = new AddressEntity();
			
			
			waterServiceEntity.setName(waterServiceRequest.getName());
			waterServiceEntity.setMobileNum(waterServiceRequest.getMobileNum());
			waterServiceEntity.setAvailable(waterServiceRequest.isAvailable());
			waterServiceEntity.setServiceType(waterServiceRequest.getServiceType());
			waterServiceDetailsServiceRepo.save(waterServiceEntity);

			//setting Address
            addressEntity.setHouseNumber(waterServiceRequest.getAddress().getHouseNumber());
            addressEntity.setCity(waterServiceRequest.getAddress().getCity());
			addressEntity.setPinCode(waterServiceRequest.getAddress().getPinCode());
			addressEntity.setState(waterServiceRequest.getAddress().getState());
			addressEntity.setCountry(waterServiceRequest.getAddress().getCountry());
			addressEntity.setService(waterServiceEntity);

			addressDao.save(addressEntity);
			
			waterServiceEntity.setAddress(addressEntity);
			
			statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
			statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());

			List<ServiceEntity> entities = new ArrayList<>();
			entities.add(waterServiceEntity);
			response.setServiceEntity(entities);
		}

		return response;
	}
}
