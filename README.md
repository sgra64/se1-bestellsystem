### se1-bestellsystem

##### Project branch structure:

```
<branches>
|
+--main - branch with src/Application.java
    |
    +--C1 - C1234 with datamodel.Customer.java + JUnit tests
    |
    +--dev - current development branch
```

##### Running stand alone commands:

Run Java from project directory:

```
java @resources/java-options.opt application.Application_C3
```

Run Javadoc:

```
javadoc @resources/jdoc-options.opt application datamodel
```

Run JUnit tests:

```
java @resources/junit-options.opt --scan-class-path

java @resources/junit-options.opt `cat resources/junit-classes_C4.opt`
```

##### .lib
Running javadoc with JUnit5 required in *module-info.java* and running stand-alone
JUnit tests with junit-standalone runner, assumes *./lib* package in the
project directory with:
 - `lib/org.apiguardian.jar`
 - `lib/org.junit.jar`
 - `lib/org.junit.jupiter.api.jar`
 - `lib/org.junit.platform.commons.jar`
 - `lib/org.opentest4j.jar`
 - `lib/runner`
 - `lib/runner/junit-platform-console-standalone-1.8.2.jar`
