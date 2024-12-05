package lab_3a;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTicketPriceCalculatorTest {

    private MovieTicketPriceCalculator calculator;

    @BeforeEach
    public void setUp() {
        LocalTime startMatinee = LocalTime.of(13, 0);  // 1:00 PM
        LocalTime endMatinee = LocalTime.of(17, 0);    // 5:00 PM
        int maxChildAge = 12;
        int minSeniorAge = 65;

        calculator = new MovieTicketPriceCalculator(startMatinee, endMatinee, maxChildAge, minSeniorAge);
    }

    @Test
    public void testStandardPriceForAdult() {
        LocalTime showTime = LocalTime.of(19, 0);  // 7:00 PM (not a matinee)
        int age = 30;  // Adult age
        int expectedPrice = 2700;  // Standard price in cents
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testMatineePriceForSenior() {
        LocalTime showTime = LocalTime.of(14, 0);  // 2:00 PM (matinee)
        int age = 70;  // Senior age
        int expectedPrice = 2000;  // Matinee price - senior discount
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testMatineePriceForChild() {
        LocalTime showTime = LocalTime.of(15, 0);  // 3:00 PM (matinee)
        int age = 10;  // chile age
        int expectedPrice = 2100;  // matinee price - child discount
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testMatineePriceForAdult() {
        LocalTime showTime = LocalTime.of(14, 0);  // 2:00 PM (matinee)
        int age = 30;  // adult age
        int expectedPrice = 2400;  // matinee price for adult
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testStandardPriceForSenior() {
        LocalTime showTime = LocalTime.of(18, 0);  // 6:00 PM (not a matinee)
        int age = 70;  // Senior age
        int expectedPrice = 2300;  // Standard price - senior discount
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testInvalidMatineeTime() {
        LocalTime invalidStartMatinee = LocalTime.of(18, 0);  // 6:00 PM
        LocalTime invalidEndMatinee = LocalTime.of(14, 0);    // 2:00 PM
        int maxChildAge = 12;
        int minSeniorAge = 65;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new MovieTicketPriceCalculator(invalidStartMatinee, invalidEndMatinee, maxChildAge, minSeniorAge);
        });

        assertEquals("matinee start time cannot come after end time", exception.getMessage());
    }

    @Test
    public void testDiscountForExactChildAge() {
        LocalTime showTime = LocalTime.of(14, 0);  // 2:00 PM (matinee)
        int age = 12;  // Max child age
        int expectedPrice = 2400 - 300;  // Matinee price - child discount
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testDiscountForExactSeniorAge() {
        LocalTime showTime = LocalTime.of(14, 0);  // 2:00 PM (matinee)
        int age = 65;  // Min senior age
        int expectedPrice = 2400 - 400;  // Matinee price - senior discount
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testMatineePriceAtExactStart() {
        LocalTime showTime = LocalTime.of(13, 0);  // Exact matinee start time
        int age = 30;  // Adult
        int expectedPrice = 2400;  // Matinee price
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testStandardPriceAfterMatinee() {
        LocalTime showTime = LocalTime.of(18, 0);  // 6:00 PM (after matinee time)
        int age = 30;  // Adult
        int expectedPrice = 2700;  // Standard price
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testSeniorDiscountBoundary() {
        LocalTime showTime = LocalTime.of(14, 0);  // Matinee time
        int age = 65;  // Senior, exactly at the boundary
        int expectedPrice = 2400 - 400;  // Matinee price - senior discount
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testNoDiscountForAdult() {
        LocalTime showTime = LocalTime.of(14, 0);  // Matinee time
        int age = 30;  // Adult, no discount
        int expectedPrice = 2400;  // Matinee price (no discount)
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testMatineePriceJustBeforeEnd() {
        LocalTime showTime = LocalTime.of(15, 59);  // Just one minute before matinee ends
        int age = 30;  // Adult
        int expectedPrice = 2400;  // Matinee price
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }

    @Test
    public void testStandardPriceBeforeMatinee() {
        LocalTime showTime = LocalTime.of(12, 0);  // Before matinee starts
        int age = 25;  // Adult (no discount)
        int expectedPrice = 2700;  // Standard price (no discount)
        assertEquals(expectedPrice, calculator.computePrice(showTime, age));
    }
}
