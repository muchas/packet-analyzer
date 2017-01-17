import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.iisg.to.filter.JavaScriptEngine;
import pl.edu.agh.iisg.to.filter.validators.JavaScriptFilterCodeValidator;
import pl.edu.agh.iisg.to.filter.validators.Validator;


import static org.junit.Assert.*;

public class JavaScriptFilterCodeValidatorTest {

    private JavaScriptEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new JavaScriptEngine();
    }

    @Test
    public void testValidateWithInvalidJavascript() throws Exception {
        String code = "function func1(packet) { if() { invalid_js } }";
        Validator validator = new JavaScriptFilterCodeValidator("func1", code, engine);

        assertFalse(validator.validate());
        assertFalse(validator.getErrors().isEmpty());
    }

    @Test
    public void testValidateWithCallToNotExistingFunction() throws Exception {
        String code = "function func1(packet) { return func2(); }";
        Validator validator = new JavaScriptFilterCodeValidator("func1", code, engine);

        assertFalse(validator.validate());
        assertFalse(validator.getErrors().isEmpty());
    }

    @Test
    public void testValidateValidCall() throws Exception {
        String code = "function func1(packet) { if(packet.getProtocol()) { return true; } return false; }";
        Validator validator = new JavaScriptFilterCodeValidator("func1", code, engine);

        assertTrue(validator.validate());
        assertTrue(validator.getErrors().isEmpty());
    }
}