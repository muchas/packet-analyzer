package pl.edu.agh.iisg.to.filter;


import pl.edu.agh.iisg.to.filter.entities.Filter;

import java.util.List;

public interface FilterStorage {
    public boolean save(Filter filter);
    public boolean delete(Filter filter);
    public List<Filter> loadAll();
}
