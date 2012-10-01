/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.Entity;
import play.db.jpa.Model;

/**
 *
 * @author daviddale
 */
@Entity
public class CustomerCharge extends Model{
    
    public String chargeId;
    public Card card;
    public Customer customer;
    
    public CustomerCharge(){}
    
    public CustomerCharge(String chargeId, Card card){
        this.chargeId = chargeId;
        this.card = card;
        this.customer = card.customer;
    }
    
}
