
import org.junit.Test;
import static org.junit.Assert.*;
import sunrisedentalclinic.model.Bill;

public class BillTest {

    @Test
    public void testCalculateTotal() {

        Bill bill = new Bill();
        bill.setConsultationFee(50.0);
        bill.setTreatmentCost(150.0);

        double actual = bill.calculateTotal();

        assertEquals("Total should be sum of consultation and treatment", 200.0, actual, 0.001);
    }

    @Test
    public void testConstructorAutoCalculation() {

        Bill bill = new Bill(1, 101, 100.0, 300.0, "2026-10-15");

        assertEquals("Constructor should auto-calculate total", 400.0, bill.getTotalAmount(), 0.001);
    }
}
