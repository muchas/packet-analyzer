package sample;

import sample.entities.Filter;

import java.util.List;

public class FilterApplier {
    boolean applyFilters(FilteringContext context, Packet packet, List<Filter> activeFilters) {
        boolean isValid = true;

        for (Filter filter : activeFilters) {
            try {
                if(!context.apply(filter, packet)) isValid = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isValid;
    }
}
