package com.nearme.utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nearme.entity.AddressEntity;
import com.nearme.entity.ServiceEntity;

@Component
public class GeneratePdf {

	@Autowired
	Environment environment;

	private Float[] logoImgScale = { 50f, 50f };

	private static Font COURIER = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
	private static Font COURIER_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	List<String> columnName = new ArrayList<>();

	public String generatePdfReport(List<ServiceEntity> servicesList, String TypeofService) {

		columnName = Stream.of("Id", "Name", "Mobile Num", "Address", "Available").collect(Collectors.toList());
		int noOfColumns = columnName.size();

		Document document = new Document();

		String pdfPath = environment.getProperty("nearme.pdfDir") + "ServiceFile.pdf";
		try {
			PdfWriter.getInstance(document,new FileOutputStream(environment.getProperty("nearme.pdfDir") + "ServiceFile.pdf"));

			document.open();
			addLogo(document);
			addDocTitle(document, TypeofService);
			createTable(document, noOfColumns, TypeofService, servicesList);
			addFooter(document);
			document.close();

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
		return pdfPath;
	}

	private void addLogo(Document document) {
		try {
			Image img = Image.getInstance(environment.getProperty("nearme.logoImgPath"));
			img.scalePercent(logoImgScale[0], logoImgScale[1]);
			img.setAlignment(Element.ALIGN_RIGHT);
			document.add(img);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	private void addDocTitle(Document document, String TypeOfService) throws DocumentException {

		Paragraph p1 = new Paragraph();
		leaveEmptyLine(p1, 1);
		if (TypeOfService.equalsIgnoreCase("w")) {
			p1.add(new Paragraph(environment.getProperty("nearme.waterservice"), COURIER));

		} else if (TypeOfService.equalsIgnoreCase("m")) {
			p1.add(new Paragraph(environment.getProperty("nearme.maidservice"), COURIER));

		} else if (TypeOfService.equalsIgnoreCase("c")) {
			p1.add(new Paragraph(environment.getProperty("nearme.cleaningservice"), COURIER));

		} else if (TypeOfService.equalsIgnoreCase("g")) {
			p1.add(new Paragraph(environment.getProperty("nearme.grocerymartservice"), COURIER));

		}
		p1.setAlignment(Element.ALIGN_CENTER);
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph(environment.getProperty("nearme.servicedetails"), COURIER_SMALL));
		document.add(p1);
	}

	private void createTable(Document document, int noOfColumns, String typeOfService, List<ServiceEntity> services)
			throws DocumentException {

		//Deepak
		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 2);
		paragraph.add(new Paragraph("Vendor Details", COURIER));
		leaveEmptyLine(paragraph, 1);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(noOfColumns);
		System.out.println(noOfColumns);

		for (int i = 0; i < noOfColumns; i++) {
			Phrase phrase = new Phrase(columnName.get(i));
			PdfPCell cell = new PdfPCell(phrase);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setBorderColor(BaseColor.BLACK);
			table.addCell(cell);
		}
		for (ServiceEntity waterService : services) {

			table.setWidthPercentage(100);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.getDefaultCell().setBorderColor(BaseColor.BLACK);
			table.addCell(waterService.getId().toString());
			table.addCell(waterService.getName());
			table.addCell(waterService.getMobileNum().toString());
			table.addCell(getAppendAddress(waterService.getAddress()));
			table.addCell(waterService.isAvailable() == true ? "YES" : "NO");
		}

		document.add(table);
	}

	private void addFooter(Document document) throws DocumentException {
		Paragraph p2 = new Paragraph();
		leaveEmptyLine(p2, 3);
		p2.setAlignment(Element.ALIGN_LEFT);
		p2.add(new Paragraph(environment.getProperty("nearme.footerData"), COURIER_SMALL_FOOTER));

		document.add(p2);
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	private static String getAppendAddress(AddressEntity addressEntity) {
		
	 String address	= addressEntity.getHouseNumber() +" "+addressEntity.getCity() +" "
	                   +addressEntity.getState()+" "+addressEntity.getCountry()+" "+addressEntity.getPinCode();
		
	return address;
	}
}
