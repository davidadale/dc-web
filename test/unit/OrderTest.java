/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import java.math.BigDecimal;
import models.CustomerOrder;
import models.CustomerOrder.DisposalMethod;
import models.CustomerOrder.Plan;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author daviddale
 */
public class OrderTest {

    @Test
    public void testTotalCost(){
        CustomerOrder order = new CustomerOrder();
        order.plan = Plan.GOLD;
        order.disposalMethod = DisposalMethod.MAGNETIC;
        assertEquals( order.getTotal(),new BigDecimal("59.99") );//29.99 + 30.00
    }
    
}
