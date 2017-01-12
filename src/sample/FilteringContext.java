package sample;

import sample.entities.Filter;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FilteringContext {

    private Map<String, Filter> filterMap;
    private JavaScriptEngine engine;

    public FilteringContext() {
        this.filterMap = new HashMap<>();
        this.engine = new JavaScriptEngine();
    }

    public FilteringContext(List<Filter> filters) {
        this.filterMap = new HashMap<>();
        this.engine = new JavaScriptEngine();

        filters.forEach(this::add);
    }

    public void add(Filter filter) {
        this.filterMap.put(filter.getName(), filter);

        try {
            this.engine.eval(filter.getCode());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public boolean apply(Filter filter, Packet packet) throws Exception {
        if(!filterMap.containsKey(filter.getName())) {
            throw new Exception("Filter out of the context");
        }

        try {
            Object result = engine.invokeFunction(filter.getName(), packet);
            return (boolean) result;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    public JavaScriptEngine getEngine() {
        return engine;
    }
}
