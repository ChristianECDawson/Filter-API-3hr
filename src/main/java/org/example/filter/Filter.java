package org.example.filter;

import java.util.Map;

public interface Filter {

    boolean matches(Map<String, String> resource);

}