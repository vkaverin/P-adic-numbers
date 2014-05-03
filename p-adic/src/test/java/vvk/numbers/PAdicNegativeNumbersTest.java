package vvk.numbers;


import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class PAdicNegativeNumbersTest {

    @Test
    public void testAddition() {
        int base = 5;
        PAdic a = new PAdic(new BigInteger("-125"), base);
        PAdic b = new PAdic("2", base);
        PAdic result = new PAdic(new BigInteger("-123"), base);

        Assert.assertEquals(result, a.add(b));
    }
}
