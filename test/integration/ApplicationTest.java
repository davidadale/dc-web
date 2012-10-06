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

public class ApplicationTest extends FunctionalTest {

    @Before
    public void setUp(){

        //CustomerOrder order = CustomerOrder.find("byOrderNumber","123123123123").first();
        //List<Item> items = Item.find("customer = ? order by created", order.customer).fetch();
        //System.out.println( "Song list =============> " + items );

        //JPA.em().getTransaction().setRollbackOnly();
        Fixtures.deleteDatabase();
        Fixtures.loadModels("data.yml");
        //JPA.em().getTransaction().commit();
   }
    
    @After
    public void tearDown(){
        //EntityTransaction trans = JPA.em().getTransaction();
        //if( trans != null && trans.isActive() ){
        //    trans.rollback();
        //}
    }

    
    /*@Test
    public void testTheHomePage() throws IOException{
 
        Map<String,String> params = new HashMap<String,String>();
        params.put("width", "400");
        params.put("height","200");
        params.put("contentType", "text/json");
        params.put("orderNo", "12345678");
        params.put("identifier","123-123-asdf-123");
        params.put("created","2012-02-14 14:12:00");
        
        Response response = POST("/api/photo", params );
        String json = getContent( response );
        System.out.println( json );
        assertStatus(200, response);
        
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();
        
        Item photo = gson.fromJson(json, Item.class);
        assertTrue( photo.get("width").intValue() == 400 );
        assertTrue( photo.get("height").intValue() == 200 );
        assertEquals( photo.contentType,"text/json");
        assertEquals( photo.orderNumber,"12345678" );
        assertEquals( photo.identifier,"123-123-asdf-123" );
        
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.set(Calendar.MINUTE,  0);
        date.set(Calendar.SECOND,  0);
        date.set(Calendar.MONTH,   1);
        date.set(Calendar.DATE,   14);
        date.set(Calendar.YEAR, 2012);
        
        assertTrue( photo.get("created").dateValue().equals( date.getTime() ) );
    }*/

    @Test public void test_api_add_song(){
/*
            String orderNo,
            String contentType,
            String identifier,
            String title, 
            String album, 
            String track 
*/

        String identifier = UUID.randomUUID().toString();

        Map<String,String> params = new HashMap<String,String>();
        params.put("orderNo", "123123123123");
        params.put("contentType","audio");
        params.put("identifier", identifier);
        params.put("title", "Standing on the rock");
        params.put("album","123-123-asdf-123");
        params.put("track","2012-02-14 14:12:00");

        Response response = POST("/api/song", params );
        String json = getContent( response );
        System.out.println( json );
        assertStatus(200, response);        


    }


    @Test public void testOrderNumber(){
        //JPA.em().getTransaction().setRollbackOnly();
        assertTrue( OrderNumber.next().length() == 10 );
    }
         
    
}