package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;


@Entity
public class Customer extends Model{
    @Required
    public String firstName;
    @Required
    public String lastName;
    @Required
    public String email;
    @Required
    public String phone;
}