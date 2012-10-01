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


	public static void deleteCustomer(Long id){
		Customer customer = Customer.findById( id );

		CustomerCharge.deleteAll();

		/*List<CustomerCharge> charges = CustomerCharge.find("byCustomer",customer).fetch();
		for(CustomerCharge charge: charges){
			charge.delete();
		}*/

		List<Card> cards = Card.find("byCustomer",customer).fetch();
		for(Card card: cards){
			card.delete();
		}

		List<Billing> billingAddresses = Billing.find("byCustomer", customer).fetch();
		for(Billing b: billingAddresses){
			b.delete();
		} 

		List<CustomerOrder> orders = CustomerOrder.find("byCustomer", customer).fetch();
		for(CustomerOrder o: orders){
			o.delete();
		}		

		customer.delete();

		customers();
	}
}