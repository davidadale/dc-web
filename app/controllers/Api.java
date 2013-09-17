/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cleaners.IndexReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.*;
import org.apache.commons.collections.CollectionUtils;
import models.*;

import play.data.validation.Error;
import play.data.validation.Validation;
import play.db.jpa.JPA;
import play.mvc.Controller;
import static cleaners.Extensions.*;

/**
 * The API for Drive Cleaners
 * @author daviddale
 */
public class Api extends Controller  {

    public static void createToken(String username,String password){
        User user = User.find("byEmail",username).first();
        if( not(user) ){
            renderJSON( new ErrorResponse("No user found for " + username) );
        }
        if( !user.password.equals( password  ) ){
            renderJSON( new ErrorResponse("Bad username or password") );
        }
        
        AuthToken token = new AuthToken( username );
        token.save();

        renderJSON(new SuccessResponse(token) );

    }

    public static void uploadIndex( Long id, File file ){

        IndexReader reader = new IndexReader();
        List objects = null;
        try{
            objects = reader.read( file );

            for( Object obj: objects ){
                Map<String,String> row = (Map) obj;
                for(String key: row.keySet()){

                }
            }

        }catch(Exception e){
            renderJSON( new ErrorResponse( "Error reading index. err: " + e.getMessage() ) );
        }
        renderJSON( new SuccessResponse( objects ) );
    }


    public static void customers(){
        List<Customer> customers = Customer.findAll();
        if( empty(customers ) ){
            renderJSON( new ErrorResponse("No customers found in system.") );
        }
        renderJSON( new SuccessResponse( customers ) );
    }   

    public static void customer(Long id ){
        Customer customer = Customer.findById( id );
        if( not( customer ) ){
            renderJSON( new ErrorResponse("No customer found for customer id " + id ) );
        }
        renderJSON( new SuccessResponse(customer) );
    }

    public static void users(){
        List<User> users = User.findAll();
        if( empty(users) ){
            renderJSON( new ErrorResponse("No users found in system.") );
        }
        renderJSON( new SuccessResponse(users) );
    }

    public static void user(Long id ){
        User user = User.findById( id );
        if( not(user ) ){
            renderJSON( new ErrorResponse("No user for user id " + id ) );
        }
        renderJSON( new SuccessResponse(user) );
    }    

    public static void items( Long custId ){
        
        Customer customer = Customer.findById( custId );
        if( not(customer) ){
            renderJSON(new ErrorResponse("No customer found for customer id " + custId ) );
        }
        
        List<Item> items = Item.find("byCustomer",customer).fetch();
        renderJSON( new SuccessResponse(items) );
    }

    public static void item( Long id ){
        Item item = Item.findById( id );
        if(not(item)){
            renderJSON(new ErrorResponse("No item found for item id " + id));
        }
        renderJSON( new SuccessResponse(item) );
    }

    public static void createItem(){
        Item item = new Item();
        item.updateMetaData( params.allSimple() );
        item.save();
        renderJSON( new SuccessResponse(item) );
    }

    public static void updateItem( Long id ){
        Item item = Item.findById( id );  
        if( not(item ) ){
            renderJSON(new ErrorResponse("No item found for item id " + id));
        }

        item.updateMetaData( params.allSimple() );
        item.save();
        renderJSON( new SuccessResponse(item) );
    }

    public static void deleteItem( Long id ){
        Item item = Item.findById( id );        

        if( not(item) ){
            renderJSON( new ErrorResponse("No item found for item id " + id ));
        }

        Long custId = item.customer.id;
        item.delete();

        Customer customer = Customer.findById( custId );

        if( not(customer) ){
            renderJSON( new ErrorResponse("No customer found for customer id " + custId ));
        }

        List<Item> items = Item.find("byCustomer",customer).fetch();
        renderJSON( new SuccessResponse(items) );

    }

    public static void order(String orderNumber){
        
        CustomerOrder order = CustomerOrder.find("byOrderNumber",orderNumber).first();
        if( not(order) ){
            renderJSON( new ErrorResponse("Order not found for order number " + orderNumber ) );
        }
        renderJSON( new SuccessResponse( order ) );

    }

    


//====================== Old stuff below this ===============
    /**
     * Returns a customer record as JSON
     * @param id customer id
     */
 /*   public static void getCustomer(Long id){
        validation.required(id);
        if(Validation.hasErrors()){
            // deal with errors
        }
        Customer customer = Customer.findById(id);
        renderJSON( customer );
    }
    
    public static void getCustomerOrder(String orderNumber ){
        validation.required(orderNumber);
        if(Validation.hasErrors() ){
            // deal with errors
        }
        CustomerOrder order = CustomerOrder.find( "byOrderNumber", orderNumber ).first();
        renderJSON( order );
    }

    public static void getItem(Long id){
        validation.required(id);
        if(Validation.hasErrors()){

        }
        Item item = Item.findById( id );
        renderJSON( item );
    }

    public static void addCustomer(Customer customer){

        validation.valid( customer );

        if( Validation.hasErrors() ){
            error( 400, getErrorsAsJson( validation.errorsMap() ) );   
        }

        if( customer.alreadyRegistered() ){
            error( 400, "{success:false,message:'user already exists'}");
        }

        customer.save();
        renderJSON( customer );
    }

    public static void updateCustomer( Long id, Customer customer ){
        Customer db = Customer.findById( id );
        db.updateFrom( customer );
        validation.valid( db );
        if( Validation.hasErrors() ){
            error( 400, "{success:false}");
        }
        db.save();
        renderJSON(db);
    }
    public static void deleteCustomer( Long id ){
        Customer customer = Customer.findById( id );
        try{
            customer.delete();    
        }catch(Exception e){
            error(400,"{success:false,'message': error trying to delete customer}");
        }
        renderJSON("{success:true}");
    }

    public static void createOrder(CustomerOrder order){
        validation.valid( order );
        if( Validation.hasErrors() ){
            error( 400, getErrorsAsJson( validation.errorsMap() ) );
        }
        order.save();
        renderJSON( order );
    }
    public static void updateOrder(Long id, CustomerOrder order){
        CustomerOrder db = CustomerOrder.findById( id );
        db.updateFrom( order );
        db.save();
        renderJSON( db );
    }

    public static void deleteOrder(Long id){
        CustomerOrder o = CustomerOrder.findById( id );
        try{ o.delete(); }
        catch(Exception e){renderJSON("{success:false}");}
        renderJSON("{success:true}");
    }

    public static void addItem( Item item ){
        CustomerOrder order = CustomerOrder.find("byOrderNumber", item.orderNumber ).first();
        //item.customer = order.customer;
        item.save();
        renderJSON( item );        
    }

    public static void updateItem( Long id, Item item){
        Item db = Item.findById( id );
        db.updateFrom( item );
        db.save();
        renderJSON( db );
    }

    public static void deleteItem(Long id){
        Item item = Item.findById( id );
        try{ item.delete(); }
        catch(Exception e){ renderJSON("{success:false}");}
        renderJSON("{success:true}");
    }
    
    public static String getErrorsAsJson( Map<String, List<Error>> fields ){
        StringBuilder json = new StringBuilder("[");
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();
        for( String key : fields.keySet()){
            List<Error> errors = fields.get( key );
            for(Error e: errors){
                json.append("{field:");
                json.append( e.getKey() );
                json.append(",message:");
                json.append( e.message());
                json.append("},");
            }
        }
        json.append("]");
        return json.toString();
    }*/
    
}
