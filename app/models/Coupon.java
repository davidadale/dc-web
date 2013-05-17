package models;
import play.db.jpa.*;
import javax.persistence.*;

@Entity
public class Coupon extends Model{

	public String code;
	public Integer percentage;
	public Boolean used = false;

	public static Coupon get(){
		Coupon c = new Coupon();
		//c.code = generateCode();
		return c;
	}

	//protected static v

}