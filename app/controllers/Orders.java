package controllers;

import filter.*;
import models.*;
import exception.*;
import java.math.BigDecimal;
import play.data.validation.*;
import play.mvc.*;

@SuppressWarnings("static-access")
public class Orders extends Controller{
    
    public static void list(){
        
    }
    
    public static void start(){
        // start with a clear session.
        session.clear();
        create();
    }
    
    /**
    * Cancel the current order by clearing session data.
    */
    public static void cancel(){
        session.clear();
        Site.index();
    }

    /**
    * Fill out the customer general information
    */
    public static void create(){
    
        Customer customer = null;
        
        // see if the customer informatmion is already filled in
        if( CustomerId.present() ){ // should be some time limit applied here.
            customer = Customer.findById( CustomerId.get() );    
        }
        
        renderTemplate("orders/customer.html",customer);
    }
    



    /**
    * Save customer information and create a new CustomerOrder
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

        renderTemplate( "orders/card.html" );
    }

    public static void saveCardInfo(@Valid Card card){

        if( validation.hasErrors() ){
            validation.keep();
            renderTemplate("orders/card.html");
        }
        Customer customer = Customer.findById( CustomerId.get() );
        CustomerOrder order = CustomerOrder.findById( OrderId.get() );
        card.customer = customer;
        card.order = order;
        card.save();

        Cashier.get().makePayment(card, BigDecimal.ZERO);
        
        thankYou();
    }

    public static void confirmCharge(){
        
        CustomerOrder order = CustomerOrder.findById( OrderId.get() );
        Card card = Card.find("byOrder", order ).first();
        Cashier.get().makePayment( card, order.getTotal() ).save();
        
        
        
        render();
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

    public static void thankYou(){
        CustomerOrder order = CustomerOrder.findById( OrderId.get() );
        renderTemplate("orders/thankYou.html", order );
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