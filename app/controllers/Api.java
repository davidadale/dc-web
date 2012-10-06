/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import models.CustomerOrder;
import models.Item;
import play.data.validation.Error;
import play.data.validation.Validation;
import play.db.jpa.JPA;
import play.mvc.Controller;

/**
 *
 * @author daviddale
 */
public class Api extends Controller  {
    
    
    public static void getCustomerOrder(String orderNumber ){
        validation.required(orderNumber);
        if(Validation.hasErrors() ){
            // deal with errors
        }
        
        CustomerOrder order = CustomerOrder.find( "byOrderNumber", orderNumber ).first();
        // return order;
    }
    
    public static void addSong(
            String orderNo,
            String contentType,
            String identifier,
            String title, 
            String album, 
            String track ){
        
        CustomerOrder order = CustomerOrder.find("byOrderNumber", orderNo ).first();

        Item song = new Item( order.customer, orderNo, contentType, identifier );
        song.set(Item.TITLE ,title);
        song.set(Item.ALBUM, album);
        song.set(Item.TRACK, track);
        song.save();
        System.out.println( "Song save just called........");
        List<Item> items = Item.find("customer = ? order by created", order.customer).fetch();
        System.out.println( "Song list =============> " + items );
        renderJSON( song );
        
    }
    
    public static void addPhoto(
            String orderNo,
            String contentType,
            String identifier,
            String description,
            Integer width,
            Integer height,
            Date created){

        CustomerOrder order = CustomerOrder.find("byOrderNumber", orderNo ).first();

        Validation.required("orderNo" ,orderNo);
        Validation.required("contentType" ,contentType);
        Validation.required("identifier" ,identifier);
        Validation.required("width"   ,width);
        Validation.required("height"  ,height);
        Validation.required("created" ,created);
        
        if( Validation.hasErrors() ){
            error( 400, getErrorsAsJson( validation.errorsMap() ) );
        }
        
        Item photo = new Item( order.customer, orderNo, contentType, identifier);
        photo.set(Item.DESCRIPTION, description );
        photo.set(Item.WIDTH, width );
        photo.set(Item.HEIGHT, height );
        photo.set(Item.CREATED, created );
        
        photo.save();

        renderJSON( photo );
    }
    
    
    public static void addDocument(
            String orderNo,
            String contentType,
            String identifier,
            String title,
            String author,
            String modified,
            String created){
        
        CustomerOrder order = CustomerOrder.find("byOrderNumber", orderNo ).first();

        Item doc = new Item( order.customer, orderNo, contentType, identifier );
        doc.set(Item.TITLE, title);
        doc.set(Item.AUTHOR, author);
        doc.set(Item.MODIFIED, modified);
        doc.set(Item.CREATED, created);
        doc.save();
        
        renderJSON(doc);
    }
    
    /**
     * This method adds an item to the customers order
     * <Post> 
     *    item.filename
     *    item.filetype
     *    item.identifier
     *    item.order.id
     * </Post>
     * @param item 
     */
    public static void addItem(Item item ){
        
        //validation.required( item.order.id );
        
        if( Validation.hasErrors() ){
            // handle error
        }
        
        System.out.println("Order id is: --------------------> " + item.identifier );
        System.out.println("Order id is: --------------------> " + item.orderNumber );
        System.out.println("Order id is: --------------------> " + item.contentType );
        System.out.println("Order id is: --------------------> " + item.filename );
        
        // CustomerOrder order = CustomerOrder.findById( item.order.id );
        // item.order = order;
        item.save();
        
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
    }
    
    public static void getItem(Long id){
        
        
        
    }
    
    
}
