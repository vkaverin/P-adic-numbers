package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PAdicSubstractionTest {

    @Test
    public void testSubstraction() {
        PAdic rat = new PAdic(1, 5);
        PAdic point = new PAdic("0.1");

        Assert.assertEquals(rat.substract(point), PAdic.ZERO);
        Assert.assertEquals(point.substract(rat), PAdic.ZERO);

        PAdic one = new PAdic("1");
        Assert.assertEquals(one.substract(one).getOrder(), PAdic.ZERO.getOrder());
        Assert.assertEquals(one.substract(one), PAdic.ZERO);

        PAdic one3 = new PAdic("11.1");
        PAdic one1 = new PAdic("10");

        PAdic result = new PAdic("0.1");
        Assert.assertEquals(one3.substract(one1).substract(one), result);

        PAdic point1 = new PAdic("1.0");
        PAdic point2 = new PAdic("0.2");
        PAdic point3 = new PAdic("0.3");

        Assert.assertEquals(point2, point1.substract(point3));
        Assert.assertEquals(point3, point1.substract(point2));
        Assert.assertEquals(PAdic.ZERO, PAdic.ZERO.substract(PAdic.ZERO));
        Assert.assertEquals(point1, point1.substract(PAdic.ZERO));
        Assert.assertEquals(PAdic.ZERO,point1.substract(point1));
        Assert.assertEquals(PAdic.ZERO,point2.substract(point2));

        PAdic x = new PAdic("1231.0012");
        PAdic y = new PAdic("13211");
        result = new PAdic("444444444444444444444444444444444444444444444444444444433020.0012");

        Assert.assertEquals(result, x.substract(y));

        result = new PAdic("11424.4433");

        Assert.assertEquals(result, y.substract(x));

        y = new PAdic("1.23");
        result = new PAdic("444444444444444444444444444444444444444444444444444444443220.2233");

        Assert.assertEquals(result, y.substract(x));
  }
}
