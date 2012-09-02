package models;

import filter.Pad;

import javax.persistence.*;
import play.db.jpa.*;

@Entity
public class OrderNumber extends Model{

	public static String next(){
		OrderNumber on = new OrderNumber();
		on.save();
		return Pad.lpad( on.id , 10 );
	}

}