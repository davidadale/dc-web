package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

import validation.*;


@Entity
public class Customer extends Model{
	
    @Required
    public String firstName;

    @Required
    public String lastName;

    @Required
    public String email;

    @Required
    public String phone;

    public boolean alreadyRegistered(){
        Customer customer = Customer.find("byEmail",email).first();
        return customer != null;
    }

}