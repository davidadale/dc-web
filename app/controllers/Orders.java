package controllers;

import cleaners.Cashier;
import cleaners.CustomerId;
import cleaners.OrderId;
import exception.UserAlreadyRegisteredException;
import models.*;
import notifiers.Mails;
import play.Logger;
import play.data.validation.Valid;
import play.mvc.Catch;
import play.mvc.Controller;

/**
 * This controller handles creating new users by handling the sign up process.
 */
@SuppressWarnings("static-access")
public class Orders extends Controller {


    /**
     * Cancel the current order by clearing session data.
     */
    public static void cancel() {
        session.clear();
        Site.index();
    }

    /**
     * Save customer information and create a new CustomerOrder
     */
    public static void saveCustomerInfo(@Valid Customer customer) {

        if (validation.hasErrors()) {
            renderTemplate("site/index.html", customer);
        }

        if (CustomerId.not(customer)) {
            if (customer.alreadyRegistered()) {
                flash.put("username", customer.email);
                throw new UserAlreadyRegisteredException();
            }
            customer.deleteIfPresent(); // get rid of the old unregistered version.
            customer.save();
            CustomerId.put(customer.id);
        }

        Mails.verifyAccount(customer);

        CustomerOrder order = new CustomerOrder();
        renderTemplate("orders/order.html", order);
    }

    public static void saveOrderInfo(@Valid CustomerOrder order) {

        if (validation.hasErrors()) {
            renderTemplate("orders/order.html", order);
        }

        Customer customer = Customer.findById(CustomerId.get());
        
        if( OrderId.notSet() ){
            
            order.customer = customer;
            order.orderNumber = OrderNumber.next();
            order.save();
            OrderId.put( order.id );            

        }else{            

            CustomerOrder temp = CustomerOrder.findById( OrderId.get() );            
            temp.updateFrom( order );
            temp.save();
            order = temp;
            
        }

        renderTemplate("orders/thankYou.html",order);
    }

    public static void showCardInfo() {
        renderTemplate("orders/card.html");
    }

    public static void saveCardInfo(@Valid Card card) {

        if (validation.hasErrors()) {
            validation.keep();
            renderTemplate("orders/card.html");
        }
        Customer customer = Customer.findById(CustomerId.get());
        CustomerOrder order = CustomerOrder.findById(OrderId.get());
        card.customer = customer;
        card.order = order;
        card.save();
        confirmCharge();
    }

    public static void confirmCharge() {
        CustomerOrder order = CustomerOrder.findById(OrderId.get());
        renderTemplate("orders/confirm.html", order);
    }

    public static void charge() {
        CustomerOrder order = CustomerOrder.findById(OrderId.get());
        Card card = Card.find("byOrder", order).first();
        Cashier.get().makePayment(card, order.getTotal());
        thankYou();
    }

    public static void thankYou() {
        CustomerOrder order = CustomerOrder.findById(OrderId.get());
        renderTemplate("orders/thankYou.html", order);
    }

    public static void verifyAccount(String token) {

        Customer customer = Customer.find("byVerificationToken", token).first();

        if (customer != null) {

            Logger.info("Customer account %s has been verified.", customer.email);

            customer.verified = true;
            customer.save();

            User user = User.find("byEmail", customer.email).first();

            if (user == null) {
                Logger.info("Creating a new user account for customer.");
                user = new User();
                user.email = customer.email;
                user.password = customer.password;
                user.verificationToken = customer.verificationToken;
                user.save();
            }

            Security.signInAs( user );
            Client.index();
            //Security.changePassword(user);

        } else {

            flash.error("Account doesn't seem to be valid. Please try registering again.");
            Orders.cancel();

        }
    }

    @Catch(UserAlreadyRegisteredException.class)
    public static void alreadyRegistered(Throwable throwable) {
        // send to login with the appropriate message
        flash.error("Email is already registered, please sign in to submit a new order.");
        try {
            Secure.login();
        } catch (Throwable t) {
            // go to a 404 or something.
        }

    }
}