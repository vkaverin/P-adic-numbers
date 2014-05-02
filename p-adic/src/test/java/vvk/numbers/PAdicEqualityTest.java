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

public class PAdicEqualityTest {

    @Test
    public void testEquality() {
        final int base = 5;

        PAdic rational1 = new PAdic(new BigInteger("5"), new BigInteger("1"), base);
        PAdic rational1_neg = new PAdic(new BigInteger("-5"), new BigInteger("-1"), base);
        PAdic string1 = new PAdic("10", base);
        PAdic integer1 = new PAdic(new BigInteger("5"), base);

        Assert.assertEquals(rational1, string1);
        Assert.assertEquals(string1, integer1);
        Assert.assertEquals(rational1, integer1);
        Assert.assertEquals(rational1_neg, string1);
        Assert.assertEquals(rational1_neg, rational1);
        Assert.assertEquals(rational1_neg, integer1);
        Assert.assertEquals(rational1, rational1);
        Assert.assertEquals(rational1, rational1_neg);
        Assert.assertEquals(rational1_neg, rational1);
        Assert.assertEquals(rational1_neg, rational1);

        PAdic point1 = new PAdic("0.1", base);
        PAdic point2 = new PAdic("0.01", base);
        PAdic point3 = new PAdic("0.001", base);
        PAdic point4 = new PAdic("0000.100000", base);
        PAdic rat1 = new PAdic(new BigInteger("1"), new BigInteger("5"), base);
        PAdic rat2 = new PAdic(new BigInteger("1"), new BigInteger("25"), base);
        PAdic rat3 = new PAdic(new BigInteger("1"), new BigInteger("125"), base);

        Assert.assertEquals(point1, rat1);
        Assert.assertEquals(point2, rat2);
        Assert.assertEquals(point3, rat3);
        Assert.assertEquals(point4, rat1);
        Assert.assertEquals(point4, point1);

        PAdic str1 = new PAdic("01.0", base);
        PAdic str2 = new PAdic("1", base);

        Assert.assertEquals(str1, str2);

        str1 = new PAdic("00000002.0000000", base);
        str2 = new PAdic("00000002.0", base);
        PAdic str3 = new PAdic("2.0000000", base);
        PAdic str4 = new PAdic("2", base);

        Assert.assertEquals(str1, str2);
        Assert.assertEquals(str2, str3);
        Assert.assertEquals(str3, str4);
        Assert.assertEquals(str4, str1);
        Assert.assertEquals(str4, str2);

        PAdic int1 = new PAdic(new BigInteger("2"), base);
        PAdic strx = new PAdic("2", base);

        PAdic ratx = new PAdic(new BigInteger("1"), new BigInteger("2"), base);
        PAdic raty = new PAdic(new BigInteger("-1"), new BigInteger("-2"), base);

        PAdic ratz = new PAdic(new BigInteger("2"), new BigInteger("1"), base);
        PAdic ratn = new PAdic(new BigInteger("-2"), new BigInteger("-1"), base);

        Assert.assertEquals(int1, strx);
        Assert.assertEquals(ratx, raty);
        Assert.assertEquals(ratz, ratn);
        Assert.assertEquals(strx, ratn);

        PAdic point7 = new PAdic("0.2", base);
        PAdic point5 = new PAdic("0.3", base);
        PAdic point6 = new PAdic("0.0002", base);

        Assert.assertEquals(new PAdic("1", 5), point7.add(point5));
        Assert.assertEquals(new PAdic("0.2002", base), point7.add(point6));
    }

    @Test
    public void testZero() {
        final int base = 5;

        PAdic zeroString = new PAdic("0", base);
        PAdic severalZerosString = new PAdic("000000000000", base);
        PAdic zeroPointString = new PAdic("000000.000000", base);
        PAdic zeroInt = new PAdic(new BigInteger("0"), base);
        PAdic zeroRat = new PAdic(new BigInteger("0"), new BigInteger("1"), base);

        Assert.assertEquals(zeroString, zeroInt);
        Assert.assertEquals(zeroString, zeroRat);
        Assert.assertEquals(zeroRat, zeroInt);
        Assert.assertEquals(severalZerosString, zeroString);
        Assert.assertEquals(severalZerosString, zeroPointString);
        Assert.assertEquals(severalZerosString, zeroInt);
        Assert.assertEquals(severalZerosString, zeroRat);
        Assert.assertEquals(zeroPointString, zeroInt);
        Assert.assertEquals(zeroPointString, zeroRat);
        Assert.assertEquals(zeroPointString, zeroString);

    }

    @Test
    public void testIndependenceFromRedundantZeros() {
        int base = 7;
        PAdic a = new PAdic("1", base);
        PAdic b = new PAdic("1.0", base);
        PAdic c = new PAdic("00001", base);
        PAdic d = new PAdic("1.000000", base);
        PAdic e = new PAdic("000001.000000", base);

        Assert.assertEquals(a, a);
        Assert.assertEquals(a, b);
        Assert.assertEquals(a, c);
        Assert.assertEquals(a, d);
        Assert.assertEquals(a, e);
        Assert.assertEquals(b, b);
        Assert.assertEquals(b, c);
        Assert.assertEquals(b, d);
        Assert.assertEquals(b, e);
        Assert.assertEquals(c, c);
        Assert.assertEquals(c, d);
        Assert.assertEquals(c, e);
        Assert.assertEquals(d, d);
        Assert.assertEquals(d, e);
        Assert.assertEquals(e, e);
    }

    @Test
    public void testSequenceRepresentation() {
        int[] a = {6, 5, 4, 3, 2, 1};
        PAdic x = new PAdic(a, -3, 7);
        PAdic result = new PAdic("123.456", 7);

        Assert.assertEquals(result, x);

        a = new int[] {2};
        x = new PAdic(a, 2, 7);
        result = new PAdic("200", 7);

        Assert.assertEquals(result, x);

        a = new int[] {0, 0, 0, 0, 0, 2, 3, 0, 0, 0, 0};
        x = new PAdic(a, -2, 7);
        result = new PAdic("0.32", 7);
        Assert.assertEquals(result, x);

        a = new int[] {0, 0, 0, 0, 0, 2, 3};
        Assert.assertEquals(result, x);

        a = new int[] {2, 3};
        Assert.assertEquals(result, x);

        x = new PAdic(a, 2, 7);
        result = new PAdic("3200", 7);
        Assert.assertEquals(result, x);

        a = new int[] {2, 3, 4, 5};
        x = new PAdic(a, 2, 7);
        result = new PAdic("543200", 7);
        Assert.assertEquals(result, x);

        x = new PAdic(a, -2, 7);
        result = new PAdic("54.32", 7);
        Assert.assertEquals(result, x);

        a = new int[] {0, 0, 0, 0, 0, 0, 2, 3, 4};
        x = new PAdic(a, 2, 7);
        result = new PAdic("43200", 7);
        Assert.assertEquals(result, x);
    }
}
