# hibernate-test

Plain Hibernate/JPA test project (no Quarkus runtime).

## What it contains

- JPA entities under `src/main/java/test/hibernate`
- Test-only persistence configuration in `src/test/resources/META-INF/persistence.xml`
- Integration test in `src/test/java/test/hibernate/FooBarCollectorEntityGraphIT.java`

## Run tests

Run unit tests:

```bash
./mvnw test
```

Run integration tests (`*IT.java`) with Failsafe:

```bash
./mvnw verify
```



