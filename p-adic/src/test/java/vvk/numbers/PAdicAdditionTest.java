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
