package controllers;

import filter.*;
import models.*;
import play.data.validation.*;
import play.mvc.*;

@SuppressWarnings("static-access")
public class Orders extends Controller{
    
    public static void list(){
        
    }
    
    public static void create(){
        renderTemplate("orders/customer.html");
    }
    
    public static void saveCustomerInfo(@Valid Customer customer){
        if( validation.hasErrors() ){
            renderTemplate("orders/customer.html", customer);
        }        
        customer.save();        
        CustomerId.put( customer.id );
        CustomerOrder order = new CustomerOrder();        
        renderTemplate( "orders/order.html", order );
    }
    
    public static void saveOrderInfo(@Valid CustomerOrder order){
        if( validation.hasErrors() ){
            renderTemplate("orders/order.html", order);
        }
        Customer customer = Customer.findById( CustomerId.get() );
        order.createOrderNumber();
        order.save();
        Billing billing = new Billing(true);
        renderTemplate("orders/billing.html", billing );
    }
    
    public static void saveBillingInfo(@Valid Billing billing ){
        
        if( validation.hasErrors() ){
            renderTemplate("orders/billing.html",billing);
        }
        Customer customer = Customer.findById( CustomerId.get() );
        billing.customer = customer;
        billing.save();
        if( billing.shipSame ){
            createShipper();
        }else{
            renderTemplate("orders/shipping.html");    
        }
    }
    
    
    public static void saveShippingInfo(@Valid Shipping shipping){
        if( validation.hasErrors() ){
            renderTemplate("orders/shipping.html",shipping);
        }
        shipping.save();
        createShipper();
    }
    
    public static void createShipper(){
        
    }
}