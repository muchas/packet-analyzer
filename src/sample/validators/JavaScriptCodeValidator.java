package sample.validators;


import sample.JavaScriptEngine;

import javax.script.ScriptException;

public class JavaScriptCodeValidator extends BaseValidator {

    private String functionName;
    private String code;
    private JavaScriptEngine engine;

    public JavaScriptCodeValidator(String functionName, String code, JavaScriptEngine engine) {
        this.engine = engine;
        this.functionName = functionName;
        this.code = code;
    }

    @Override
    public boolean validate() {
        try {
            engine.eval(code);
            engine.invokeFunction(functionName);
            return true;

        } catch (ScriptException e) {
            errors.add(e.getMessage());
        } catch (NoSuchMethodException e) {
            errors.add(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
