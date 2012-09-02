package models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class CustomerOrder extends Model{
 
    public enum DisposalMethod{MAGNETIC,PHYSICAL,RETURN}
    
    public enum Type{LAPTOP,DESKTOP}
    
    public enum Plan{SILVER,GOLD,PLATINUM}//SILVER is inluded, GOLD 80 GB $30, Platinum 200 GB $75 
 
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
    
    
    public BigDecimal getDisposalCost(){
        switch(disposalMethod){
            case PHYSICAL:
                return new BigDecimal("5.00");
            case RETURN:
                return new BigDecimal("40.00");
            default:
                return new BigDecimal("0.00");
        }        
    }
    
    public BigDecimal getPlanCost(){
        switch(plan){
            case GOLD:
                return new BigDecimal("30.00");                
            case PLATINUM:    
                return new BigDecimal("75.00");
            default:
                return new BigDecimal("0.00");
        }
    }
    
    
    public BigDecimal getTotal(){
        BigDecimal total = new BigDecimal("29.99")
        .add( getPlanCost() )
        .add( getDisposalCost() );
        return total;
    }
    
    /*public Order(){
        this.disposalMethod = DisposalMethod.MAGNETIC;
        shipSame = true;
        created = new Date();
    }*/
}