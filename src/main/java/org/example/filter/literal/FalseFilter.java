package org.example.filter.literal;

import org.example.filter.Filter;

import java.util.Map;

public record FalseFilter() implements Filter {

    @Override
    public boolean matches(Map<String, String> resource) {
        return false;
    }

}
