package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

@Entity
public class Billing extends Model{
    
    public String address;
    public String address2;

    @Required
    public String city;
    
    public String state;
    public String zip;
    public boolean preferred;
    public boolean shipSame;
    
    @ManyToOne
    public Customer customer;
    
    public Billing(){
        
    }
    
    public Billing(boolean shipSame){
        this.shipSame = shipSame;
    }
}