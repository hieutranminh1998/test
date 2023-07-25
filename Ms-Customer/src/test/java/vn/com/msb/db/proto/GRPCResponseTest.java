package vn.com.msb.db.proto;

import com.google.code.beanmatchers.BeanMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GRPCResponseTest {
    @Test
    public void testTheClassIsGoodJavaBean() {
        MatcherAssert.assertThat(GRPCResponse.class,
                CoreMatchers.allOf(
                        BeanMatchers.hasValidBeanConstructor(),
                        BeanMatchers.hasValidGettersAndSetters()
                ));

        assertEquals(new GRPCResponse(200,"abc").getCode(), 200);
    }
}