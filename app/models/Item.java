package models;

import java.util.HashSet;
import java.util.*;
import java.util.Date;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Item extends Model{

    @ManyToOne
    public Customer customer;

    public String orderNumber;
    public Date created;
    public String storageKey;

    @OneToMany
    public List<MetaData> data;

    public Item(){
        created = new Date();
        data = new LinkedList<MetaData>();
    }
    
    public Item(Customer customer){
        this();
        this.customer = customer;
    }

    /**
     * Utility method.
     *
     */
    public void updateMetaData( Map<String,String> params ){
        
        // get rid of previous meta data attributes
        List<MetaData> old = MetaData.find( "byItem",this ).fetch();
        for( MetaData prop: old ){
            prop.delete();
        }

        for( Map.Entry<String,String> param: params.entrySet() ){
            data.add( new MetaData( this , param.getKey(), param.getValue() )  );
        }

    }



}