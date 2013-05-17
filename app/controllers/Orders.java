package controllers;

import cleaners.Cashier;
import cleaners.CustomerId;
import cleaners.OrderId;
import models.*;
import exception.*;
import play.data.validation.*;
import play.mvc.*;

import notifiers.*;

/**
 * This controller handles creating new users by handling the sign up process.
 */
@SuppressWarnings("static-access")
public class Orders extends Controller {

    /**
     * This method starts a new customer order process by clearing the session
     * and then calling the create method.
     */
    public static void reset() {
        session.clear();
        showCustomerInfo();
    }

    /**
     * Cancel the current order by clearing session data.
     */
    public static void cancel() {
        session.clear();
        Site.index();
    }

    /**
     * Fill out the customer general information starting the customer sign up
     * process. The sesssion
     */
    public static void showCustomerInfo() {
        Customer customer = null;
        // see if the customer informatmion is already filled in
        if (CustomerId.present()) { // should be some time limit applied here.
            customer = Customer.findById(CustomerId.get());
        }
        renderTemplate("orders/customer.html", customer);
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
        order.customer = customer;
        order.orderNumber = OrderNumber.next();
        order.save();
        OrderId.put(order.id);

        renderTemplate("orders/card.html");
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
        System.out.println("Customer is null: " + (customer == null));
        if (customer != null) {
            customer.verified = true;
            customer.save();
            User user = User.find("byEmail", customer.email).first();
            System.out.println("User is null: " + (user == null));
            if (user == null) {
                user = new User();
                user.email = customer.email;
                user.verificationToken = customer.verificationToken;
                user.save();

            }
            Security.changePassword(user);
        } else {
            Orders.reset();
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