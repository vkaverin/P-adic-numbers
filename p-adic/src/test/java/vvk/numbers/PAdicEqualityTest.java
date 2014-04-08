package vvk.numbers;

import org.junit.Assert;
import org.junit.Test;

public class PAdicEqualityTest {

    @Test
    public void testEquality() {
        PAdic rational1 = new PAdic(5, 1);
        PAdic rational1_neg = new PAdic(-5, -1);
        PAdic string1 = new PAdic("10");
        PAdic integer1 = new PAdic(5);

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

        PAdic point1 = new PAdic("0.1");
        PAdic point2 = new PAdic("0.01");
        PAdic point3 = new PAdic("0.001");
        PAdic point4 = new PAdic("0000.100000");
        PAdic rat1 = new PAdic(1, 5);
        PAdic rat2 = new PAdic(1, 25);
        PAdic rat3 = new PAdic(1, 125);

        Assert.assertEquals(point1, rat1);
        Assert.assertEquals(point2, rat2);
        Assert.assertEquals(point3, rat3);
        Assert.assertEquals(point4, rat1);
        Assert.assertEquals(point4, point1);

        PAdic str1 = new PAdic("01.0");
        PAdic str2 = new PAdic("1");

        Assert.assertEquals(str1, str2);

        str1 = new PAdic("00000002.0000000");
        str2 = new PAdic("00000002.0");
        PAdic str3 = new PAdic("2.0000000");
        PAdic str4 = new PAdic("2");

        Assert.assertEquals(str1, str2);
        Assert.assertEquals(str2, str3);
        Assert.assertEquals(str3, str4);
        Assert.assertEquals(str4, str1);
        Assert.assertEquals(str4, str2);

        PAdic int1 = new PAdic(2);
        PAdic strx = new PAdic("2");

        PAdic ratx = new PAdic(1, 2);
        PAdic raty = new PAdic(-1, -2);

        PAdic ratz = new PAdic(2, 1);
        PAdic ratn = new PAdic(-2, -1);

        Assert.assertEquals(int1, strx);
        Assert.assertEquals(ratx, raty);
        Assert.assertEquals(ratz, ratn);
        Assert.assertEquals(strx, ratn);

        PAdic point7 = new PAdic("0.2");
        PAdic point5 = new PAdic("0.3");
        PAdic point6 = new PAdic("0.0002");

        Assert.assertEquals(new PAdic("1"), point7.add(point5));
        Assert.assertEquals(new PAdic("0.2002"), point7.add(point6));
    }
}
