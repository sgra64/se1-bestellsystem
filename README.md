# C2: *Customer.java*

Steps:

1. [Branch Assembly](#1-branch-assembly)

1. [Implement: *Customer.java*](#2-implement-customerjava)

1. [Run Tests for Class: *Customer.java*](#3-run-tests-for-class-customerjava)

1. [Supplement *Javadoc*](#4-supplement-javadoc)

1. [Push Branch to your Remote Repository](#3-push-branch-to-your-remote-repository)



&nbsp;

## 1. Branch Assembly

Tag the latest commit on branch `main` with `"base"`.

Create a new branch: `c12-customer` to implement the class: *Customer.java*.

Set a new remote with name: `se1-remote` pointing to the remote repository
from where the branch will be pulled:

```sh
git remote add se1-remote https://github.com/sgra64/se1-bestellsystem.git

git remote -v                   # show all remotes
```

Output will show two remotes: *"origin"* pointing to your remote repository
and the new remote: *"se1-remote"* :

```
origin  git@gitlab.bht-berlin.de:<your-name>/se1-bestellsystem.git (fetch)
origin  git@gitlab.bht-berlin.de:<your-name>/se1-bestellsystem.git (push)
se1-remote      https://github.com/sgra64/se1-bestellsystem.git (fetch)
se1-remote      https://github.com/sgra64/se1-bestellsystem.git (push)
```

Merge the remote branch `se1-remote/c12-customer` into the new local
branch `c12-customer`:

```sh
# pull the remote branch (pull: fetch + merge), --strategy-option theirs
git pull se1-remote c12-customer \
    --squash --allow-unrelated-histories
```

A new class [*Application_C12.java*](src/application/Application_C12.java)
has been added to package `application`, which creates and prints some
*Customer* objects:

```java
/**
 * Run application code on an instance rather than in {@code static main();}
 */
@Override
public void run(String[] args) {
    // 
    final Customer eric = new Customer("Eric Meyer")
        .setId(892474L)     // set id, first time
        .setId(947L)        // ignored, since id can only be set once
        .addContact("eric98@yahoo.com")
        .addContact("eric98@yahoo.com") // ignore duplicate contact
        .addContact("(030) 3945-642298");

    final Customer anne = new Customer("Bayer, Anne")
        .setId(643270L)
        .addContact("anne24@yahoo.de")
        .addContact("(030) 3481-23352")
        .addContact("fax: (030)23451356");

    final Customer tim = new Customer("Tim Schulz-Mueller")
        .setId(286516L)
        .addContact("tim2346@gmx.de");

    final Customer nadine = new Customer("Nadine-Ulla Blumenfeld")
        .setId(412396L)
        .addContact("+49 152-92454");

    final Customer khaled = new Customer()
        .setName("Khaled Saad Mohamed Abdelalim")
        .setId(456454L)
        .addContact("+49 1524-12948210");

    final TableFormatter tf = new TableFormatter("|%-6s", "| %-32s", "| %-32s |")
        .line()
        .row("ID", "NAME", "CONTACTS")  // table header
        .line();

    final List<Customer> customers = List.of(eric, anne, tim, nadine, khaled);
}
```

Compile and run the code:

```sh
mk compile run                      # compile and run the code
```

Output shows an empty table since class *Customer* is still incomplete:

```
Hello, se1.bestellsystem (Application_C12, modular)
+------+---------------------------------+----------------------------------+
|ID    | NAME                            | CONTACTS                         |
+------+---------------------------------+----------------------------------+
|0     | ,                               |                                  |
|0     | ,                               |                                  |
|0     | ,                               |                                  |
|0     | ,                               |                                  |
|0     | ,                               |                                  |
+------+---------------------------------+----------------------------------+
```



&nbsp;

## 2. Implement: *Customer.java*

Implement the class in a new package: `datamodel` according to the
*UML Class Diagram*:

<img src="https://raw.githubusercontent.com/sgra64/se1-bestellsystem/refs/heads/markup/main/Concept-diagram-customer.png" alt="drawing" width="600"/>


After implementation, re-compile and run the code. The table now shows the
created *Customer* objects:

```sh
mk compile run                      # compile and run the code
```
```
Hello, se1.bestellsystem (Application_C12, modular)
+------+---------------------------------+----------------------------------+
|ID    | NAME                            | CONTACTS                         |
+------+---------------------------------+----------------------------------+
|892474| Meyer, Eric                     | eric98@yahoo.com, (+1 contacts)  |
|643270| Bayer, Anne                     | anne24@yahoo.de, (+2 contacts)   |
|286516| Schulz-Mueller, Tim             | tim2346@gmx.de                   |
|412396| Blumenfeld, Nadine-Ulla         | +49 152-92454                    |
|456454| Abdelalim, Khaled Saad Mohamed  | +49 1524-12948210                |
+------+---------------------------------+----------------------------------+
```



&nbsp;

## 3. Run Tests for Class: *Customer.java*

Run the JUnit-tests for class *Customer.java*.

```sh
mk compile-tests run-tests          # run JUnit-tests for class Customer.java
```
```
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
│  ├─ Customer_200_SetId_Tests ✔
│  │  ├─ test200_setIdRegularValue() ✔
│  │  ├─ test201_setIdMinValue() ✔
│  │  ├─ test202_setIdMaxValue() ✔
│  │  ├─ test210_setIdWithIllegalArguments() ✔
│  │  ├─ test211_setIdWithIllegalArguments() ✔
│  │  └─ test220_setIdOnce() ✔
│  ├─ Application_0_always_pass_Tests ✔
│  │  ├─ test_001_always_pass() ✔
│  │  └─ test_002_always_pass() ✔
│  ├─ Customer_300_SetName_Tests ✔
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
│  ├─ Customer_100_Constructor_Tests ✔
│  │  ├─ test100_Constructor() ✔
│  │  ├─ test101_ConstructorWithRegularNameArgument() ✔
│  │  ├─ test102_ConstructorWithEmptyNameArgument() ✔
│  │  ├─ test103_ConstructorWithNullArgument() ✔
│  │  └─ test104_ChainableSetters() ✔
│  └─ Customer_500_SetNameExtended_Tests ✔
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
├─ JUnit Vintage ✔
└─ JUnit Platform Suite ✔

Test run finished after 278 ms
[         9 containers found      ]
[         0 containers skipped    ]
[         9 containers started    ]
[         0 containers aborted    ]
[         9 containers successful ]
[         0 containers failed     ]
[        55 tests found           ]
[         0 tests skipped         ]
[        55 tests started         ]
[         0 tests aborted         ]
[        55 tests successful      ]
[         0 tests failed          ]
```



&nbsp;

## 4. Supplement *Javadoc*

Refer to the 
[*Javadoc for Class Customer*](https://sgra64.github.io/se1.bestellsystem/D12-Datamodel/javadoc/se1.bestellsystem/datamodel/Customer.html) and supplement Javadoc to class *Customer.java*.

Change the Author to yourself in: `application/package-info.java`.

Generate *Javadoc* and compare the results with the link above:

```sh
mk javadoc                          # create javadoc in folder 'docs'

chrome docs/index.html              # open 'index.html' in a browser
```



&nbsp;

## 5. Push Branch to your Remote Repository

When tests are passing and Javadoc has been sucessfully created:

1. commit changes on your local branch `c12-customer`.

1. Push the branch to your remote repository.

1. Merge branch `c12-customer` to your local `main` branch.

1. Push branch `main` also to your remote repository.
