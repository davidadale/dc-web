package unit;

import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;
import cleaners.*;

public class BasicTest extends UnitTest {


    @Test 
    public void test_length_of_number(){
        assertTrue( OrderNumber.next().length() == 10 );
    }    

    @Test
    public void test_auth_code(){
    	AuthCode code = new AuthCode();
    	AuthCode code2 = new AuthCode();
    	assertNotSame( code.value, code2.value);
    }


}
