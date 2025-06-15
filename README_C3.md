# C3: *JUnit*-Tests for Class: *Customer*

This assignment demonstrates writing *JUnit* tests for the class *Customer*.

The *Test Plan* for class *Customer* is structured in topics (methods):

1. *Constructor* - Tests (100) in file:
    *Customer_100_Constructor_Tests.java* in package `datamodel` in `tests`.

1. *Id* - Tests (200) in file:
    *Customer_200_Id_Tests.java* in same package.

1. *Name* - Tests (300) in file:
    [*Customer_300_Name_Tests.java*](tests/datamodel/Customer_300_Name_Tests.java)
    in the same package.

1. *Contacts* - Tests (400) in file:
    [*Customer_400_Contacts_Tests.java*](tests/datamodel/Customer_400_Contacts_Tests.java)
    in the same package.

1. Extended *XXL Name* - Tests (500) in file:
    [*Customer_500_NameXXL_Tests.java*](tests/datamodel/Customer_500_NameXXL_Tests.java)
    in the same package.

Steps:

1. [Create *Constructor* Tests (100)](#1-create-constructor-tests-100)

1. [Create *Id* Tests (200)](#2-create-id-tests-200)

1. [Run *Name* Tests (300)](#3-run-name-tests-300)

1. [Run *Contacts* Tests (400)](#4-run-contacts-tests-400)

1. [Run Extended *XXL Name* Tests (500)](#5-run-extended-xxl-name-tests-500)

1. [Run all *Customer* Tests](#6-run-all-customer-tests)

1. [Commit and Push Changes](#7-commit-and-push-changes)



&nbsp;

## 1. Create *Constructor* Tests (100)

*Constructor* tests (100) test the constructor(s) of a class.
Class *Customer* has two constructors:

```java
/**
 * Default constructor.
 */
public Customer() { }

/**
 * Constructor with single-String name argument, for example "Eric Meyer"
 * (see method {@link splitName} for details).
 * @param name single-String Customer name
 * @throws IllegalArgumentException if name argument is null or empty
 */
public Customer(String name) {
    setName(name);  // throws IllegalArgumentException if is null or empty
}
```

Create a new test class: *Customer_100_Constructor_Tests.java* in `tests`
in a new package `datamodel` with test methods for the default constructor:

```java
/**
 * Tests for {@link Customer} class: [100..199] with tested Constructors:
 * <pre>
 * - Customer()             // default constructor
 * - Customer(String name)  // constructor with name argument
 * </pre>
 * @author sgra64
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Customer_100_Constructor_Tests {

    /*
     * Regular test case 100: Default Constructor.
     */
    @Test @Order(100)
    void test100_DefaultConstructor() {
        final Customer c1 = new Customer();     // call default Constructor
        assertEquals(null, c1.getId());         // returns null for unassigned id
        assertEquals("", c1.getLastName());     // lastName: ""
        assertEquals("", c1.getFirstName());    // firstName: ""
        assertEquals(0, c1.contactsCount());    // 0 contacts
    }

    /*
     * Regular test case 101: Default Constructor test methods
     * are chainable.
     */
    @Test @Order(101)
    void test101_DefaultConstructorChainableSetters() {
        final Customer c1 = new Customer();
        // test self-reference is returned for setter methods
        assertSame(c1, c1.setId(0L));
        assertSame(c1, c1.setName("Eric Meyer"));
        assertSame(c1, c1.setName("Eric","Meyer"));
        assertSame(c1, c1.addContact("eric@gmail.com"));
    }

    /*
     * Regular test case 102: Default Constructor with setId(id) only
     * allowed to set id once.
     */
    @Test @Order(102)
    void test102_DefaultConstructorSetIdOnlyOnce() {
        final Customer c1 = new Customer();
        assertEquals(null, c1.getId());     // id is null (unassigned)
        c1.setId(648L);                     // set id for the first time
        assertEquals(648L, c1.getId());     // id is 648
        c1.setId(912L);                     // set id for the second time
        assertEquals(648L, c1.getId());     // id is still 648
    }
}
```

Next, create test methods for the `Customer(String name)` constructor
constructing objects from a single-String *name* argument.

Consider **regular test cases** with *"regular"* single-String *name* arguments:

- test case 110: `Customer c1 = new Customer("Eric Meyer");`

- test case 111: `Customer c1 = new Customer("Meyer, Eric");`
    - the exptected *firstName* is: `"Eric"` and *lastName*: `"Meyer"`.

- test case 112: `Customer c1 = new Customer("Meyer");`
    - the exptected *firstName* is: `""` (empty String) and *lastName*: `"Meyer"`.

Corresponding test-methods are:

```java
    /*
     * Regular test case 110: Constructor with regular first name last name.
     * new Customer("Eric Meyer"),  expected: firstName: "Eric", lastName: "Meyer"
     */
    @Test @Order(110)
    void test110_ConstructorWithRegularFirstLastName() {
        ...
    }

    /*
     * Regular test case 111: Constructor with regular last name comma first name.
     * new Customer("Meyer, Eric"),  expected: firstName: "Eric", lastName: "Meyer"
     */
    @Test @Order(111)
    void test111_ConstructorWithRegularLastCommaFirstName() {
        ...
    }

    /*
     * Regular test case 112: Constructor with regular single last name.
     * new Customer("Meyer"),  expected: firstName: "" (empty), lastName: "Meyer"
     */
    @Test @Order(112)
    void test112_ConstructorWithRegularLastNameOnly() {
        ...
    }
```

Next, consider **corner test cases** constructing *Customer* objects from
short(est) or long(est) allowed name arguments:

- test case 120: `Customer c1 = new Customer("E M");`
    - the exptected *firstName* is: `"E"` (empty String) and *lastName*: `"M"`.

- test case 121: `Customer c1 = new Customer("Nadine Ulla Maxine Adriane Blumenfeld");`
    - the exptected *firstName* is: `"Nadine Ulla Maxine Adriane"` and
        *lastName*: `"Blumenfeld"`.

- test case 122: `Customer c1 = new Customer("Nadine Ulla Maxine Adriane von-Blumenfeld-Bozo");`
    - the exptected *firstName* is: `"Nadine Ulla Maxine Adriane"` and
        *lastName*: `"von-Blumenfeld-Bozo"`.

- test case 123: `Customer c1 = new Customer("von-Blumenfeld-Bozo, Nadine Ulla Maxine Adriane");`
    - the exptected *firstName* is: `"Nadine Ulla Maxine Adriane"` and
        *lastName*: `"von-Blumenfeld-Bozo"`.

with corresponding test-methods:

```java
    /*
     * Corner test case 120: Constructor with shortest allowed first and last name.
     * test three cases:
     *  - new Customer("E M"),  expected: firstName: "E", lastName: "M"
     *  - new Customer("M, E"), expected: firstName: "E", lastName: "M"
     *  - new Customer("M"),    expected: firstName: "", lastName: "M"
     */
    @Test @Order(120)
    void test120_ConstructorWithCornerShortestPossibleFirstAndLastName() {
        ...
    }

    /*
     * Corner test case 121: Constructor with long first and last name.
     * new Customer("Nadine Ulla Maxine Adriane Blumenfeld")
     *  - expected: firstName: "Nadine Ulla Maxine Adriane", lastName: "Blumenfeld"
     */
    @Test @Order(121)
    void test121_ConstructorWithLongFirstAndLastName() {
        ...
    }

    /*
     * Corner test case 122: Constructor with long first and multi-part last name.
     * new Customer("Nadine Ulla Maxine Adriane von-Blumenfeld-Bozo")
     *  - expected: firstName: "Nadine Ulla Maxine Adriane", lastName: "von-Blumenfeld-Bozo"
     */
    @Test @Order(122)
    void test122_ConstructorWithLongFirstAndMultipartLastName() {
        ...
    }

    /*
     * Corner test case 123: Constructor with long first and multi-part last name.
     * new Customer("von-Blumenfeld-Bozo, Nadine Ulla Maxine Adriane")
     *  - expected: firstName: "Nadine Ulla Maxine Adriane", lastName: "von-Blumenfeld-Bozo"
     */
    @Test @Order(123)
    void test123_ConstructorWithLongMultipartLastNameAndFirstName() {
        ...
    }
```

Create test methods for **exception test cases** attempting to construct
objects from empty or `null` name arguments:

- test case 130: `Customer c1 = new Customer("");`
    - the exptected outcome is that an `IllegalArgumentException` is thrown
        by the constructor with message: `"name empty"`.


- test case 131: `Customer c1 = new Customer(null);`
    - the exptected outcome is that an `IllegalArgumentException` is thrown
        by the constructor with message: `"name null"`.

```java
    /*
     * Exception test case 130: Constructor with empty name: "".
     * The exptected outcome is that an {@link IllegalArgumentException}
     * is thrown by the constructor with message: "name empty".
     */
    @Test @Order(130)
    void test130_ConstructorWithEmptyName() {
        ...
    }

    /*
     * Exception test case 131: Constructor with null argument.
     * The exptected outcome is that an {@link IllegalArgumentException}
     * is thrown by the constructor with message: "name null".
     */
    @Test @Order(131)
    void test131_ConstructorWithNullArgument() {
        ...
    }
```

Run *100'er Constructor* tests selectively in your IDE and in the terminal:

```sh
java -cp "$JUNIT_CLASSPATH" org.junit.platform.console.ConsoleLauncher \
    $JUNIT_OPTIONS -c datamodel.Customer_100_Constructor_Tests
```

Output shows *Constructor* tests passing (or correct your code in *Customer.java*):

```
╷
├─ JUnit Jupiter ✔
│  └─ Customer_100_Constructor_Tests ✔
│     ├─ test100_DefaultConstructor() ✔
│     ├─ test101_DefaultConstructorChainableSetters() ✔
│     ├─ test102_DefaultConstructorSetIdOnlyOnce() ✔
│     ├─ test110_ConstructorWithRegularFirstLastName() ✔
│     ├─ test111_ConstructorWithRegularLastCommaFirstName() ✔
│     ├─ test112_ConstructorWithRegularLastNameOnly() ✔
│     ├─ test120_ConstructorWithCornerShortestPossibleFirstAndLastName() ✔
│     ├─ test121_ConstructorWithLongFirstAndLastName() ✔
│     ├─ test122_ConstructorWithLongFirstAndMultipartLastName() ✔
│     ├─ test123_ConstructorWithLongMultipartLastNameAndFirstName() ✔
│     ├─ test130_ConstructorWithEmptyName() ✔
│     └─ test131_ConstructorWithNullArgument() ✔
├─ JUnit Vintage ✔
└─ JUnit Platform Suite ✔

Test run finished after 182 ms
[        12 tests successful      ]     <-- 12 tests are passing
[         0 tests failed          ]     <--  0 tests are failing
```



&nbsp;

## 2. Create *Id* Tests (200)

*Id* tests (200) test methods of class *Customer*:
- `getId()`
- `setId(long id)`

Develop a test plan and design test-methods (like above) covering the
following cases:

1. **Regular cases**: regulare cases stage regular use cases:
    - 200: test the value returned for `getId()` after object construction is: `null`.
    - 201: test value returned for `getId()` after first `setId(x)` is the set value: `x`.
    - 202: test value returned for `getId()` after second invocation of `setId(y)`
        is still the first value: `x`.

1. **Corner cases**:
    - 210: test `setId(x)` with minimum allowed value `x` and value `x+1`.
    - 211: test `setId(x)` with maximum allowed value `x` and value `x-1`.
    - 212: test `setId(0)` with value zero.

1. **Exception cases**:

    - 220: test `setId(-1)` illegal (exception) case that expects the method
        to throw an `IllegalArgumentException` with message: `"invalid id (negative)"`.
        Test both, that the exception is thrown and the exception message.

    - 221: test `setId(Long.MIN_VALUE)` illegal (exception) case that expects the method
        to throw an `IllegalArgumentException` with message: `"invalid id (negative)"`.
        Test both, that the exception is thrown and the exception message.

Create a new test class in package `datamodel` in `tests` with a
test method for the default constructor:

```java
/**
 * Tests for {@link Customer} class: [200..299] Id-tests with tested
 * methods:
 * <pre>
 * - getId()
 * - setId(long id)
 * </pre>
 * @author sgra64
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Customer_200_Id_Tests {
    ...
}
```

Run *200'er Id* tests selectively in your IDE and in the terminal:

```sh
java -cp "$JUNIT_CLASSPATH" org.junit.platform.console.ConsoleLauncher \
    $JUNIT_OPTIONS -c datamodel.Customer_200_Id_Tests
```

Output shows *Constructor* tests passing (or correct your code in *Customer.java*):

```
╷
├─ JUnit Jupiter ✔
│  └─ Customer_200_Id_Tests ✔
│     ├─ test200_IdNullAfterCconstruction() ✔
│     ├─ test201_setIdMinValue() ✔
│     ├─ test201_setIdRegularValue() ✔
│     ├─ test202_setIdRegularValueTwice() ✔
│     ├─ test210_setIdMinValue() ✔
│     ├─ test211_setIdMaxValue() ✔
│     ├─ test212_setIdZeroValue() ✔
│     └─ test220_setIdWithNegativeArguments() ✔
├─ JUnit Vintage ✔
└─ JUnit Platform Suite ✔

Test run finished after 242 ms
[         8 tests successful      ]     <-- 8 tests are passing
[         0 tests failed          ]     <-- 0 tests are failing
```



&nbsp;

## 3. Run *Name* Tests (300)

Tests [*Customer_300_Name_Tests.java*](tests/datamodel/Customer_300_Name_Tests.java)
test *Name* (300) methods of class *Customer*:

- `getFirstName()`

- `getLastName()`

- `setName(String name)`

- `setName(String first, String last)`

Fix the tested code (your implementation of class *Customer*) if tests
do not pass.

Run *300'er Name* tests selectively in your IDE and in the terminal:

```sh
java -cp "$JUNIT_CLASSPATH" org.junit.platform.console.ConsoleLauncher \
    $JUNIT_OPTIONS -c datamodel.Customer_300_Name_Tests
```

Output shows *Constructor* tests passing (or correct your code in *Customer.java*):

```
├─ JUnit Jupiter ✔
│  └─ Customer_300_Name_Tests ✔
│     ├─ test300_setNameFirstAndLastName() ✔
│     ├─ test301_setNameFirstAndLastName() ✔
│     ├─ test302_setNameFirstAndLastName() ✔
│     ├─ test303_setNameFirstAndLastName() ✔
│     ├─ test310_setNameSingleName() ✔
│     ├─ test311_setNameSingleName() ✔
│     ├─ test312_setNameSingleName() ✔
│     ├─ test313_setNameSingleName() ✔
│     ├─ test320_setNameDoubleLastName() ✔
│     ├─ test321_setNameDoubleLastName() ✔
│     ├─ test322_setNameDoubleLastName() ✔
│     ├─ test330_setNameSingleArgumentConstructor() ✔
│     ├─ test331_setNameSingleArgumentConstructor() ✔
│     ├─ test332_setNameSingleArgumentConstructor() ✔
│     ├─ test333_setNameSingleArgumentConstructor() ✔
│     ├─ test334_setNameSingleArgumentConstructor() ✔
│     └─ test335_setNameSingleArgumentConstructor() ✔
├─ JUnit Vintage ✔
└─ JUnit Platform Suite ✔

Test run finished after 242 ms
[        17 tests successful      ]     <-- 17 tests are passing
[         0 tests failed          ]     <--  0 tests are failing
```



&nbsp;

## 4. Run *Contacts* Tests (400)

Tests [*Customer_400_Contacts_Tests.java*](tests/datamodel/Customer_400_Contacts_Tests.java)
test *Contacts* (400) Tests test methods of class *Customer*:

- `contactsCount()`

- `getContacts()`

- `addContact(String contact)`

- `deleteContact(int i)`

- `deleteAllContacts()`

Fix the tested code (your implementation of class *Customer*) if tests
do not pass.


Run *400'er Contacts* tests selectively in your IDE and in the terminal:

```sh
java -cp "$JUNIT_CLASSPATH" org.junit.platform.console.ConsoleLauncher \
    $JUNIT_OPTIONS -c datamodel.Customer_400_Contacts_Tests
```

Output shows *Constructor* tests passing (or correct your code in *Customer.java*):

```
├─ JUnit Jupiter ✔
│  └─ Customer_400_Contacts_Tests ✔
│     ├─ test400_addContactsRegularCases() ✔
│     ├─ test401_addContactsCornerCases() ✔
│     ├─ test402_addContactsCornerCases() ✔
│     ├─ test403_addContactsMinimumLength() ✔
│     ├─ test404_addContactsIgnoreDuplicates() ✔
│     ├─ test410_deleteContactRegularCases() ✔
│     ├─ test411_deleteContactOutOfBoundsCases() ✔
│     └─ test412_deleteAllContacts() ✔
├─ JUnit Vintage ✔
└─ JUnit Platform Suite ✔

Test run finished after 242 ms
[         8 tests successful      ]     <-- 8 tests are passing
[         0 tests failed          ]     <-- 0 tests are failing
```



&nbsp;

## 5. Run Extended *XXL Name* Tests (500)

Tests [*Customer_500_NameXXL_Tests.java*](tests/datamodel/Customer_500_NameXXL_Tests.java)
test name-related methods of class *Customer* with extreme names.

Fix the tested code (your implementation of class *Customer*) if tests
do not pass.

Run *500'er Extended Name* tests selectively in your IDE and in the terminal:

```sh
java -cp "$JUNIT_CLASSPATH" org.junit.platform.console.ConsoleLauncher \
    $JUNIT_OPTIONS -c datamodel.Customer_500_NameXXL_Tests
```

Output shows *Constructor* tests passing (or correct your code in *Customer.java*):

```
├─ JUnit Jupiter ✔
│  └─ Customer_500_NameXXL_Tests ✔
│     ├─ test500_setNameMultipartLastName() ✔
│     ├─ test501_setNameMultipartLastName() ✔
│     ├─ test502_setNameMultipartLastName() ✔
│     ├─ test510_setNameDoubleFirstName() ✔
│     ├─ test511_setNameDoubleFirstName() ✔
│     ├─ test512_setNameDoubleFirstName() ✔
│     ├─ test520_setNameMultipartFirstNames() ✔
│     ├─ test521_setNameMultipartFirstNames() ✔
│     ├─ test522_setNameMultipartFirstNames() ✔
│     ├─ test530_setNameMultipartFirstNames() ✔
│     ├─ test531_setNameMultipartNames() ✔
│     ├─ test544_setNameMultiDashMultipartFirstNames() ✔
│     ├─ test550_setNameMultiDashMultipartFirstNames() ✔
│     ├─ test550_setNameExtremeLongNames() ✔
│     ├─ test551_setNameMultiDashMultipartFirstNames() ✔
│     ├─ test552_setNameMultipartNames() ✔
│     └─ test553_setNameMultiDashMultipartFirstNames() ✔

Test run finished after 242 ms
[        17 tests successful      ]     <-- 17 tests are passing
[         0 tests failed          ]     <--  0 tests are failing
```



&nbsp;

## 6. Run all *Customer* Tests

Run all Customer tests in the IDE and in the terminal:

```sh
mk clean compile compile-tests          # compile tested code and test code

mk run-tests                            # run all tests

# alternative method to run tests by discovery (used by 'mk run-tests')
java -cp "$JUNIT_CLASSPATH" org.junit.platform.console.ConsoleLauncher \
    $JUNIT_OPTIONS --scan-class-path

# alternative method to run tests by selection
java -cp "$JUNIT_CLASSPATH" org.junit.platform.console.ConsoleLauncher \
    $JUNIT_OPTIONS \
    -c application.Application_0_always_pass_Tests \
    -c datamodel.Customer_100_Constructor_Tests \
    -c datamodel.Customer_200_Id_Tests \
    -c datamodel.Customer_300_Name_Tests \
    -c datamodel.Customer_400_Contacts_Tests \
    -c datamodel.Customer_500_NameXXL_Tests
```

Output shows all tests for class *Customer* passing :

```
╷
├─ JUnit Jupiter ✔
│  ├─ Customer_400_Contacts_Tests ✔
│  │  ├─ test400_addContactsRegularCases() ✔
│  │  ├─ test401_addContactsCornerCases() ✔
│  │  ├─ test402_addContactsCornerCases() ✔
│  │  ├─ test403_addContactsMinimumLength() ✔
│  │  ├─ test404_addContactsIgnoreDuplicates() ✔
│  │  ├─ test410_deleteContactRegularCases() ✔
│  │  ├─ test411_deleteContactOutOfBoundsCases() ✔
│  │  └─ test412_deleteAllContacts() ✔
│  ├─ Application_0_always_pass_Tests ✔
│  │  ├─ test_001_always_pass() ✔
│  │  └─ test_002_always_pass() ✔
│  ├─ Customer_200_Id_Tests ✔
│  │  ├─ test200_IdNullAfterCconstruction() ✔
│  │  ├─ test201_setIdMinValue() ✔
│  │  ├─ test201_setIdRegularValue() ✔
│  │  ├─ test202_setIdRegularValueTwice() ✔
│  │  ├─ test210_setIdMinValue() ✔
│  │  ├─ test211_setIdMaxValue() ✔
│  │  ├─ test212_setIdZeroValue() ✔
│  │  └─ test220_setIdWithNegativeArguments() ✔
│  ├─ Customer_500_NameXXL_Tests ✔
│  │  ├─ test500_setNameMultipartLastName() ✔
│  │  ├─ test501_setNameMultipartLastName() ✔
│  │  ├─ test502_setNameMultipartLastName() ✔
│  │  ├─ test510_setNameDoubleFirstName() ✔
│  │  ├─ test511_setNameDoubleFirstName() ✔
│  │  ├─ test512_setNameDoubleFirstName() ✔
│  │  ├─ test520_setNameMultipartFirstNames() ✔
│  │  ├─ test521_setNameMultipartFirstNames() ✔
│  │  ├─ test522_setNameMultipartFirstNames() ✔
│  │  ├─ test530_setNameMultipartFirstNames() ✔
│  │  ├─ test531_setNameMultipartNames() ✔
│  │  ├─ test544_setNameMultiDashMultipartFirstNames() ✔
│  │  ├─ test550_setNameMultiDashMultipartFirstNames() ✔
│  │  ├─ test550_setNameExtremeLongNames() ✔
│  │  ├─ test551_setNameMultiDashMultipartFirstNames() ✔
│  │  ├─ test552_setNameMultipartNames() ✔
│  │  └─ test553_setNameMultiDashMultipartFirstNames() ✔
│  ├─ Customer_300_Name_Tests ✔
│  │  ├─ test300_setNameFirstAndLastName() ✔
│  │  ├─ test301_setNameFirstAndLastName() ✔
│  │  ├─ test302_setNameFirstAndLastName() ✔
│  │  ├─ test303_setNameFirstAndLastName() ✔
│  │  ├─ test310_setNameSingleName() ✔
│  │  ├─ test311_setNameSingleName() ✔
│  │  ├─ test312_setNameSingleName() ✔
│  │  ├─ test313_setNameSingleName() ✔
│  │  ├─ test320_setNameDoubleLastName() ✔
│  │  ├─ test321_setNameDoubleLastName() ✔
│  │  ├─ test322_setNameDoubleLastName() ✔
│  │  ├─ test330_setNameSingleArgumentConstructor() ✔
│  │  ├─ test331_setNameSingleArgumentConstructor() ✔
│  │  ├─ test332_setNameSingleArgumentConstructor() ✔
│  │  ├─ test333_setNameSingleArgumentConstructor() ✔
│  │  ├─ test334_setNameSingleArgumentConstructor() ✔
│  │  └─ test335_setNameSingleArgumentConstructor() ✔
│  └─ Customer_100_Constructor_Tests ✔
│     ├─ test100_DefaultConstructor() ✔
│     ├─ test101_DefaultConstructorChainableSetters() ✔
│     ├─ test102_DefaultConstructorSetIdOnlyOnce() ✔
│     ├─ test110_ConstructorWithRegularFirstLastName() ✔
│     ├─ test111_ConstructorWithRegularLastCommaFirstName() ✔
│     ├─ test112_ConstructorWithRegularLastNameOnly() ✔
│     ├─ test120_ConstructorWithCornerShortestPossibleFirstAndLastName() ✔
│     ├─ test121_ConstructorWithLongFirstAndLastName() ✔
│     ├─ test122_ConstructorWithLongFirstAndMultipartLastName() ✔
│     ├─ test123_ConstructorWithLongMultipartLastNameAndFirstName() ✔
│     ├─ test130_ConstructorWithEmptyName() ✔
│     └─ test131_ConstructorWithNullArgument() ✔
├─ JUnit Vintage ✔
└─ JUnit Platform Suite ✔

Test run finished after 277 ms
[         9 containers found      ]
[         0 containers skipped    ]
[         9 containers started    ]
[         0 containers aborted    ]
[         9 containers successful ]
[         0 containers failed     ]
[        64 tests found           ]
[         0 tests skipped         ]
[        64 tests started         ]
[         0 tests aborted         ]
[        64 tests successful      ]     <-- 64 tests are passing
[         0 tests failed          ]     <--  0 tests are failing
```



&nbsp;

## 7. Commit and Push Changes

When tests are passing, commit changes and push to your remote repository:

```sh
git status                      # show changed files
git add .                       # stage changes for commit
git commit -m "c3: JUnit-tests 100, 200 for Customer.java"
git log --oneline               # show commit log
```
```
340e59f (HEAD -> main) c3: JUnit-tests 100, 200 for Customer.java   <-- latest commit
340e59f c12: Customer.java
b1e8439 pull se1-remote c12-customer
aad5b4a add .gitmodules file and modules .env, .vscode, libs
44cdf9f add README.md
c43f7e1 add src, tests, resources
638c570 add .gitmodules, .gitmodules.sh
d8f0873 add .gitignore
4375dd3 (tag: root) root commit (empty)
```

Push commits to your remote repository:

```sh
git push                        # push the commit to your upstream remote repository
```
