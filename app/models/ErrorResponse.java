package models;


public class ErrorResponse{

	public String msg;
	public boolean success = false;

	public ErrorResponse(String msg){
		this.msg = msg;
	}
	
}