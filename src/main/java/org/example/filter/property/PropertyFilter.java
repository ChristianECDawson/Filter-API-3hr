package org.example.filter.property;

import org.example.filter.Filter;
import java.util.Map;

abstract class PropertyFilter implements Filter {

    protected final String name;

    PropertyFilter(String name) {
        this.name = name;
    }

    protected String value(Map<String,String> resource) {
        return resource.get(name);
    }

}