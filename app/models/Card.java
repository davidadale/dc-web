package models;

import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;
import java.util.Date;

@Entity
public class Card extends Model{

	@ManyToOne
	public Customer customer;

	@ManyToOne
	public CustomerOrder order;

	@Required
	public String token;

	public Date created = new Date();
}
