package com.nearme.utilities;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nearme.entity.AddressEntity;
import com.nearme.entity.ServiceEntity;

@SpringBootTest
public class testGeneratePdfReport {

	@Autowired
	GeneratePdf generatePdf;
	
	
	@Test
	public void ReportGenerateTest() {
	List<ServiceEntity> servicesList = new ArrayList<>();
	
	AddressEntity address1 = new AddressEntity(1L, "MIG-129", "Gorakhpur", "273007", "UP", "India"); 
	AddressEntity address2 = new AddressEntity(2L, "37/23", "New York", "46782", "New York", "US"); 
	
    ServiceEntity service1 = new ServiceEntity(1L, "Sanjay",6393188838L, true , "cleaning",address1);
    ServiceEntity service2 = new ServiceEntity(2L, "Darsh",9893188830L, false ,"maid",address2);
    
    servicesList.add(service1);
    servicesList.add(service2);

    // Call the generatePdfReport method with the test data
    String pdfPath = generatePdf.generatePdfReport(servicesList, "c");

    // Verify that the method returns a non-null PDF path
    assertNotNull(pdfPath);

    // Verify that the generated PDF file exists
    File pdfFile = new File(pdfPath);
    assertTrue(pdfFile.exists());

    // TODO: Add more tests to verify the contents of the PDF file
	}
}
