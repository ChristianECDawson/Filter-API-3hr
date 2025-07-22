package org.example.filter.property;

import java.util.Map;

public final class LessThanFilter extends PropertyFilter {

    private final String target;

    public LessThanFilter(String name, String target) {
        super(name);
        this.target = target;
    }

    @Override public boolean matches(Map<String,String> resource) {
        String value = value(resource);
        if (value == null) return false;
        try {
            return Double.parseDouble(value) < Double.parseDouble(target);
        }
        catch (NumberFormatException nfe) {
            return value.compareToIgnoreCase(target) < 0;
        }
    }

}