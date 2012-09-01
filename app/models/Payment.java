package models;

import javax.persistence.*;
import play.db.jpa.*;
import java.util.Date;

@Entity
public class Payment extends Model{

	public String token;
	public Date created;
}
