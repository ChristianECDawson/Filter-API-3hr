package org.example.filter.property;


import java.util.Map;

public final class EqualsFilter extends PropertyFilter {

    private final String target;

    public EqualsFilter(String name, String target) {
        super(name);
        this.target = target;
    }

    @Override public boolean matches(Map<String,String> resource) {
        String v = value(resource);
        return v != null && v.equalsIgnoreCase(target);
    }

}