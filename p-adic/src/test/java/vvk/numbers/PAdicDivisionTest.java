/*
The MIT License (MIT)

Copyright (c) 2014 Vladislav Kaverin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class PAdicDivisionTest {

    @Test
    public void testDivision() {
        final int base = 5;

        PAdic twoPoint = new PAdic("222.2", 5);
        PAdic onePoint = new PAdic("111.1", base);
        PAdic two = new PAdic(new BigInteger("2"), base);

        PAdic x = new PAdic("12321.1232", base);
        PAdic y = new PAdic("13.21", base);

        Assert.assertNotSame(x, x.divide(y));

        Assert.assertEquals(two, twoPoint.divide(onePoint));
        Assert.assertEquals(onePoint, twoPoint.divide(two));
        
        x = new PAdic(new BigInteger("1"), new BigInteger("2"), 5);
        y = new PAdic(new BigInteger("2"), new BigInteger("1"), 5);
        PAdic result = new PAdic(new BigInteger("1"), new BigInteger("4"), 5);
        Assert.assertEquals(result, x.divide(y));

        x = new PAdic(new BigInteger("1"), new BigInteger("2"), 11);
        y = new PAdic(new BigInteger("2"), new BigInteger("1"), 11);
        result = new PAdic(new BigInteger("1"), new BigInteger("4"), 11);
        Assert.assertEquals(result, x.divide(y));

        x = new PAdic(new BigInteger("1"), new BigInteger("11"), 7);
        y = new PAdic("1", 7);
        PAdic z = new PAdic("14", 7);

        Assert.assertEquals(x, y.divide(z));
    }

    @Test
    public void testDivisionAgain() {
        int base = 7;

        PAdic a = new PAdic("11", base);
        PAdic b = new PAdic("2", base);
        PAdic c = new PAdic(new BigInteger("2"), base);
        PAdic d = new PAdic(new BigInteger("4"), base);

        PAdic result = new PAdic("1", base);
        Assert.assertEquals(result, b.divide(c));
        Assert.assertEquals(d, a.divide(c));
        Assert.assertEquals(d, a.divide(b));
        Assert.assertEquals(b, d.divide(c));
        Assert.assertEquals(c, d.divide(b));
        Assert.assertEquals(c, d.divide(c));
        Assert.assertEquals(b, d.divide(b));

        PAdic x = new PAdic(new BigInteger("23"), new BigInteger("14"), 7);
        PAdic y = new PAdic(new BigInteger("23"), 7);
        result = new PAdic(new BigInteger("1"), new BigInteger("14"), 7);
        Assert.assertEquals(result, x.divide(y));

        x = new PAdic(new BigInteger("-125"), 5);
        y = new PAdic("100", 5);
        result = new PAdic(new BigInteger("-5"), 5);

        Assert.assertEquals(result, x.divide(y));
    }

    @Test
    public void testRational() {
        PAdic a = new PAdic(new BigInteger("2"), 2);
        PAdic result = new PAdic("1", 2);

        Assert.assertEquals(result, a.divide(a));
    }
}
