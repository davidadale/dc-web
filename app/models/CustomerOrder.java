package models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class CustomerOrder extends Model{
 
    public enum DisposalMethod{MAGNETIC,PHYSICAL,RETURN}
    
    public enum Type{LAPTOP,DESKTOP}
    
    public enum Plan{SILVER,GOLD,PLATINUM}

    public enum Status{WAITING,RECEIVED,STARTED,COMPLETE,CANCELED}

    public enum Os{MAC,WINDOWS,LINUX}
 
    public Date created;
 
    @Required
    public Type type;
    
    @Required
    public DisposalMethod disposalMethod;
    
    @Required
    public Plan plan;

    @Required
    public Os system = Os.MAC;

    public Status status;   
    
    public boolean shipSame;

    @ManyToOne
    public Billing billing;
    
    @ManyToOne
    public Shipping shipping;

    @ManyToOne
    public Customer customer;

    public Long customerId;    

    public String orderNumber;

    @Transient
    public boolean confirmed;

    
    public CustomerOrder(){
        created = new Date();
        customer = new Customer();
    }

    public void addItem( Map<String,Object> attributes  ){
       new Item( orderNumber, attributes ).save();
    }

    public void updateFrom(CustomerOrder order){
        if( order.created!=null ){ created = order.created;}
        if( order.type!=null){ type=order.type;}
        if( order.disposalMethod!=null ){disposalMethod=order.disposalMethod;}
        if( order.plan!=null ){plan = order.plan;}

        shipSame = order.shipSame;

        if( order.billing!=null ){billing=order.billing;}
        if( order.shipping!=null ){shipping=order.shipping;}
        if( order.customerId!=null ){customerId=order.customerId;}
    }
    
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