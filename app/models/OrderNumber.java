package models;

import javax.persistence.*;
import play.db.jpa.*;
import static cleaners.Extensions.*;

@Entity
public class OrderNumber extends Model {

    public static String next() {
        OrderNumber on = new OrderNumber();
        on.save();
        // a little padding so my order
        // number doesn't look so corny
        Long number = on.id + 999655l;
        return lpad(number, 10);
    }
}