package validation;

import play.data.validation.*;
import models.Customer;
import exception.UserAlreadyRegisteredException;

public class ExistingUser extends Check{
	
	public boolean isSatisfied(Object object, Object email) {
		//EmailNotUsed check = new EmailNotUsed();
		Customer customer = Customer.find("byEmail", (String)email).first();

		if( customer!=null ){
			throw new UserAlreadyRegisteredException();
		}
		
		return true;
	}
}