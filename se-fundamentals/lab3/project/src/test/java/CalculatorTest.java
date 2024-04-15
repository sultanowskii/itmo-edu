import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {
    @Test
    public void testSum() {
        int a = 5;
        int b = 3;

        int result = Calculator.sum(a, b);

        Assert.assertEquals(8, result);
    }

    @Test
    public void testSumWithNegative() {
        int a = -5;
        int b = 3;

        int result = Calculator.sum(a, b);

        Assert.assertEquals(-2, result);
    }

    @Test
    public void testSumWithZero() {
        int a = 3;
        int b = 0;

        int result = Calculator.sum(a, b);

        Assert.assertEquals(3, result);
    }

    @Test
    public void testMul() {
        int a = 5;
        int b = 3;

        int result = Calculator.mul(a, b);

        Assert.assertEquals(15, result);
    }

    @Test
    public void testMulWithNegative() {
        int a = 5;
        int b = -3;

        int result = Calculator.mul(a, b);

        Assert.assertEquals(-15, result);
    }

    @Test
    public void testMulWithBothNegatives() {
        int a = -5;
        int b = -3;

        int result = Calculator.mul(a, b);

        Assert.assertEquals(15, result);
    }

    @Test
    public void testMulWithZero() {
        int a = 5;
        int b = 0;

        int result = Calculator.mul(a, b);

        Assert.assertEquals(0, result);
    }

    @Test
    public void testMulWithBothZeros() {
        int a = 0;
        int b = 0;

        int result = Calculator.mul(a, b);

        Assert.assertEquals(0, result);
    }
}
