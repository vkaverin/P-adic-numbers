package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PAdicAdditionTest {

    @Test
    public void testAddition() {

        final int base = 5;

        PAdic x = new PAdic("122", 5);
        PAdic y = new PAdic("221", base);
        PAdic z = new PAdic("10", base);
        PAdic zneg = new PAdic(-5, base);

        Assert.assertEquals(new PAdic("343", base), x.add(y));
        Assert.assertEquals(new PAdic("244", base), x.add(x));
        Assert.assertEquals(new PAdic("0", base), z.add(zneg));
        Assert.assertEquals(new PAdic("0", base), zneg.add(z));

        x = new PAdic("123", base);
        y = new PAdic("0.1", base);
        z = new PAdic(-1, 5, base);

        Assert.assertEquals(new PAdic("123.1", base), x.add(y));
        Assert.assertEquals(x, x.add(y).add(z));

        x = new PAdic("1231.00120", base);
        y = new PAdic("13211", base);
        z = new PAdic("1.23", base);

        PAdic result = new PAdic("14443.2312", base);

        Assert.assertEquals(result, x.add(y).add(z));
        Assert.assertEquals(result, z.add(y).add(x));
        Assert.assertEquals(result, y.add(x).add(z));
        Assert.assertEquals(result, x.add(y.add(z)));
        Assert.assertEquals(result, z.add(y.add(x)));
        Assert.assertEquals(result, y.add(z.add(x)));
    }
}
