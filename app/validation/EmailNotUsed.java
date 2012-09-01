package validation;

import play.data.validation.*;
import models.Customer;

public class EmailNotUsed extends Check{

    public boolean isSatisfied(Object object, Object email) {
    	Customer customer = Customer.find("byEmail", (String)email).first();
    	setMessage("validation.email.alreadyUsed", (String)email );
    	return customer == null;
    }
}