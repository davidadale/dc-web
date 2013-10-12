package models;

public class SuccessResponse{

	public boolean success = true;
    public Long resultSize = 0l;
	public Object data;

	public SuccessResponse(Object obj){
		this.data = obj;
	}

    public SuccessResponse( Object obj, Long resultSize ){
        this.data = obj;
        this.resultSize = resultSize;
    }
}