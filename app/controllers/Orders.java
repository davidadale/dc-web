package controllers;

import filter.*;
import models.*;
import exception.*;
import play.data.validation.*;
import play.mvc.*;

@SuppressWarnings("static-access")
public class Orders extends Controller{
    
    public static void list(){
        
    }
    
    /**
    * Fill out the customer general information
    */
    public static void create(){
        Customer customer = null;
        
        if( CustomerId.present() ){ // should be some time limit applied here.
            customer = Customer.findById( CustomerId.get() );    
        }
        
        renderTemplate("orders/customer.html",customer);
    }
    
    public static void cancel(){
        session.clear();
        Site.index();
    }


    /**
    * Save customer informatmion and create a new CustomerOrder
    */
    public static void saveCustomerInfo(@Valid Customer customer){
        
        if( validation.hasErrors() ){
            renderTemplate("orders/customer.html", customer);
        }    

        if( CustomerId.not( customer ) ){

            if( customer.alreadyRegistered() ){
                throw new UserAlreadyRegisteredException();
            }

            customer.save();        
            CustomerId.put( customer.id );
        }

        CustomerOrder order = new CustomerOrder();        
        renderTemplate( "orders/order.html", order );
    }
    
    public static void saveOrderInfo(@Valid CustomerOrder order){
        
        if( validation.hasErrors() ){
            renderTemplate("orders/order.html", order);
        }
        
        Customer customer = Customer.findById( CustomerId.get() );
        order.customer = customer;
        order.orderNumber = OrderNumber.next();
        order.save();
        OrderId.put( order.id );

        Billing billing = new Billing(true);
        renderTemplate("orders/billing.html", billing );
    }
    
    public static void saveBillingInfo(@Valid Billing billing ){
        
        if( validation.hasErrors() ){
            renderTemplate("orders/billing.html",billing);
        }

        Customer customer = Customer.findById( CustomerId.get() );
        CustomerOrder order = CustomerOrder.findById( OrderId.get() );

        billing.customer = customer;
        billing.save();

        order.billing = billing;
        order.save();

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

    @Catch(UserAlreadyRegisteredException.class)
    public static void alreadyRegistered(Throwable throwable ){
        // send to login with the appropriate message
        flash.error("Email is already registered, please sign in to submit a new order.");
        System.out.println("CATCH FOR ALREADy registered was called");
        try{
            Secure.login();    
        }catch(Throwable t){
            // go to a 404 or something.
        }
        
    }    
    
    public static void createShipper(){
        
    }

    public static void label(){
        renderTemplate("orders/label.html");
    }
}