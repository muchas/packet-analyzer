package sample;

import sample.entities.Filter;

import javax.script.ScriptException;
import java.util.List;

public class FilterApplier {
    boolean applyFilters(FilteringContext context, Packet packet, List<Filter> activeFilters) {
        boolean is_valid = true;

        for (Filter filter : activeFilters) {
            try {
                if (!filter.apply(context, packet)) is_valid = false;
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        return is_valid;
    }
}
