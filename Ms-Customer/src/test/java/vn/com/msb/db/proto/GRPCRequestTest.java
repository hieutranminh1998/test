package vn.com.msb.db.proto;

import com.example.customer.entity.Customer;
import com.google.code.beanmatchers.BeanMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GRPCRequestTest {
    @Test
    public void testTheClassIsGoodJavaBean() {
        MatcherAssert.assertThat(GRPCRequest.class,
                CoreMatchers.allOf(
                        BeanMatchers.hasValidBeanConstructor(),
                        BeanMatchers.hasValidGettersAndSetters()
                ));

        assertEquals(new GRPCRequest(null,"abc").getCustomerId(), "abc");
    }
}