package sample;

import sample.entities.Filter;

import javax.script.ScriptException;
import java.util.List;


public class FilteringContext {

    private List<Filter> filters;
    private JavaScriptEngine engine;

    public FilteringContext(List<Filter> filters) {
        this.filters = filters;
        this.engine = new JavaScriptEngine();

        for(Filter filter: filters) {
            try {
                this.engine.eval(filter.getCode());
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Filter filter) {
        this.filters.add(filter);

        try {
            this.engine.eval(filter.getCode());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public JavaScriptEngine getEngine() {
        return engine;
    }
}
