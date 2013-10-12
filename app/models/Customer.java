package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

import validation.*;
import cleaners.AuthCode;

@Entity
public class Customer extends Model{
	
    @Required
    public String firstName;

    @Required
    public String lastName;

    @Required
    public String email;
    
    @Required
    public String password;

    public String phone;

    @Required
    public Boolean verified = false;

    @Required
    public String verificationToken = new AuthCode().value;

    @Transient
    public String getName(){
        return firstName + " " + lastName;
    }

    public Customer updateFrom(Customer c){
        if(c.firstName!=null){firstName = c.firstName;}
        if(c.lastName!=null){lastName = c.lastName;}
        if(c.phone!=null){phone = c.phone;}
        if(c.email!=null){email = c.email;}
        return this;
    }

    public boolean  alreadyRegistered(){
        Customer customer = Customer.find("byEmailAndVerified",email,true).first();
        return customer != null;
    }

    public void deleteIfPresent(){
        Customer customer = Customer.find("byEmail", email ).first();
        if( customer!=null){
            customer.delete();
        }
    }

    /**
     * Just a thought of how an order could be created
     * and assigned to customer.
     */
    public CustomerOrder createOrder(){
        CustomerOrder order = new CustomerOrder();
        order.customerId = id;
        return order;
    }

}