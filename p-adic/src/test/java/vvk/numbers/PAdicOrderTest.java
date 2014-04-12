package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PAdicOrderTest {

    @Test
    public void testOrder() {
        final int base = 5;

        Assert.assertEquals(2, new PAdic("12300", base).getOrder());
        Assert.assertEquals(-2, new PAdic("123.12", base).getOrder());
        Assert.assertEquals(0, new PAdic("12312", base).getOrder());
        Assert.assertEquals(-2, new PAdic("123.120000", base).getOrder());
        Assert.assertEquals(-1, new PAdic(5, 25, base).getOrder());
        Assert.assertEquals(1, new PAdic(25, 5, base).getOrder());
        Assert.assertEquals(2, new PAdic(25, 3, base).getOrder());
        Assert.assertEquals(-2, new PAdic(3, 25, base).getOrder());
        Assert.assertEquals(0, new PAdic(3, 6, base).getOrder());
        Assert.assertEquals(1, new PAdic(5, base).getOrder());
        Assert.assertEquals(2, new PAdic(25, base).getOrder());
        Assert.assertEquals(1, new PAdic(5, 6, base).getOrder());
        Assert.assertEquals(0, new PAdic(0, 1, base).getOrder());
    }
}
