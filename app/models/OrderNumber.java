package models;

import javax.persistence.*;
import play.db.jpa.*;
import static cleaners.Extensions.*;

@Entity
public class OrderNumber extends Model {

    public static String next() {
        OrderNumber next = new OrderNumber();
        next.save();        
        Long number = next.id + 999655l;
        return lpad(number, 10);
    }
}