package controllers;

import play.mvc.*;
import play.data.validation.*;
import java.util.*;
import models.*;
import java.util.zip.*;
import java.io.*;
import static cleaners.Extensions.*;

@With(Secure.class)
public class Admin extends Controller{

	/**
	 * List orders and serve as the default location
	 * for administration of the system.
	 */
	public static void index(){
		orders();
	}

	/**
	 * List orders.
	 *
	 */
	public static void orders (){
		List<CustomerOrder> orders;
		if( params._contains("q") ){
			orders = CustomerOrder.find("orderNumber like ? order by created desc","%"+params.get("q")+"%" ).fetch();
		}else{
			orders = CustomerOrder.find("order by created desc").fetch();
		}

		//List<CustomerOrder> orders = CustomerOrder.find("order by created desc").fetch();
		renderTemplate( "admin/orders.html", orders );		
	}

	public static void createOrder(){
		CustomerOrder order = new CustomerOrder();
		renderTemplate("admin/editOrder.html",order );
	}

    public static void uploadIndex( Long id, File file ){
        println( file.toString() );
        render();
    }

	public static void saveOrder(@Valid CustomerOrder order){
		if( validation.hasErrors() ){
			renderTemplate("admin/editOrder.html", order);
		}
		order.save();
		orders();
	}

	public static void showOrder(Long id){
		CustomerOrder order = CustomerOrder.findById( id );
		renderTemplate("admin/order.html",order);
	}

	public static void editOrder(Long id){
		CustomerOrder order = CustomerOrder.findById( id );
		renderTemplate("admin/editOrder.html", order );
	}

	public static void deleteOrder( Long id ){
		CustomerOrder order = CustomerOrder.findById( id );
		order.delete();
		orders();
	}

	/**
	 * List all the customers.
	 *
	 */
	public static void customers(){
		List<Customer> customers;
		if( params._contains("q") ){
			customers = Customer.find("email like ?", "%"+params.get("q")+"%").fetch();
		}else{
			customers = Customer.findAll();
		}

		//List<Customer> customers = Customer.findAll();
		renderTemplate("admin/customers.html",customers);
	}

	public static void createCustomer(){
		Customer customer = new Customer();
		renderTemplate("admin/editCustomer.html",customer);
	}

	public static void saveCustomer(@Valid Customer customer, CustomerOrder order){

        if( validation.hasErrors() ){
            renderTemplate("admin/editCustomer.html", customer);
        }
		customer.save();
		customers();
	}

	public static void showCustomer(Long id){
		Customer customer = Customer.findById(id);
		List<CustomerOrder> orders = CustomerOrder.find("byCustomerId",id).fetch();
		System.out.println ("ORders is " + orders);
		renderTemplate("admin/customer.html",customer,orders);
	}

	public static void editCustomer(Long id){
		Customer customer = Customer.findById( id );
		renderTemplate( "admin/editCustomer.html", customer );
	}

	/**
	 * This is a destructive method that should only be used if 
	 * the customer record should be totally removed from the system.
	 * 
	 */
	public static void deleteCustomer(Long id){
		Customer customer = Customer.findById( id );

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



	public static void orders(Long custId){
		Customer customer = Customer.findById( custId );
		List<CustomerOrder> orders = CustomerOrder.find("byCustomer", customer).fetch();
		List<Card> cards = Card.find("byCustomer", customer).fetch();		
		renderTemplate("admin/orders.html",orders,cards);
	}

	/**
	 * List users.
	 *
	 */
	public static void users(){
		List<User> users = User.find("order by email").fetch();
		renderTemplate("admin/users.html",users);
	}

	public static void showPasswordReset( Long id, Long timestamp ){
		User user = User.findById( id );
		renderTemplate("admin/_changePassword.html",user);
	}

	public static void changePassword(Long id,String password,String confirm){
		
		validation.required( password );
		validation.required( confirm );

		if( validation.hasErrors() ){
			renderJSON( new ErrorResponse("Both fields are required"));
		}

		if( !password.equals( confirm ) ){
			renderJSON( new ErrorResponse("Password does not match") );
		}

		User user = User.findById( id );
		user.password = password;
		user.save();

		renderJSON( new SuccessResponse( user ) );

	}

	public static void createUser(){
		User user = new User();
		renderTemplate("admin/editUser.html",user);
	}

	public static void saveUser(@Valid User user){
		if( validation.hasErrors() ){
			renderTemplate("admin/editUser.html",user);			
		}
		user.save();
		users();
	}

	public static void showUser(Long id){
		User user = User.findById( id );
		renderTemplate("admin/user.html",user);
	}

	public static void editUser(Long id){
		User user = User.findById( id );
		renderTemplate("admin/editUser.html",user);
	}

}