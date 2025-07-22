package org.example.filter.builder;

import org.example.filter.*;
import org.example.filter.composite.*;
import org.example.filter.literal.*;

public final class Filters {

    private Filters() {
    }

    // literals
    public static Filter alwaysTrue() {
        return new TrueFilter();
    }

    public static Filter alwaysFalse() {
        return new FalseFilter();
    }

    // logical
    public static Filter and(Filter... filters) {
        return new AndFilter(filters);
    }

    public static Filter or (Filter... filters) {
        return new OrFilter(filters);
    }

    public static Filter not(Filter filter) {
        return new NotFilter(filter);
    }

    // property entry‑point
    public static PropertyFilterBuilder property(String name) {
        return new PropertyFilterBuilder(name);
    }
}