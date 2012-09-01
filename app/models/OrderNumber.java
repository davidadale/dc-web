package models;

import javax.persistence.*;
import play.db.jpa.*;
import filter.Pad;

@Entity
public class OrderNumber extends Model{

	public static String next(){
		OrderNumber on = new OrderNumber();
		on.save();
		return Pad.lpad( on.id , 10 );
	}

}