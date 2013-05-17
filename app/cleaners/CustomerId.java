package cleaners;

import play.mvc.Scope.Session;
import models.Customer;

public class CustomerId{
    private static final String CUSTOMER_ID = "customer_id";

    public static Long get(){
        String sid = Session.current().get( CUSTOMER_ID );
        try{ return Long.parseLong( sid ); }
        catch(Exception e){ return null; }
    }

    public static boolean present(){
    	return get()!=null;
    }

    public static boolean is(Customer customer ){
    	return present() && get().equals( customer.id );
    }

    public static boolean not( Customer customer ){
    	return !is( customer );
    }
    
    public static void put(Long id){
        Session.current().put( CUSTOMER_ID, id );
    }
}