package filter;

import java.util.*;

public class Config{

	private Map<String,String> requiredProps = new HashMap<String,String>();
	private Map<String,String> local = null;
	private static Config instance;

	private Config(){
		requiredProps.put("privateKey","STRIPES_PRIVATE_KEY");
		requiredProps.put("publicKey","STRIPES_PUBLIC_KEY");
	}

	private Config(Map<String,String> properties){
		this();
		this.local = properties;
	}

	public static Config get(){
		if( instance == null ){
			instance = new Config();
		}
		return instance;
	}

	public static String getPrivateKey(){
		return get().propertyValue( "privateKey" );
	}

	public static String getPublicKey(){
		return get().propertyValue( "publicKey" );
	}


	public String propertyValue(String key){

		if( local==null ){
			return System.getenv( requiredProps.get(key) );
		}else{
			return local.get( key );
		}

	}


}