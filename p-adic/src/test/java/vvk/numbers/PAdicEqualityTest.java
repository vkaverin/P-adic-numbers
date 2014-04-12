package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PAdicEqualityTest {

    @Test
    public void testEquality() {
        final int base = 5;

        PAdic rational1 = new PAdic(5, 1, base);
        PAdic rational1_neg = new PAdic(-5, -1, base);
        PAdic string1 = new PAdic("10", base);
        PAdic integer1 = new PAdic(5, base);

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
        PAdic rat1 = new PAdic(1, 5, base);
        PAdic rat2 = new PAdic(1, 25, base);
        PAdic rat3 = new PAdic(1, 125, base);

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

        PAdic int1 = new PAdic(2, base);
        PAdic strx = new PAdic("2", base);

        PAdic ratx = new PAdic(1, 2, base);
        PAdic raty = new PAdic(-1, -2, base);

        PAdic ratz = new PAdic(2, 1, base);
        PAdic ratn = new PAdic(-2, -1, base);

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
        PAdic zeroInt = new PAdic(0, base);
        PAdic zeroRat = new PAdic(0, 1, base);

        Assert.assertEquals(zeroString, zeroInt);
        Assert.assertEquals(zeroString, zeroRat);
        Assert.assertEquals(zeroRat, zeroInt);
    }
}
