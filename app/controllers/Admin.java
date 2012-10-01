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
		List<Card> cards = Card.find("byCustomer", customer).fetch();
		renderTemplate("admin/orders.html",orders,cards);
	}

	/**
	 * This is a destructive meethod that should only be used if 
	 * the customer record should be totally removed from the system.
	 * 
	 */
	public static void deleteCustomer(Long id){
		Customer customer = Customer.findById( id );

		List<Billing> billingAddresses = Billing.find("byCustomer", customer).fetch();
		for(Billing b: billingAddresses){
			b.delete();
		} 

		/*List<Shipping> shipping = Shipping.find("byCustomer",customer).fetch();
		for( Shipping s: shipping ){
			s.delete();
		}*/

		List<Card> cards = Card.find("byCustomer",customer).fetch();
		for( Card c: cards ){
			c.delete();
		}

		List<CustomerOrder> orders = CustomerOrder.find("byCustomer", customer).fetch();
		for(CustomerOrder o: orders){
			o.delete();
		}

		customer.delete();
		customers();
	}
}