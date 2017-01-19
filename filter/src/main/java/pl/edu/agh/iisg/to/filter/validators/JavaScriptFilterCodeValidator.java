package pl.edu.agh.iisg.to.filter.validators;




import pl.edu.agh.iisg.to.filter.JavaScriptEngine;
import pl.edu.agh.iisg.to.filter.Packet;

import javax.script.ScriptException;

public class JavaScriptFilterCodeValidator extends BaseValidator {

    private String functionName;
    private String code;
    private JavaScriptEngine engine;

    public JavaScriptFilterCodeValidator(String functionName, String code, JavaScriptEngine engine) {
        this.engine = engine;
        this.functionName = functionName;
        this.code = code;
    }

    @Override
    public boolean validate() {
        try {
            engine.eval(code);
            engine.invokeFunction(functionName, getTestPacket());
            return true;

        } catch (ScriptException e) {
            errors.add(e.getMessage());
        } catch (NoSuchMethodException e) {
            errors.add(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private Packet getTestPacket() {
        return new Packet();
    }
}
