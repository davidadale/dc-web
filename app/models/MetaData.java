package models;

import play.db.jpa.*;
import javax.persistence.*;

@Entity
public class MetaData extends Model{

	@ManyToOne
	Item item;

	public String name;
	public String value;

	public MetaData(){

	}

	public MetaData(Item item, String name, String value){
		this.item = item;
		this.name = name;
		this.value = value;
	}

}