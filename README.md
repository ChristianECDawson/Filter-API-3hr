# Filter‑API
A lightweight Java filtering framework that lets you define and evaluate filters against resources (Map<String, String>) using fluent builder syntax, logical composition, and extensible design patterns.

## Getting Started

### Requirements

* Java 17 or higher
* Maven 3.6+

### Build the Project

```bash
mvn clean install
```

This command compiles the source code and packages it as a JAR.

### 🧪 Run All Tests

```bash
mvn test
```

---

### Builder Pattern

The filter creation uses a fluent **builder pattern**, enabling readable and composable filter definitions:

```java
Filters.property("role").eq("admin")
```

Filters can be composed with logical operators:

```java
Filters.and(
  Filters.property("age").gt("30"),
  Filters.property("role").eq("admin")
)
```
Filters can be composed with complexity:

```java
Filters.and(
    Filters.or(
        Filters.property("role").eq("administrator"),
        Filters.and(
            Filters.property("age").gt("40"),
            Filters.property("email").matches(".*@example\\.com")
        )
    ),
    Filters.property("firstname").present()
)
```

This pattern encourages a declarative and intuitive filter construction style.

---

##  Unit Testing Approach

The project uses simple, clear **JUnit 5** tests for each filter category:

* One test that should match and assert `true`
* One test that should not match and assert `false`

This approach is chosen for readability and clarity in a small codebase.

### Scaling Tests

As the number of test cases grows, consider using **parameterized tests** with JUnit 5:

```java
@ParameterizedTest
@CsvSource({ "admin,true", "guest,false" })
```

This reduces duplication and keeps tests maintainable at scale.

---
