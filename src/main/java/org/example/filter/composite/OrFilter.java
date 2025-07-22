package org.example.filter.composite;

import org.example.filter.Filter;

import java.util.List;
import java.util.Map;

public final class OrFilter implements Filter {

    private final List<Filter> filters;

    public OrFilter(Filter... filters) {
        this.filters = List.of(filters);
    }

    @Override public boolean matches(Map<String,String> resource) {
        return filters.stream().anyMatch(f -> f.matches(resource));
    }

}