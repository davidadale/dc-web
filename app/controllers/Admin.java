package controllers;

import play.mvc.*;
import java.util.*;
import models.*;

@With(Secure.class)
public class Admin extends Controller{

	public static void customers(){
		List<Customer> customers = Customer.findAll();
		renderTemplate("admin/list.html",customers);
	}

	public static void orders(Long custId){
		Customer customer = Customer.findById( custId );
		List<CustomerOrder> orders = CustomerOrder.find("byCustomer", customer).fetch();
		renderTemplate("admin/orders.html",orders);
	}


	public static void deleteCustomer(Long id){
		Customer customer = Customer.findById( id );

		List<CustomerOrder> orders = CustomerOrder.find("byCustomer", customer).fetch();
		for(CustomerOrder o: orders){
			o.delete();
		}

		List<Billing> billingAddresses = Billing.find("byCustomer", customer).fetch();
		for(Billing b: billingAddresses){
			b.delete();
		} 

		customer.delete();

		customers();
	}
}