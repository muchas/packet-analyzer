package sample.entities;


import sample.FilteringContext;
import sample.Packet;

import javax.script.ScriptException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Filter {
    private String id;
    private String name;
    private String body;

    public Filter(String code) {
        String lines[] = code.split("\\r?\\n");

        String bodyLines[] = Arrays.copyOfRange(lines, 1, Array.getLength(lines));


        this.body = String.join("", bodyLines);
    }

    public Filter(String name, String body) {
        this.name = name;
        this.body = body;
    }

    public String getCode() {
        return this.addToFunctionScope(body);
    }

    public String getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean apply(Packet packet, FilteringContext context) throws ScriptException {
        try {
            Object result = context.getEngine().invokeFunction(name, packet);
            return (boolean) result;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }



    private String addToFunctionScope(String functionBody) {
        return String.join("\n", new String[] {
                "function " + name + "(packet) { ",
                    functionBody,
                "}"
        });
    }
}
