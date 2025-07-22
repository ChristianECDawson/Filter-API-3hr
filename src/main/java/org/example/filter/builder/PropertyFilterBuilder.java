package org.example.filter.builder;

import org.example.filter.Filter;
import org.example.filter.property.*;

public final class PropertyFilterBuilder {
    private final String name;

    PropertyFilterBuilder(String name) {
        this.name = name;
    }

    public Filter present() {
        return new PresentFilter(name);
    }

    public Filter eq(String value) {
        return new EqualsFilter(name, value);
    }

    public Filter lt(String value) {
        return new LessThanFilter(name, value);
    }

    public Filter gt(String value) {
        return new GreaterThanFilter(name, value);
    }

    public Filter matches(String value) {
        return new RegexFilter(name, value);
    }

}