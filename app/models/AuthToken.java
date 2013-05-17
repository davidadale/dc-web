package models;

import play.db.jpa.*;
import javax.persistence.*;
import java.util.UUID;

@Entity
public class AuthToken extends Model{

	public String authToken;
	public String email;

	public AuthToken(String email){
		this.email  = email;
		authToken = UUID.randomUUID().toString();
	}
	
}