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

public class PAdicOrderTest {

    @Test
    public void testOrder() {
        final int base = 5;

        Assert.assertEquals(2, new PAdic("12300", base).getOrder());
        Assert.assertEquals(-2, new PAdic("123.12", base).getOrder());
        Assert.assertEquals(0, new PAdic("12312", base).getOrder());
        Assert.assertEquals(-2, new PAdic("123.120000", base).getOrder());
        Assert.assertEquals(-1, new PAdic(new BigInteger("5"), new BigInteger("25"), base).getOrder());
        Assert.assertEquals(1, new PAdic(new BigInteger("25"), new BigInteger("5"), base).getOrder());
        Assert.assertEquals(2, new PAdic(new BigInteger("25"), new BigInteger("3"), base).getOrder());
        Assert.assertEquals(-2, new PAdic(new BigInteger("3"), new BigInteger("25"), base).getOrder());
        Assert.assertEquals(0, new PAdic(new BigInteger("3"), new BigInteger("6"), base).getOrder());
        Assert.assertEquals(1, new PAdic(new BigInteger("5"), base).getOrder());
        Assert.assertEquals(2, new PAdic(new BigInteger("25"), base).getOrder());
        Assert.assertEquals(1, new PAdic(new BigInteger("5"), new BigInteger("6"), base).getOrder());
        Assert.assertEquals(0, new PAdic(BigInteger.ZERO, BigInteger.ONE, base).getOrder());
        Assert.assertEquals(1, new PAdic("1.234", base).add(new PAdic("3.211", base)).getOrder());    }
}
