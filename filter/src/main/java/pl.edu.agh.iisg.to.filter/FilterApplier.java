package pl.edu.agh.iisg.to.filter;

import pl.edu.agh.iisg.to.filter.entities.Filter;

import java.util.List;

public class FilterApplier {

    private FilteringContext context;
    private List<Filter> activeFilters;

    FilterApplier(List<Filter> activeFilters, FilteringContext context) {
        this.activeFilters = activeFilters;
        this.context = context;
    }

    boolean apply(Packet packet) {
        for (Filter filter: activeFilters) {
            try {
                if(!context.apply(filter, packet)) return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
