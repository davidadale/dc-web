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
import play.mvc.Http.Response;
import play.test.*;

public class ApplicationTest extends FunctionalTest {

    @Before
    public void setUp(){
        
   }
    
    @After
    public void tearDown(){
        JPA.em().getTransaction().rollback();
    }
    
    
    @Test
    public void testTheHomePage() throws IOException{
        
        JPA.em().getTransaction().setRollbackOnly();    
 
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
        
        
    }
         
    
}