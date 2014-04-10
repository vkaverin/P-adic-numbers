package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PAdicTest {

    @Test
    public void testOrder() {
        Assert.assertEquals(2, new PAdic("12300").getOrder());
        Assert.assertEquals(-2, new PAdic("123.12").getOrder());
        Assert.assertEquals(0, new PAdic("12312").getOrder());
        Assert.assertEquals(-2, new PAdic("123.120000").getOrder());
        Assert.assertEquals(-1, new PAdic(5, 25).getOrder());
        Assert.assertEquals(1, new PAdic(25, 5).getOrder());
        Assert.assertEquals(2, new PAdic(25, 3).getOrder());
        Assert.assertEquals(-2, new PAdic(3, 25).getOrder());
        Assert.assertEquals(0, new PAdic(3, 6).getOrder());
        Assert.assertEquals(1, new PAdic(5).getOrder());
        Assert.assertEquals(2, new PAdic(25).getOrder());
        Assert.assertEquals(1, new PAdic(5, 6).getOrder());
        Assert.assertEquals(0, new PAdic(0, 1).getOrder());
    }

    @Test
    public void testDivision() {
        PAdic twoPoint = new PAdic("222.2");
        PAdic onePoint = new PAdic("111.1");
        PAdic two = new PAdic(2);

        Assert.assertEquals(two, twoPoint.divide(onePoint));
        Assert.assertEquals(onePoint, twoPoint.divide(two));
    }
}
