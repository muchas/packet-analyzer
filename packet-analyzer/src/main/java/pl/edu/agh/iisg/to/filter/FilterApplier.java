package pl.edu.agh.iisg.to.filter;

import pl.edu.agh.iisg.to.collector.Packet;
import pl.edu.agh.iisg.to.filter.entities.Filter;

import java.util.List;

public class FilterApplier {

    private FilteringContext context;
    private List<Filter> filters;

    public FilterApplier(List<Filter> filters, FilteringContext context) {
        this.filters = filters;
        this.context = context;
    }

    boolean apply(Packet packet) {
        for (Filter filter: filters) {
            if(!filter.getIsActive()) {
                continue;
            }

            try {
                if(!context.apply(filter, packet)) return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
