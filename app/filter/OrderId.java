package filter;

import play.mvc.Scope.Session;

public class OrderId{
	private static final String ORDER_ID = "order_id";
    
	public static Long get(){
	    String sid = Session.current().get( ORDER_ID );
	    try{ return Long.parseLong( sid ); }
	    catch(Exception e){ return null; }
	}

	public static void put(Long id){
	    Session.current().put( ORDER_ID, id );
	}
}