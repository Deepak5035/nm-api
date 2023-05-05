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
import com.nearme.service.MaidServiceDetailsService;
import com.nearme.utilities.ConstantManager;

@Service
public class MaidServiceDetailsServiceImpl implements MaidServiceDetailsService {

	@Autowired
	ServiceDetailsServiceRepo maidServiceDetailsServiceRepo;

	@Autowired
	AddressDao addressDao;
	
	@Override
	public ServiceResponse maidServiceDetails(String serviceType) {

		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		ServiceResponse response = new ServiceResponse();
		response.setStatusDescriptionModel(statusDescriptionModel);

		try {
			List<ServiceEntity> maidServiceEntities = maidServiceDetailsServiceRepo.getServiceData(serviceType);

			if (maidServiceEntities != null) {

				response.setServiceEntity(maidServiceEntities);
				statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
				statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());

			} else {
				response.setServiceEntity(maidServiceEntities);
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
	public ServiceResponse maidVendorDetails(ServiceRequest maidServiceRequest) {
		
		StatusDescriptionModel statusDescriptionModel = new StatusDescriptionModel();
		ServiceResponse response = new ServiceResponse();
		response.setStatusDescriptionModel(statusDescriptionModel);
		
		ServiceEntity maidServiceEntity1 = new ServiceEntity();
		
		maidServiceEntity1 =  maidServiceDetailsServiceRepo.findByMobileNumAndServiceType(maidServiceRequest.getMobileNum(),maidServiceRequest.getServiceType());
		
		if(maidServiceEntity1!=null) {
           statusDescriptionModel.setDescription(ConstantManager.RecordWithSameNumThere.getStatusDescription());
           statusDescriptionModel.setStatus(ConstantManager.RecordWithSameNumThere.getStatusCode());
		}
		else {
			ServiceEntity maidServiceEntity = new ServiceEntity();
			AddressEntity addressEntity = new AddressEntity();
			
			maidServiceEntity.setName(maidServiceRequest.getName());
			maidServiceEntity.setMobileNum(maidServiceRequest.getMobileNum());
			maidServiceEntity.setAvailable(maidServiceRequest.isAvailable());
			maidServiceEntity.setServiceType(maidServiceRequest.getServiceType());
			
			maidServiceDetailsServiceRepo.save(maidServiceEntity);
			 
			//setting Address
            addressEntity.setHouseNumber(maidServiceRequest.getAddress().getHouseNumber());
            addressEntity.setCity(maidServiceRequest.getAddress().getCity());
			addressEntity.setPinCode(maidServiceRequest.getAddress().getPinCode());
			addressEntity.setState(maidServiceRequest.getAddress().getState());
			addressEntity.setCountry(maidServiceRequest.getAddress().getCountry());
			addressEntity.setService(maidServiceEntity);

			addressDao.save(addressEntity);
			
			maidServiceEntity.setAddress(addressEntity);
			
			statusDescriptionModel.setDescription(ConstantManager.Success.getStatusDescription());
	        statusDescriptionModel.setStatus(ConstantManager.Success.getStatusCode());

	        List<ServiceEntity> entities = new ArrayList<>();
	        entities.add(maidServiceEntity);
	        response.setServiceEntity(entities);
		}
		
		return response;
	}
}
