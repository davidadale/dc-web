package filter;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import models.Card;
import models.CustomerCharge;


public class Cashier{

	private static Cashier instance;

	private Cashier(){
		Stripe.apiKey = Config.getPrivateKey();
	}

	public static Cashier get(){
		if( instance==null ){
			instance = new Cashier();
		}
		return instance;
	}

	public CustomerCharge makePayment( Card card, BigDecimal amount ){
		
		
		Map<String,Object> chargeParams = new HashMap<String,Object>();
		chargeParams.put( "amount", amount);
		chargeParams.put( "currency","usd");
		chargeParams.put( "description","initial charge for getting drive cleaned");
		chargeParams.put( "card", card.token );

                try{
                    Charge charge = Charge.create( chargeParams );
                    return new CustomerCharge(charge.getId(),card);                    
                }catch(Exception e){
                    // handle exception
                }

		return null;
	}

}