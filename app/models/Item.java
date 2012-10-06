package models;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Item extends Model{
     
    public static final String TITLE = "title";
    public static final String ALBUM = "album";
    public static final String TRACK = "album";
    public static final String DESCRIPTION = "description";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String CREATED = "created";
    public static final String MODIFIED = "modified";
    public static final String AUTHOR = "author";    
    
    @ManyToOne
    public Customer customer;

    @Required
    public String filename;
    public String contentType;
    public Long size;
    public String identifier;
    public String orderNumber;
    public Date created;
    
    @OneToMany(cascade= CascadeType.ALL)
    public Set<Attribute> attributes;
    
    public Item(Customer customer){
        this.customer = customer;
        attributes = new HashSet<Attribute>();
        created = new Date();
    }
    
    public Item(Customer customer, String orderNo,String contentType, String identifier ){
        this( customer );
        this.orderNumber = orderNo;
        this.contentType = contentType;
        this.identifier = identifier;
    }
    
    public void set(String key, Object value){
        attributes.add( new Attribute( key,     String.valueOf( value ) ) );
    }
    
    public Attribute get(String key){

        Attribute attr = null;
        
        if( key==null || key.length()==0){
            return attr;
        }

        
        for(Attribute a: attributes ){
            if( key.equals(  a.key ) ){
                attr = a;
                break;
            }
        }
        return attr;
    }
    
}