import org.junit.Assert;
import org.junit.Test;
import vvk.numbers.PAdic;

public class PAdicTest {

    @Test
    public void testOrder() {
        Assert.assertEquals(2, new PAdic("12300").getOrder());
        Assert.assertEquals(-2, new PAdic("123.12").getOrder());
        Assert.assertEquals(0, new PAdic("12312").getOrder());
        Assert.assertEquals(-2, new PAdic("123.120000").getOrder());
        Assert.assertEquals(-1, new PAdic(5, 25).getOrder());
        Assert.assertEquals(1, new PAdic(25, 5).getOrder());
        Assert.assertEquals(2, new PAdic(25, 3).getOrder());
        Assert.assertEquals(-2, new PAdic(3, 25).getOrder());
        Assert.assertEquals(0, new PAdic(3, 6).getOrder());
        Assert.assertEquals(1, new PAdic(5).getOrder());
        Assert.assertEquals(2, new PAdic(25).getOrder());
        Assert.assertEquals(1, new PAdic(5, 6).getOrder());
        Assert.assertEquals(0, new PAdic(0, 1).getOrder());
    }

    @Test
    public void testZero() {
        PAdic zeroString = new PAdic("0");
        PAdic zeroInt = new PAdic(0);
        PAdic zeroRat = new PAdic(0, 1);

        Assert.assertEquals(zeroString, PAdic.ZERO);
        Assert.assertEquals(zeroInt, PAdic.ZERO);
        Assert.assertEquals(zeroRat, PAdic.ZERO);
        Assert.assertEquals(zeroString, zeroInt);
        Assert.assertEquals(zeroString, zeroRat);
        Assert.assertEquals(zeroRat, zeroInt);
    }

    @Test
    public void testEquality() {
        PAdic rational = new PAdic(5, 1);
        PAdic rationalNeg = new PAdic(-5, -1);
        PAdic string = new PAdic("10");
        PAdic integer = new PAdic(5);

        Assert.assertEquals(rational, string);
        Assert.assertEquals(string, integer);
        Assert.assertEquals(rational, integer);

        Assert.assertEquals(rationalNeg, string);
        Assert.assertEquals(rationalNeg, rational);
        Assert.assertEquals(rationalNeg, integer);

        PAdic point1 = new PAdic("0.1");
        PAdic point2 = new PAdic("0.01");
        PAdic point3 = new PAdic("0.001");
        PAdic rat1 = new PAdic(1, 5);
        PAdic rat2 = new PAdic(1, 25);
        PAdic rat3 = new PAdic(1, 125);

        Assert.assertEquals(point1, rat1);
        Assert.assertEquals(point2, rat2);
        Assert.assertEquals(point3, rat3);

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

    }

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

    @Test
    public void testSubstraction() {
        PAdic rat = new PAdic(1, 5);
        PAdic point = new PAdic("0.1");

        Assert.assertEquals(rat.getOrder(), point.getOrder());
        Assert.assertEquals(-1, point.getOrder());
        Assert.assertEquals(rat.substract(point), PAdic.ZERO);
        Assert.assertEquals(point.substract(rat), PAdic.ZERO);

        PAdic one = new PAdic("1");
        Assert.assertEquals(one.substract(one).getOrder(), PAdic.ZERO.getOrder());
        Assert.assertEquals(one.substract(one), PAdic.ZERO);

        PAdic one3 = new PAdic("11.1");
        PAdic one1 = new PAdic("10");

        PAdic result = new PAdic("0.1");
        Assert.assertEquals(one3.substract(one1).substract(one), result);
    }

    @Test
    public void testMultiplication() {
        PAdic a = new PAdic("123.4");
        PAdic one = new PAdic("1");
        Assert.assertEquals(-1, a.getOrder());
        Assert.assertEquals(0, one.getOrder());
        Assert.assertEquals(a, a.multiply(one));
        Assert.assertEquals(a.multiply(PAdic.ZERO), PAdic.ZERO);
        Assert.assertEquals(a.multiply(PAdic.ZERO), PAdic.ZERO);

        PAdic two = new PAdic("2222");
        PAdic point = new PAdic("0.1");
        PAdic result = new PAdic("222.2");
        Assert.assertEquals(two.multiply(point), result);
    }

    @Test
    public void testDivision() {
        PAdic twoPoint = new PAdic("222.2");
        PAdic onePoint = new PAdic("111.1");
        PAdic two = new PAdic(2);

        Assert.assertEquals(two, twoPoint.divide(onePoint));
        Assert.assertEquals(onePoint, twoPoint.divide(two));
    }
}
