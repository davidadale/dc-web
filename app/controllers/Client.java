package controllers;

import java.util.*;
import play.mvc.*;
import models.*;

public class Client extends Controller{

	public static void show( Long customerId ){
		Customer customer = Customer.findById(customerId);//customer = ? order by created
		List<Item> items = Item.find("byCustomer", customer).fetch();
		renderTemplate("client/show.html", customer, items );
	}

}