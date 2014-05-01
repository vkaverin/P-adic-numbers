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

public class PAdicAdditionTest {

    @Test
    public void testAddition() {

        final int base = 5;

        PAdic x = new PAdic("122", 5);
        PAdic y = new PAdic("221", base);
        PAdic z = new PAdic("10", base);
        PAdic zneg = new PAdic(new BigInteger("-5"), base);

        Assert.assertEquals(new PAdic("343", base), x.add(y));
        Assert.assertEquals(new PAdic("244", base), x.add(x));
        Assert.assertEquals(new PAdic("0", base), z.add(zneg));
        Assert.assertEquals(new PAdic("0", base), zneg.add(z));

        x = new PAdic("123", base);
        y = new PAdic("0.1", base);
        z = new PAdic(new BigInteger("-1"), new BigInteger("5"), base);

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


        x = new PAdic("123", base);
        y = new PAdic("300", base);
        z = new PAdic("0.001", base);

        result = new PAdic("423", base);
        Assert.assertEquals(result, x.add(y));
        Assert.assertEquals(result, y.add(x));
        result = new PAdic("123.001", base);
        Assert.assertEquals(result, x.add(z));
        Assert.assertEquals(result, z.add(x));
        result = new PAdic("300.001", base);
        Assert.assertEquals(result, y.add(z));
        Assert.assertEquals(result, z.add(y));
    }

    @Test
    public void testAdditionAgain() {
        int base = 5;
        PAdic a = new PAdic("1.234", base);
        PAdic b = new PAdic("3.211", base);
        PAdic result = new PAdic("10", base);
        Assert.assertEquals(result, a.add(b));

        a = new PAdic("123", 7);
        b = new PAdic("123", 7);
        result = new PAdic("246", 7);

        Assert.assertEquals(result, a.add(b));

        a = new PAdic("123.456", 7);
        b = new PAdic("654.321", 7);
        result = new PAdic("1111.11", 7);
        Assert.assertEquals(result, a.add(b));
    }
}
