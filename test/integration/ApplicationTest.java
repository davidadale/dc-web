package integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import models.Item;
import org.junit.*;
import play.db.jpa.JPA;
import javax.persistence.EntityTransaction;
import play.mvc.Http.Response;
import play.test.*;
import java.util.UUID;
import java.util.*;
import models.*;
import models.CustomerOrder.DisposalMethod;
import models.CustomerOrder.Type;
import models.CustomerOrder.Plan;


public class ApplicationTest extends FunctionalTest {

    @Before
    public void setUp(){
        Fixtures.deleteDatabase();
        Fixtures.loadModels("integration/data.yml");
    }
    
    @After
    public void tearDown(){

    }

    @Test
    public void test_api_create_customer(){
        Map<String,String> params = new HashMap<String,String>();
        params.put("customer.firstName","Joe");
        params.put("customer.lastName","Blo");
        params.put("customer.email","joeblo@flow7.net");
        params.put("customer.phone","123-123-1234");
        
        Response response = POST( "/api/customer", params );
        String json = getContent( response );
        
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();
        
        Customer customer = gson.fromJson(json, Customer.class); 
        assertEquals( "Joe", customer.firstName );       
        assertEquals( "joeblo@flow7.net", customer.email );

    }

    @Test
    public void test_api_update_customer(){
        Customer c = Customer.find("byEmail","johndoe@flow7.net").first();
        Map<String,String> params = new HashMap<String,String>();
        params.put("customer.firstName","Jonny");

        Response resp = POST("/api/customer/"+c.id,params);
        String json = getContent( resp );
        
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();
        
        Customer customer = gson.fromJson(json, Customer.class); 
        assertEquals("Jonny", customer.firstName );

    }

    @Test
    public void test_api_delete_customer(){
        Customer c = Customer.find("byEmail","johndoe@flow7.net").first();
        Response resp = DELETE("/api/customer/"+c.id );
        String json = getContent( resp );
        assertEquals("{success:true}",json);
    }

    @Test
    public void test_api_create_order(){
        Customer c = Customer.find("byEmail","johndoe@flow7.net").first();

        Map<String,String> params = new HashMap<String,String>();
        params.put("order.type","LAPTOP");
        params.put("order.disposalMethod","MAGNETIC");
        params.put("order.plan","SILVER");
        params.put("order.customerId", ""+c.id );

        Response resp = POST("/api/order",params);
        String json = getContent( resp );
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();
        CustomerOrder order = gson.fromJson( json, CustomerOrder.class );
        assertNotNull( order.id );
    }

    @Test
    public void test_api_update_order(){
        CustomerOrder o = CustomerOrder.find("byOrderNumber","123123123123").first();
        Map<String,String> params = new HashMap<String,String>();
        params.put("order.type","DESKTOP");

        Response resp = POST("/api/order/"+o.id,params);
        String json = getContent( resp );
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();
        CustomerOrder order = gson.fromJson( json, CustomerOrder.class );
        assertEquals(Type.DESKTOP,order.type);

    }

    @Test
    public void test_api_delete_order(){
        CustomerOrder order = CustomerOrder.find("byOrderNumber","123123123123").first();
        Response resp = DELETE("/api/order/"+order.id);
        String json = getContent( resp );
        assertEquals("{success:true}", json);
    }


    @Test 
    public void test_api_add_item(){

        String identifier = UUID.randomUUID().toString();

        Map<String,String> params = new HashMap<String,String>();
        params.put("item.orderNumber", "123123123123");
        params.put("item.contentType","audio");
        params.put("item.identifier", identifier);
        params.put("item.title", "Standing on the rock");
        params.put("item.album","123-123-asdf-123");
        params.put("item.track","2012-02-14 14:12:00");

        Response response = POST("/api/item", params );
        String json = getContent( response );
        System.out.println( json );
        assertStatus(200, response);        
        
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();
        
        Item song = gson.fromJson(json, Item.class); 
        //assertEquals("Standing on the rock", song.title);
        //assertEquals("123-123-asdf-123", song.album);
        throw new RuntimeException("Fix test");
    }

    @Test
    public void test_api_update_item(){
        // look up item to get id
        Item item = Item.find("byTitle","Oh Happy Day").first();
        //assertEquals("Bad Album Name", item.album);

        // perform an update to the album using the API 
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("item.album","new album");
        Response resp = POST("/api/item/"+item.id,params);
        String json = getContent( resp );
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();

        // verify the result
        Item song = gson.fromJson(json,Item.class);
        //assertEquals("new album",song.album);
        throw new RuntimeException("Fix test");
    }

    @Test
    public void test_api_delete_item(){
        Item item = Item.find("byTitle","Oh Happy Day").first();
        Response resp = DELETE("/api/item/"+item.id);
        String json = getContent(resp);
        assertEquals("{success:true}",json);
    }



         
    
}