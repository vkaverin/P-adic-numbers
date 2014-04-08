package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PAdicAdditionTest {

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
}
