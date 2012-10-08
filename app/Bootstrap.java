import play.jobs.*;

import models.*; 

@OnApplicationStart
public class Bootstrap extends Job{
	
	public void doJob(){
		if( Customer.count() == 0 ){
			Customer c = new Customer();
			c.firstName = "David";
			c.lastName = "Dale";
			c.email = "david@flow7.net";
			c.phone = "123-123-1233";
			c.save();
		}
		
		System.out.println("ran this from bootstrap");
	}
	
}