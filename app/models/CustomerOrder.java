package models;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class CustomerOrder extends Model{
 
    public enum DisposalMethod{MAGNETIC,PHYSICAL,RETURN;}
    
    public enum Type{LAPTOP,DESKTOP;}
    
    public enum Plan{SILVER,GOLD,PLATINUM;}
 
    public Date created;
 
    @Required
    public Type type;
    
    @Required
    public DisposalMethod disposalMethod;
    
    @Required
    public Plan plan;
    
    public boolean shipSame;

    @ManyToOne
    public Billing billing;
    
    @ManyToOne
    public Shipping shipping;
    
    @ManyToOne
    public Customer customer;
    
    
    public String orderNumber;
    
    
    public void createOrderNumber(){
        orderNumber = UUID.randomUUID().toString();
    }
    
    /*public Order(){
        this.disposalMethod = DisposalMethod.MAGNETIC;
        shipSame = true;
        created = new Date();
    }*/
}