package com.nearme.utilities;

public class ConstantManager {

	private String statusDescription;
	private int statusCode;
	
	
	
	public ConstantManager(String statusDescription, int statusCode) {
		this.statusDescription = statusDescription;
		this.statusCode = statusCode;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public static final ConstantManager UserAlreadyThere = new ConstantManager("Email Id Alreday Resister Please User Another EmailId or Login", 200);
	public static final ConstantManager AdminAlreadyThere = new ConstantManager("Email Id Alreday Resister Please User Another EmailId or Login", 200);
	public static final ConstantManager LoginSuccesfull = new ConstantManager("Login Succesfull For User", 200); 
	public static final ConstantManager UserRegistered = new ConstantManager("User registered Successfully", 200); 
	public static final ConstantManager AdminRegistered = new ConstantManager("Admin registered Successfully", 200); 
	public static final ConstantManager InvalidUserEmail = new ConstantManager("EmailId Is Not Valid" , 200); 
	public static final ConstantManager InvalidPassword = new ConstantManager("Password Is Invalid" , 200); 
    public static final ConstantManager ServerError = new ConstantManager("Error In Serevr.", 500);	
    public static final ConstantManager Success = new ConstantManager("Success", 200);	
    public static final ConstantManager NoDataFound = new ConstantManager("There Is No Data In DataBase", 200);	
    public static final ConstantManager RecordWithSameNumThere = new ConstantManager("Record With Same Number Already There", 200);	
    public final static ConstantManager UnAuhorization = new ConstantManager("Token has been expired.",400);
    public final static ConstantManager EmailIsNotCorrct = new ConstantManager("Email Id Is Not Valid.",400);
}
