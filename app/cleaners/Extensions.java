package cleaners;

import java.util.*;

public class Extensions{

    public static void println(String value, Object... params ){

        if( params != null ){
            System.out.println( String.format(value,params ) );
        }else{
            System.out.println( value );
        }

    }

	public static boolean empty( Collection coll ){
		if( coll==null || coll.isEmpty() ){
			return true;
		}
		return false;
	}

	public static boolean not( Object object ){
		return object == null;
	}

	public static String rpad(String s, int n ){
		return String.format("%1$-" + n + "s", s);
	}
	
	public static String lpad(Long l, int n){
		return String.format("%0" + n + "d", l);
	}	
}