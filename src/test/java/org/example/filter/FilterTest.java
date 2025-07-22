package org.example.filter;

import org.example.filter.builder.Filters;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/***
 * If project were to increase in scale, consider @ParameterizedTest
 */

class FilterTest {

    /* ======= Literal Filters ======= */

    @Test
    void literalTrueFilterShouldAlwaysMatch() {
        Filter alwaysTrue = Filters.alwaysTrue();
        assertTrue(alwaysTrue.matches(createUser("Joe", "Bloggs", "admin", "30", "joe@example.com")));
    }

    @Test
    void literalFalseFilterShouldNeverMatch() {
        Filter alwaysFalse = Filters.alwaysFalse();
        assertFalse(alwaysFalse.matches(createUser("Joe", "Bloggs", "admin", "30", "joe@example.com")));
    }

    /* ======= Property: Present ======= */

    @Test
    void presentFilterShouldMatchWhenPropertyExists() {
        Filter present = Filters.property("firstname").present();
        assertTrue(present.matches(createUser("Alice", "Smith", "user", "40", "alice@example.com")));
    }

    @Test
    void presentFilterShouldNotMatchWhenPropertyMissing() {
        Filter present = Filters.property("nickname").present();
        assertFalse(present.matches(createUser("Alice", "Smith", "user", "40", "alice@example.com")));
    }

    /* ======= Property: Equals ======= */

    @Test
    void equalsFilterShouldMatchWhenValuesEqualIgnoringCase() {
        Filter equalsFilter = Filters.property("role").eq("Administrator");
        assertTrue(equalsFilter.matches(createUser("Bob", "James", "administrator", "45", "bob@example.com")));
    }

    @Test
    void equalsFilterShouldNotMatchWhenValuesDiffer() {
        Filter equalsFilter = Filters.property("role").eq("manager");
        assertFalse(equalsFilter.matches(createUser("Bob", "James", "user", "45", "bob@example.com")));
    }

    /* ======= Property: Greater Than ======= */

    @Test
    void greaterThanFilterShouldMatchWhenNumericValueIsGreater() {
        Filter gtFilter = Filters.property("age").gt("30");
        assertTrue(gtFilter.matches(createUser("Carol", "Stone", "editor", "40", "carol@example.com")));
    }

    @Test
    void greaterThanFilterShouldNotMatchWhenNumericValueIsLess() {
        Filter gtFilter = Filters.property("age").gt("30");
        assertFalse(gtFilter.matches(createUser("Carol", "Stone", "editor", "25", "carol@example.com")));
    }

    /* ======= Property: Less Than ======= */

    @Test
    void lessThanFilterShouldMatchWhenNumericValueIsSmaller() {
        Filter ltFilter = Filters.property("age").lt("30");
        assertTrue(ltFilter.matches(createUser("Dan", "Turner", "intern", "22", "dan@example.com")));
    }

    @Test
    void lessThanFilterShouldNotMatchWhenNumericValueIsGreaterOrEqual() {
        Filter ltFilter = Filters.property("age").lt("30");
        assertFalse(ltFilter.matches(createUser("Dan", "Turner", "intern", "30", "dan@example.com")));
    }

    /* ======= Property: Regex ======= */

    @Test
    void regexFilterShouldMatchWhenPatternMatches() {
        Filter regexFilter = Filters.property("email").matches(".*@example\\.com");
        assertTrue(regexFilter.matches(createUser("Eve", "Black", "analyst", "28", "eve@example.com")));
    }

    @Test
    void regexFilterShouldNotMatchWhenPatternDoesNotMatch() {
        Filter regexFilter = Filters.property("email").matches(".*@example\\.com");
        assertFalse(regexFilter.matches(createUser("Eve", "Black", "analyst", "28", "eve@another.com")));
    }

    /* ======= Logical: AND ======= */

    @Test
    void andFilterShouldMatchWhenAllConditionsAreTrue() {
        Filter andFilter = Filters.and(
                Filters.property("role").eq("user"),
                Filters.property("age").gt("18")
        );
        assertTrue(andFilter.matches(createUser("Frank", "West", "user", "25", "frank@example.com")));
    }

    @Test
    void andFilterShouldNotMatchWhenAnyConditionIsFalse() {
        Filter andFilter = Filters.and(
                Filters.property("role").eq("user"),
                Filters.property("age").gt("30")
        );
        assertFalse(andFilter.matches(createUser("Frank", "West", "user", "25", "frank@example.com")));
    }

    /* ======= Logical: OR ======= */

    @Test
    void orFilterShouldMatchWhenAtLeastOneConditionIsTrue() {
        Filter orFilter = Filters.or(
                Filters.property("role").eq("admin"),
                Filters.property("email").matches(".*@example\\.com")
        );
        assertTrue(orFilter.matches(createUser("Grace", "Lee", "user", "33", "grace@example.com")));
    }

    @Test
    void orFilterShouldNotMatchWhenAllConditionsAreFalse() {
        Filter orFilter = Filters.or(
                Filters.property("role").eq("admin"),
                Filters.property("email").matches(".*@company\\.org")
        );
        assertFalse(orFilter.matches(createUser("Grace", "Lee", "user", "33", "grace@other.com")));
    }

    /* ======= Logical: NOT ======= */

    @Test
    void notFilterShouldMatchWhenInnerConditionIsFalse() {
        Filter notFilter = Filters.not(Filters.property("role").eq("admin"));
        assertTrue(notFilter.matches(createUser("Harry", "Nguyen", "user", "29", "harry@example.com")));
    }

    @Test
    void notFilterShouldNotMatchWhenInnerConditionIsTrue() {
        Filter notFilter = Filters.not(Filters.property("role").eq("admin"));
        assertFalse(notFilter.matches(createUser("Harry", "Nguyen", "admin", "29", "harry@example.com")));
    }

    /* ======= Complex Composition Test ======= */

    @Test
    void testComplexFilterConstruction() {
        Map<String, String> user = createUser("Alice", "Smith", "user", "42", "alice.smith@example.com");

        Filter complexFilter = Filters.and(
                Filters.or(
                        Filters.property("role").eq("administrator"),
                        Filters.and(
                                Filters.property("age").gt("40"),
                                Filters.property("email").matches(".*@example\\.com")
                        )
                ),
                Filters.property("firstname").present()
        );

        assertTrue(complexFilter.matches(user));

        Map<String, String> user2 = new LinkedHashMap<>(user);
        user2.put("email", "alice@not-example.org");
        assertFalse(complexFilter.matches(user2));

        Map<String, String> user3 = new LinkedHashMap<>(user);
        user3.remove("firstname");
        assertFalse(complexFilter.matches(user3));
    }

    /* ======= Utility Methods ======= */

    /***
     * Different string values used in each test to show use of utility method
     *
     * @param firstName
     * @param surname
     * @param role
     * @param age
     * @param email
     * @return
     */
    private Map<String, String> createUser(String firstName, String surname, String role, String age, String email) {
        Map<String, String> user = new LinkedHashMap<>();
        if (firstName != null){
            user.put("firstname", firstName);
        }
        if (surname != null) {
            user.put("surname", surname);
        }
        if (role != null) {
            user.put("role", role);
        }
        if (age != null) {
            user.put("age", age);
        }
        if (email != null) {
            user.put("email", email);
        }
        return user;
    }

}
