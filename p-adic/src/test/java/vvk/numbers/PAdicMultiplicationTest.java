package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PAdicMultiplicationTest {
    
    @Test
    public void testMultiplication() {
        final int base = 5;

        PAdic a = new PAdic("123.4", 5);
        PAdic one = new PAdic("1", 5);
        Assert.assertEquals(a, a.multiply(one));

        PAdic two = new PAdic("2222", 5);
        PAdic point = new PAdic("0.1", 5);
        PAdic result = new PAdic("222.2", 5);
        Assert.assertEquals(two.multiply(point), result);
        
        PAdic value1 = new PAdic(1, 6, 5);
        PAdic value2 = new PAdic(6, 1, 5);
        PAdic value3 = new PAdic(1, 5);
        PAdic value4 = new PAdic(2, base);
        PAdic value5 = new PAdic("0.1", base);
        PAdic value6 = new PAdic(5, base);
        PAdic value0 = new PAdic(0, base);
        PAdic value8 = new PAdic(1, 2, base);
        PAdic value9 = new PAdic("0.01", base);
        
        Assert.assertEquals(value3, value1.multiply(value2));
        Assert.assertEquals(value3, value3.multiply(value3));
        Assert.assertEquals(value3, value1.multiply(value4).multiply(value8).multiply(value2));
        Assert.assertEquals(value0, value0.multiply(value0));
        Assert.assertEquals(value0, value0.multiply(value4));
        Assert.assertEquals(value3, value6.multiply(value5));
        Assert.assertEquals(value9, value5.multiply(value5));
        Assert.assertEquals(value9, value3.multiply(value9));
        Assert.assertEquals(value9, value9.multiply(value3));
        Assert.assertEquals(value3, value3.multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3).multiply(value3));

        PAdic value10 = new PAdic(25, base);

        Assert.assertEquals(value6, value10.multiply(value5));
        Assert.assertEquals(value6, value5.multiply(value10));

        PAdic x = new PAdic("1231.0012", base);
        PAdic y = new PAdic("13200", base);
        result =  new PAdic("22404221.34", base);

        Assert.assertEquals(result, x.multiply(y));
        Assert.assertEquals(result, y.multiply(x));

        x = new PAdic("1.23", base);
        y = new PAdic("100", base);
        result = new PAdic("123", base);

        Assert.assertEquals(result, x.multiply(y.multiply(value3).multiply(value3.substract(new PAdic("0", 5)))));
        Assert.assertEquals(result, y.multiply(x));
    }
}