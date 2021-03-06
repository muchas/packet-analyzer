import org.junit.Test;
import pl.edu.agh.iisg.to.filter.validators.NotEmptyValueValidator;
import pl.edu.agh.iisg.to.filter.validators.Validator;


import static org.junit.Assert.*;

public class NotEmptyValueValidatorTest {

    @Test
    public void testValidateWithEmptyValue() throws Exception {
        Validator validator = new NotEmptyValueValidator("field", "");

        assertFalse(validator.validate());
        assertEquals(validator.getErrors().get(0), "field: This value should not be empty");
    }

    @Test
    public void testValidateWithWhiteChars() throws Exception {
        Validator validator = new NotEmptyValueValidator("field", "             ");

        assertFalse(validator.validate());
        assertEquals(validator.getErrors().get(0), "field: This value should not be empty");
    }

    @Test
    public void testValidate() throws Exception {
        Validator validator = new NotEmptyValueValidator("field", "       string      ");

        assertTrue(validator.validate());
        assertTrue(validator.getErrors().isEmpty());
    }
}