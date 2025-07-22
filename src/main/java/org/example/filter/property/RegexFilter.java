package org.example.filter.property;

import java.util.Map;
import java.util.regex.Pattern;

public final class RegexFilter extends PropertyFilter {

    private final Pattern pattern;

    public RegexFilter(String name, String regex) {
        super(name);
        this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    @Override public boolean matches(Map<String,String> resource) {
        String value = value(resource);
        return value != null && pattern.matcher(value).matches();
    }

}