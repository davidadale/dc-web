package models;

import play.db.jpa.*;
import javax.persistence.*;

@Entity(name="account")
public class User extends Model{
    
    public String email;
    public String password;
    public Boolean isAdmin = Boolean.FALSE;
    public String verificationToken;
}