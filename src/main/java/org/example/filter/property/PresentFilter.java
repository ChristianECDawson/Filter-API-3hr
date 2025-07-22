package org.example.filter.property;

import java.util.Map;

public final class PresentFilter extends PropertyFilter {

    public PresentFilter(String name) {
        super(name);
    }

    @Override public boolean matches(Map<String,String> resource) {
        return resource.containsKey(name);
    }
}