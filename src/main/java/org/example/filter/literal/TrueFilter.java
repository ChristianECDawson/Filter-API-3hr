package org.example.filter.literal;

import org.example.filter.Filter;

import java.util.Map;

public record TrueFilter() implements Filter {

    @Override
    public boolean matches(Map<String, String> resource) {
        return true;
    }

}
