import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.iisg.to.filter.FilteringContext;
import pl.edu.agh.iisg.to.filter.entities.Filter;
import pl.edu.agh.iisg.to.filter.validators.FilterValidator;
import pl.edu.agh.iisg.to.filter.validators.Validator;

import static org.junit.Assert.*;

public class FilterValidatorTest {

    private FilteringContext context;

    @Before
    public void setUp() throws Exception {
        context = new FilteringContext();
    }

    @Test
    public void testValidateFilterWithoutName() throws Exception {
        String body = "if(packet) { return true; } return false;";
        Filter filter = new Filter("", body);
        Validator validator = new FilterValidator(filter, context);

        assertFalse(validator.validate());
        assertFalse(validator.getErrors().isEmpty());
    }

    @Test
    public void testValidateFilterWithoutBody() throws Exception {
        Filter filter = new Filter("func1", "");
        Validator validator = new FilterValidator(filter, context);

        assertFalse(validator.validate());
        assertFalse(validator.getErrors().isEmpty());
    }

    @Test
    public void testValidateFilterInvalidCode() throws Exception {
        String body = "if(packet ==== 4) { return true; } return false;";
        Filter filter = new Filter("func1", body);
        Validator validator = new FilterValidator(filter, context);

        assertFalse(validator.validate());
        assertFalse(validator.getErrors().isEmpty());
    }

    @Test
    public void testValidateFilterWithCallToNotExistingFilter() throws Exception {
        String body = "if(packet) { return func2(packet); } return false;";
        Filter filter = new Filter("func1", body);
        Validator validator = new FilterValidator(filter, context);

        assertFalse(validator.validate());
        assertFalse(validator.getErrors().isEmpty());
    }

    @Test
    public void testValidateCorrectFilter() throws Exception {
        Filter existingFilter = new Filter("func2", "return true;");
        context.add(existingFilter);

        String body = "if(packet) { return func2(packet); } return false;";
        Filter filter = new Filter("func1", body);
        Validator validator = new FilterValidator(filter, context);

        assertTrue(validator.validate());
        assertTrue(validator.getErrors().isEmpty());
    }
}