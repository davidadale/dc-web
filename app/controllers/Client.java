package controllers;

import java.util.*;
import play.mvc.*;
import models.*;

public class Client extends Controller{

	public static void index(){
		//User user = User.find( "byEmail", Security.connected() ).first();
		render();
	}

	public static void show( Long customerId ){
		Customer customer = Customer.findById(customerId);//customer = ? order by created
		List<Item> items = Item.find("byCustomer", customer).fetch();
		renderTemplate("client/show.html", customer, items );
	}

    public static void manage( Long orderId ){
        CustomerOrder order = CustomerOrder.findById( orderId );
        List<Item> items = Item.find("byOrderNumber", order.orderNumber ).fetch();
        renderTemplate("client/index.html", items );
    }

}