package models;

import play.db.jpa.*;
import javax.persistence.*;

@Entity
public class MetaData extends Model{

	public String name;
	public String value;

	public MetaData(){

	}

	public MetaData( String name, String value){
		this.name = name;
		this.value = value;
	}

}