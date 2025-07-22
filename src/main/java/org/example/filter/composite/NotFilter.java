package org.example.filter.composite;

import org.example.filter.Filter;

import java.util.Map;

/***
 * NotFilter can be a record, even though AND & OR are similar, they use varargs constructors
 * @param filter
 */
public record NotFilter(Filter filter) implements Filter {

    @Override
    public boolean matches(Map<String, String> resource) {
        return !filter.matches(resource);
    }

}