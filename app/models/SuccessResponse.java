package models;

public class SuccessResponse{

	public boolean success = true;
	public Object data;

	public SuccessResponse(Object obj){
		this.data = obj;
	}
}