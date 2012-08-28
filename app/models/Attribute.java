/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import exception.FormatException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import play.db.jpa.Model;

/**
 *
 * @author daviddale
 */
@Entity(name="ITEM_ATTRS")
public class Attribute extends Model {
    
    @ManyToOne
    public Item item;
    
    public String key;
    public String value;
    
    public Attribute(){
        super();
    }
    
    public Attribute(String key, String value){
        this();
        this.key = key;
        this.value = value;
    }
    
    public Integer intValue(){
        return Integer.valueOf( value );
    }

    public Long longValue(){
        return Long.valueOf(value);
    }
    
    public Float floatValue(){
        return Float.valueOf(value);
    }
    
    public String stringValue(){
        return value;
    }
    
    public Date dateValue(){
        try{
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            return format.parse( value );            
        }catch( Exception e ){
            throw new FormatException("Date format was incorrect and caused an exception", e );
        }

    }
    
    public String toString(){
        return value;   
    }
    
    @Override
    public boolean equals( Object obj ){
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        
        Attribute rhs = (Attribute)obj;
        
        return new EqualsBuilder()
                .append(key, rhs.key )
                .isEquals();
    }
    
    @Override
    public int hashCode(){
       return new HashCodeBuilder()
       .append( key )
       .toHashCode();        
    }
    
    
    
}
