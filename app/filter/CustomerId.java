package filter;

import play.mvc.Scope.Session;

public class CustomerId{
    private static final String CUSTOMER_ID = "customer_id";
    
    public static Long get(){
        String sid = Session.current().get( CUSTOMER_ID );
        try{ return Long.parseLong( sid ); }
        catch(Exception e){ return null; }
    }
    
    public static void put(Long id){
        Session.current().put( CUSTOMER_ID, id );
    }
}