package pl.edu.agh.iisg.to.filter;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class JavaScriptEngine {
    private ScriptEngine engine;

    public JavaScriptEngine() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    public void eval(String code) throws ScriptException {
        engine.eval(code);
    }

    public Object invokeFunction(String functionName, Object... args) throws NoSuchMethodException, ScriptException {
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(functionName, args);
    }
}
