package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PAdicDivisionTest {

    @Test
    public void testDivision() {
        final int base = 5;

        PAdic twoPoint = new PAdic("222.2", 5);
        PAdic onePoint = new PAdic("111.1", base);
        PAdic two = new PAdic(2, base);

        PAdic x = new PAdic("12321.1232", base);
        PAdic y = new PAdic("13.21", base);

        Assert.assertNotSame(x, x.divide(y));

        Assert.assertEquals(two, twoPoint.divide(onePoint));
        Assert.assertEquals(onePoint, twoPoint.divide(two));
    }
}
