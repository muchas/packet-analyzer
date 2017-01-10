package sample.entities;


import sample.JavaScriptEngine;
import sample.Packet;

import javax.script.Invocable;
import javax.script.ScriptException;

public class Filter {
    private String id;
    private String name;
    private String code;

    private JavaScriptEngine engine;


    public Filter(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean apply(Packet packet) throws ScriptException {
        engine.eval(code);

        Invocable invocable = (Invocable) engine;

        try {
            Object result = invocable.invokeFunction("filter", "tcp");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
