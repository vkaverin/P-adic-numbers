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
    }

    @Test
    public void testZero() {
        PAdic zero = new PAdic("0");
        Assert.assertEquals(zero, PAdic.ZERO);
    }

    @Test
    public void testEquality() {
        PAdic rational = new PAdic(5, 1);
        PAdic string = new PAdic("10");
        PAdic integer = new PAdic(5);

        Assert.assertEquals(rational.getOrder(), string.getOrder());
        Assert.assertEquals(rational, string);
        Assert.assertEquals(string, integer);
        Assert.assertEquals(rational, integer);
    }

    @Test
    public void testAddition() {
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
