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
    public void testZero() {
        PAdic zeroString = new PAdic("0");
        PAdic zeroInt = new PAdic(0);
        PAdic zeroRat = new PAdic(0, 1);

        Assert.assertEquals(zeroString, PAdic.ZERO);
        Assert.assertEquals(zeroInt, PAdic.ZERO);
        Assert.assertEquals(zeroRat, PAdic.ZERO);
        Assert.assertEquals(zeroString, zeroInt);
        Assert.assertEquals(zeroString, zeroRat);
        Assert.assertEquals(zeroRat, zeroInt);
    }

    @Test
    public void testAddition() {
        PAdic x = new PAdic("122");
        PAdic y = new PAdic("221");
        PAdic z = new PAdic("10");
        PAdic zneg = new PAdic(-5);

        Assert.assertEquals(new PAdic("343"), x.add(y));
        Assert.assertEquals(new PAdic("244"), x.add(x));
        Assert.assertEquals(new PAdic("0"), z.add(zneg));
        Assert.assertEquals(new PAdic("0"), zneg.add(z));

        x = new PAdic("123");
        y = new PAdic("0.1");
        z = new PAdic(-1, 5);

        Assert.assertEquals(new PAdic("123.1"), x.add(y));
        Assert.assertEquals(x, x.add(y).add(z));
    }

    @Test
    public void testSubstraction() {
        PAdic rat = new PAdic(1, 5);
        PAdic point = new PAdic("0.1");

        Assert.assertEquals(rat.getOrder(), point.getOrder());
        Assert.assertEquals(-1, point.getOrder());
        Assert.assertEquals(rat.substract(point), PAdic.ZERO);
        Assert.assertEquals(point.substract(rat), PAdic.ZERO);

        PAdic one = new PAdic("1");
        Assert.assertEquals(one.substract(one).getOrder(), PAdic.ZERO.getOrder());
        Assert.assertEquals(one.substract(one), PAdic.ZERO);

        PAdic one3 = new PAdic("11.1");
        PAdic one1 = new PAdic("10");

        PAdic result = new PAdic("0.1");
        Assert.assertEquals(one3.substract(one1).substract(one), result);
    }

    @Test
    public void testMultiplication() {
        PAdic a = new PAdic("123.4");
        PAdic one = new PAdic("1");
        Assert.assertEquals(-1, a.getOrder());
        Assert.assertEquals(0, one.getOrder());
        Assert.assertEquals(a, a.multiply(one));
        Assert.assertEquals(a.multiply(PAdic.ZERO), PAdic.ZERO);
        Assert.assertEquals(a.multiply(PAdic.ZERO), PAdic.ZERO);

        PAdic two = new PAdic("2222");
        PAdic point = new PAdic("0.1");
        PAdic result = new PAdic("222.2");
        Assert.assertEquals(two.multiply(point), result);
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
