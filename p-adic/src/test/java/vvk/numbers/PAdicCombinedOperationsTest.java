package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PAdicCombinedOperationsTest {

    @Test
    public void testOperations() {
        PAdic a = new PAdic("11", 7);
        PAdic b = new PAdic("2", 7);
        PAdic c = new PAdic("60", 7);

        PAdic result1 = a.divide(b);
        result1 = result1.subtract(b.multiply(new PAdic("2", 7))).add(new PAdic("1", 7));
        result1 = result1.multiply(c);
        result1 = result1.subtract(a.multiply(new PAdic("4", 7)));

        PAdic result2 = b.multiply(b).multiply(new PAdic("2", 7)).multiply(new PAdic(-1, 7));
        result2 = c.multiply(result2.add(a).add(b)).divide(b);
        result2 = result2.subtract(a.multiply(b).multiply(new PAdic("4", 7)).divide(b));
        result2 = result2.subtract(b);

        Assert.assertEquals(b, result1.subtract(result2));
    }
}
