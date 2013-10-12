package models;


import java.util.*;
import java.util.Date;
import javax.annotation.Nullable;
import javax.persistence.*;

import com.google.common.collect.Iterables;
import play.db.jpa.*;
import com.google.common.collect.Collections2;
import com.google.common.base.Predicate;


@Entity
public class Item extends Model{

    public String orderNumber;
    public Date created;

    @OneToMany(cascade = CascadeType.ALL)
    public List<MetaData> data;

    public Item(){
        created = new Date();
        data = new LinkedList<MetaData>();
    }

    public Item(String orderNumber, Map<String,Object> attributes){

        this();
        this.orderNumber = orderNumber;

        for( String key: attributes.keySet() ){
            data.add( new MetaData(  key,  attributes.get( key ).toString() ) );
        }
    }

    public String getFileName(){

        MetaData name = Iterables.find(data, new Predicate<MetaData>() {
                    @Override
                    public boolean apply(@Nullable MetaData metaData) {
                        return metaData.name.equals("DC_FILENAME");
                    }
                });

        return  name.value;
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
            data.add( new MetaData(  param.getKey(), param.getValue() )  );
        }

    }



}