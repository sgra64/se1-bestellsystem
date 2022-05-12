package datamodel;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	datamodel.Customer_100_ConstructorTest.class,
	datamodel.Customer_200_SetIdTest.class,
	datamodel.Customer_300_SetNameTest.class,
	datamodel.Customer_400_ContactsTest.class,
	datamodel.Customer_500_SetNameExtendedTest.class
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("CustomerTest_ TESTS")
@Disabled
class CustomerTest_ {
	//
	private final Customer c1 = new Customer();

//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//		System.out.println("@BeforeAll");
//	}
//
//	@BeforeEach
//	void setUp() throws Exception {
//		System.out.println("@BeforeEach");
//	}
//	System.out.println( "c1.getName(): " + c1.getName());

	int level = 0;
	boolean level1() { return level >= 1; }	// simple base tests
	boolean level2() { return level >= 2; }	// simple tests
	boolean level3() { return level >= 3; }	// some logic is involved
	boolean level4() { return level >= 4; }	// name resolution
	boolean level5() { return level >= 5; }	// exceptions

	@Test @Order(100) @EnabledIf("level1")
	void test100_Constructor_L1() {
		assertEquals(c1.getId(), null);			// null because id is still unassigned
		assertEquals(c1.getLastName(), "");
		assertEquals(c1.getFirstName(), "");
		assertEquals(c1.contactsCount(), 0);
		String[] contacts = c1.getContacts();	// returns empty String[] (not null)
		assertArrayEquals(contacts, new String[] {});
//		IntStream.of(-100, -2, -1, 0, 1, 2, 100).boxed()
//			.forEach(i -> assertEquals(c1.getContacts()[i], ""));
	}


	/*
	 * Test case: Constructor with no name argument.
	 */
	@Test @Order(100)
	void test100_Constructor() {
		final Customer c1 = new Customer();		// Constructor with no name argument
		assertEquals(c1.getId(), null);			// returns null for unassigned id
		assertEquals(c1.getLastName(), "");		// lastName: ""
		assertEquals(c1.getFirstName(), "");	// firstName: ""
		assertEquals(c1.contactsCount(), 0);	// 0 contacts
		String[] contacts = c1.getContacts();	// returns empty String[] (not null)
		assertArrayEquals(contacts, new String[] {});
	}


	/*
	 * Test case: Constructor with regular name argument.
	 */
	@Test @Order(101)
	void test101_ConstructorWithRegularNameArgument() {
		final Customer c1 = new Customer("Meyer");	// Constructor with no single name argument
		assertEquals(c1.getId(), null);				// returns null for unassigned id
		assertEquals(c1.getLastName(), "Meyer");	// lastName: "Meyer"
		assertEquals(c1.getFirstName(), "");		// firstName: ""
		assertEquals(c1.contactsCount(), 0);		// 0 contacts
		assertArrayEquals(c1.getContacts(), new String[] {});	// returns String[] (not null)
	}


	/*
	 * Test case: Constructor with empty name argument: new Customer("")
	 * throws IllegalArgumentException with message "name empty."
	 */
	@Test @Order(102)
	void test102_ConstructorWithEmptyNameArgument() {
		IllegalArgumentException thrown =
			assertThrows(
				IllegalArgumentException.class, () -> {
					new Customer("");
		});
		assertEquals("name empty.", thrown.getMessage());
	}


	/*
	 * Test case: Constructor with empty name argument: new Customer(null)
	 * throws IllegalArgumentException with message "name null."
	 */
	@Test @Order(103)
	void test103_ConstructorWithNullArgument() {
		IllegalArgumentException thrown =
			assertThrows(
				IllegalArgumentException.class, () -> {
					new Customer(null);
		});
		assertEquals("name null.", thrown.getMessage());
	}


	/*
	 * Test case: Constructor with empty name argument: new Customer("")
	 * throws IllegalArgumentException with message "name empty."
	 */
	@Test @Order(104)
	void test104_ChainableSetters() {
		final Customer c1 = new Customer();
		assertSame(c1, c1.setId(0L));	// setter must return same object reference
		assertSame(c1, c1.setName(""));
		assertSame(c1, c1.setName("",""));
		assertSame(c1, c1.addContact("eric@gmail.com"));
	}


	/*
	 * Test cases 200: set id with regular value.
	 */
	@Test @Order(200)
	void test200_setIdRegularValue() {
		assertEquals(c1.getId(), null);	// returns null for unassigned id
		c1.setId(3L);					// assign id with long value
		assertEquals(c1.getId(), 3L);	// return assigned id
	}


	/*
	 * Test case: set id with smallest regular value.
	 */
	@Test @Order(201)
	void test201_setIdMinValue() {
		c1.setId(0L);
		assertEquals(c1.getId(), 0L);
	}


	/*
	 * Test case: set id with largest regular value.
	 */
	@Test @Order(202)
	void test202_setIdMaxValue() {
		c1.setId(Long.MAX_VALUE);
		assertEquals(c1.getId(), Long.MAX_VALUE);
	}


	/*
	 * Test cases 210: set id with illegal value ( < 0) throws IllegalArgument-
	 * Exception with message "invalid id (negative)." Id remains unassigned.
	 */
	@Test @Order(210)
	void test210_setIdWithIllegalArguments() {
		IllegalArgumentException thrown = assertThrows(
			IllegalArgumentException.class, () -> {
				c1.setId(-1L);		// setId(id) with illegal id
		});
		assertEquals("invalid id (negative).", thrown.getMessage());
		//
		assertEquals(c1.getId(), null);		// id remains unassigned
	}


	/*
	 * Test case: set id with multiple illegal values. Must throw IllegalArgument-
	 * Exception with message "invalid id (negative)." Id remains unassigned.
	 */
	@Test @Order(211)
	void test211_setIdWithIllegalArguments() {
		// multiple test values for illegal id
		List.of(-1L, -10L, -1000L, -1000000000000000000L, Long.MIN_VALUE).stream()
			.forEach(illegalId -> {
				IllegalArgumentException thrown = assertThrows(
					IllegalArgumentException.class, () -> {
						c1.setId(illegalId);		// setId(id) with illegal id
				});
				assertEquals("invalid id (negative).", thrown.getMessage());
				//
				assertEquals(c1.getId(), null);		// id remains unassigned
			});
	}


	/*
	 * Test case 220: set id only once.
	 */
	@Test @Order(220)
	void test220_setIdOnce() {
		c1.setId(3L);	// set id first time with regular value
		assertEquals(c1.getId(), 3L);
		c1.setId(6L);	// attempt to set id new regular value
		assertEquals(c1.getId(), 3L); // id remains unchanged
		c1.setId(3L);	// attempt to set initial  value
		assertEquals(c1.getId(), 3L);
	}


	/*
	 * Test cases 300: setName(firstName, lastName) with two arguments.
	 */
	@Test @Order(300)
	void test300_setNameFirstAndLastName() {
		c1.setName("Eric", "Meyer");
		assertEquals(c1.getFirstName(), "Eric");
		assertEquals(c1.getLastName(), "Meyer");
	}

	@Test @Order(301)
	void test301_setNameFirstAndLastName() {
		c1.setName("", "Meyer");	// lastName only
		assertEquals(c1.getFirstName(), "");
		assertEquals(c1.getLastName(), "Meyer");
	}

	@Test @Order(302)
	void test302_setNameFirstAndLastName() {
		c1.setName("Eric", "");		// firstName only
		assertEquals(c1.getFirstName(), "Eric");
		assertEquals(c1.getLastName(), "");
	}

	@Test @Order(303)
	void test303_setNameFirstAndLastName() {
		c1.setName("", "");		// firstName only
		assertEquals(c1.getFirstName(), "");
		assertEquals(c1.getLastName(), "");
	}


	/*
	 * Test cases 310: setName(name) with single-string name argument.
	 */
	@Test @Order(310)
	void test310_setNameSingleName() {
		c1.setName("Eric Meyer");			// name style 1: "first lastName"
		assertEquals(c1.getFirstName(), "Eric");
		assertEquals(c1.getLastName(), "Meyer");
	}

	@Test @Order(311)
	void test311_setNameSingleName() {
		c1.setName("Meyer, Eric");			// name style 2: "lastName, firstName"
		assertEquals(c1.getFirstName(), "Eric");
		assertEquals(c1.getLastName(), "Meyer");
	}

	@Test @Order(312)
	void test312_setNameSingleName() {
		c1.setName("Meyer; Eric");			// name style 3 with semicolon
		assertEquals(c1.getFirstName(), "Eric");
		assertEquals(c1.getLastName(), "Meyer");
	}

	@Test @Order(313)
	void test313_setNameSingleName() {
		c1.setName("E. Meyer");				// name style 4: "F. lastName"
		assertEquals(c1.getFirstName(), "E.");
		assertEquals(c1.getLastName(), "Meyer");
	}


	/*
	 * Test cases 320: setName(name) with '-' connected double-part last names.
	 */
	@Test @Order(320)
	void test320_setNameDoubleLastName() {
		c1.setName("Tim Schulz-Mueller");	// name style 1 with double last name
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller");
	}

	@Test @Order(321)
	void test321_setNameDoubleLastName() {
		c1.setName("Schulz-Mueller, Tim");	// name style 2
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller");
	}

	@Test @Order(322)
	void test322_setNameDoubleLastName() {
		c1.setName("Schulz-Mueller; Tim");	// name style 3
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller");
	}


	/*
	 * Test cases 330: single-Argument name Constructor
	 */
	@Test @Order(330)
	void test330_setNameSingleArgumentConstructor() {
		final Customer c1 = new Customer("Eric Meyer");		// name style 1
		assertEquals(c1.getFirstName(), "Eric");
		assertEquals(c1.getLastName(), "Meyer");
	}

	@Test @Order(331)
	void test331_setNameSingleArgumentConstructor() {
		final Customer c1 = new Customer("Meyer, Eric");	// name style 3
		assertEquals(c1.getFirstName(), "Eric");
		assertEquals(c1.getLastName(), "Meyer");
	}

	@Test @Order(332)
	void test332_setNameSingleArgumentConstructor() {
		final Customer c1 = new Customer("Meyer; Eric");	// name style 3
		assertEquals(c1.getFirstName(), "Eric");
		assertEquals(c1.getLastName(), "Meyer");
	}

	@Test @Order(333)
	void test333_setNameSingleArgumentConstructor() {
		final Customer c1 = new Customer("Tim Schulz-Mueller");		// name style 1
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller");
	}

	@Test @Order(334)
	void test334_setNameSingleArgumentConstructor() {
		final Customer c1 = new Customer("Schulz-Mueller, Tim");	// name style 2
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller");
	}

	@Test @Order(335)
	void test335_setNameSingleArgumentConstructor() {
		final Customer c1 = new Customer("Schulz-Mueller; Tim");	// name style 3
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller");
	}


	/*
	 * Test cases 400: add contacts.
	 */
	@Test @Order(400)
	void test400_addContactsRegularCases() {
		assertEquals(c1.contactsCount(), 0);
		c1.addContact("eric@gmail.com");
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"
		});
		c1.addContact("(0152) 38230529");
		assertEquals(c1.contactsCount(), 2);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529"	// must maintain order
		});
		c1.addContact("(030) 3534346-6336");
		assertEquals(c1.contactsCount(), 3);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529", "(030) 3534346-6336"
		});
	}

	@Test @Order(401)
	void test401_addContactsCornerCases() {
		assertEquals(c1.contactsCount(), 0);
		c1.addContact(" eric@gmail.com  ");	// trim leading and trailing spaces
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"
		});
		c1.addContact("\t(0152) 38230529\t \n\t");	// trim more leading and
		assertEquals(c1.contactsCount(), 2);		// trailing white spaces
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529"
		});
	}

	@Test @Order(402)
	void test402_addContactsCornerCases() {
		assertEquals(c1.contactsCount(), 0);
		c1.addContact("\"eric@gmail.com\"");	// trim leading and trailing quotes
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"
		});
		c1.addContact("\" \"'\"(0152) 38230529';'\" ,\t\n\"");	// trim leading and trailing
		assertEquals(c1.contactsCount(), 2);	// special chars: quotes["'] and [;,.]
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529"
		});
	}

	@Test @Order(403)
	void test403_addContactsMinimumLength() {
		assertEquals(c1.contactsCount(), 0);
		c1.addContact("e@gm.c");	// minimum length for meaningful contact is 6 characters
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"e@gm.c"
		});
		//
		final String contact = "e@g.c";	// < 6 characters as contact is illegal
		IllegalArgumentException thrown =
			assertThrows(
				IllegalArgumentException.class, () -> {
					c1.addContact(contact);
		});
		assertEquals("contact less than 6 characters: \"" + contact +
				"\".", thrown.getMessage());
		//
		final String contact2 = "\"  e@g.c \t\"";	// input > 6, but not after trimming
		thrown = assertThrows(
			IllegalArgumentException.class, () -> {
				c1.addContact(contact2);
		});
		assertEquals("contact less than 6 characters: \"" + contact2 +
				"\".", thrown.getMessage());
	}

	@Test @Order(404)
	void test404_addContactsIgnoreDuplicates() {
		// duplicate entries for contacts are ignored
		assertEquals(c1.contactsCount(), 0);
		c1.addContact("eric@gmail.com");
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"
		});
		c1.addContact("eric@gmail.com");	// duplicate contact
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"				// ignored
		});
		c1.addContact("eric@gmail.com");	// duplicate contact, 2nd added
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"				// still ignored
		});
	}


	/*
	 * Test cases 410: delete contacts.
	 */
	@Test @Order(410)
	void test410_deleteContactRegularCases() {
		c1.addContact("eric@gmail.com")
			.addContact("(0152) 38230529")
			.addContact("(030) 3534346-6336");
		assertEquals(c1.contactsCount(), 3);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529", "(030) 3534346-6336"
		});
		c1.deleteContact(2);	// delete from upper bound
		assertEquals(c1.contactsCount(), 2);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529"
		});
		c1.deleteContact(0);	// delete from lower bound
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] { "(0152) 38230529" });
	}

	@Test @Order(411)
	void test411_deleteContactOutOfBoundsCases() {
		c1.addContact("eric@gmail.com")
			.addContact("(0152) 38230529")
			.addContact("(030) 3534346-6336");
		assertEquals(c1.contactsCount(), 3);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529", "(030) 3534346-6336"
		});
		c1.deleteContact(300);	// delete > upper bound, ignore
		c1.deleteContact(3);
		assertEquals(c1.contactsCount(), 3);
		c1.deleteContact(-1);	// delete < upper bound, ignore
		c1.deleteContact(-100);
		assertEquals(c1.contactsCount(), 3);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529", "(030) 3534346-6336"
		});
	}

	@Test @Order(412)
	void test412_deleteAllContacts() {
		c1.deleteAllContacts();	// nothing to delete
		assertEquals(c1.contactsCount(), 0);
		assertArrayEquals(c1.getContacts(), new String[] { });
		//
		c1.addContact("eric@gmail.com")
			.addContact("(0152) 38230529")
			.addContact("(030) 3534346-6336");
		//
		assertEquals(c1.contactsCount(), 3);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529", "(030) 3534346-6336"
		});
		//
		c1.deleteAllContacts();	// all contacts deleted
		assertEquals(c1.contactsCount(), 0);
		assertArrayEquals(c1.getContacts(), new String[] { });
		//
		c1.deleteAllContacts();	// repeat
		assertEquals(c1.contactsCount(), 0);
		assertArrayEquals(c1.getContacts(), new String[] { });
	}


	/*
	 * Test cases 500: setName(name) with '-' connected multi-part last names.
	 */
	@Test @Order(500)
	void test500_setNameMultipartLastName() {
		c1.setName("Tim Schulz-Mueller-Meyer");		// name style 1 with multi-part last name
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller-Meyer");
	}

	@Test @Order(501)
	void test501_setNameMultipartLastName() {
		c1.setName("Schulz-Mueller-Meyer, Tim");	// name style 2
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller-Meyer");
	}

	@Test @Order(502)
	void test502_setNameMultipartLastName() {
		c1.setName("Schulz-Mueller-Meyer; Tim");	// name style 3
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller-Meyer");
	}


	/*
	 * Test cases 510: setName(name) with double first names.
	 */
	@Test @Order(510)
	void test510_setNameDoubleFirstName() {
		c1.setName("Nadine Ulla Blumenfeld");	// name style 1
		assertEquals(c1.getFirstName(), "Nadine Ulla");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}

	@Test @Order(511)
	void test511_setNameDoubleFirstName() {
		c1.setName("Blumenfeld, Nadine Ulla");	// name style 2
		assertEquals(c1.getFirstName(), "Nadine Ulla");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}

	@Test @Order(512)
	void test512_setNameDoubleFirstName() {
		c1.setName("Blumenfeld; Nadine Ulla");	// name style 3
		assertEquals(c1.getFirstName(), "Nadine Ulla");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}


	/*
	 * Test cases 520: setName(name) with multiple first names.
	 */
	@Test @Order(520)
	void test520_setNameMultipartFirstNames() {
		c1.setName("Nadine Ulla Maxine Blumenfeld");	// name style 1
		assertEquals(c1.getFirstName(), "Nadine Ulla Maxine");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}

	@Test @Order(521)
	void test521_setNameMultipartFirstNames() {
		c1.setName("Blumenfeld, Nadine Ulla Maxine");	// name style 2
		assertEquals(c1.getFirstName(), "Nadine Ulla Maxine");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}

	@Test @Order(522)
	void test522_setNameMultipartFirstNames() {
		c1.setName("Blumenfeld; Nadine Ulla Maxine");	// name style 3
		assertEquals(c1.getFirstName(), "Nadine Ulla Maxine");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}


	/*
	 * Test cases 530: setName(name) with multiple first and last names.
	 */
	@Test @Order(530)
	void test530_setNameMultipartFirstNames() {
		c1.setName("Khaled Mohamed Arif Saad-Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "Khaled Mohamed Arif");
		assertEquals(c1.getLastName(), "Saad-Abdelalim");
	}

	@Test @Order(531)
	void test531_setNameMultipartNames() {
		c1.setName("Saad-Abdelalim, Khaled Mohamed-Arif");	// name style 2
		assertEquals(c1.getFirstName(), "Khaled Mohamed-Arif");
		assertEquals(c1.getLastName(), "Saad-Abdelalim");
	}


	/*
	 * Test cases 550: setName(name) with multiple, multi-dash first and last names.
	 */
	@Test @Order(550)
	void test550_setNameMultiDashMultipartFirstNames() {
		c1.setName("Khaled-Mohamed Arif Saad-Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "Khaled-Mohamed Arif");
		assertEquals(c1.getLastName(), "Saad-Abdelalim");
	}

	@Test @Order(551)
	void test551_setNameMultiDashMultipartFirstNames() {
		c1.setName("Khaled-Mohamed-Arif Saad-Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "Khaled-Mohamed-Arif");
		assertEquals(c1.getLastName(), "Saad-Abdelalim");
	}

	@Test @Order(552)
	void test552_setNameMultipartNames() {
		c1.setName("Khaled Mohamed-Arif Saad-Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "Khaled Mohamed-Arif");
		assertEquals(c1.getLastName(), "Saad-Abdelalim");
	}

	@Test @Order(553)
	void test553_setNameMultiDashMultipartFirstNames() {
		c1.setName("Khaled-Mohamed-Arif-Saad-Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "");
		assertEquals(c1.getLastName(), "Khaled-Mohamed-Arif-Saad-Abdelalim");
	}

	@Test @Order(544)
	void test544_setNameMultiDashMultipartFirstNames() {
		c1.setName("Khaled Mohamed Arif Saad Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "Khaled Mohamed Arif Saad");
		assertEquals(c1.getLastName(), "Abdelalim");
	}


	/*
	 * Test cases 550: setName(name) with extreme long names.
	 */
	@Test @Order(550)
	void test550_setNameExtremeLongNames() {
		c1.setName("Auguste Viktoria Friederike Luise Feodora Jenny "
				+ "von-Schleswig-Holstein-Sonderburg-Augustenburg");
		assertEquals(c1.getFirstName(), "Auguste Viktoria Friederike Luise Feodora Jenny");
		assertEquals(c1.getLastName(), "von-Schleswig-Holstein-Sonderburg-Augustenburg");
		//
		c1.setName("Auguste Viktoria Friederike Luise Feodora Jenny "
				+ "von-Schleswig-Holstein-Sonderburg-Augustenburg");
		assertEquals(c1.getFirstName(), "Auguste Viktoria Friederike Luise Feodora Jenny");
		assertEquals(c1.getLastName(), "von-Schleswig-Holstein-Sonderburg-Augustenburg");
		//
		c1.setName("Karl-Theodor Maria Nikolaus Johann Jacob Philipp Franz Joseph Sylvester",
				"Buhl-Freiherr von und zu Guttenberg");
		assertEquals(c1.getFirstName(), "Karl-Theodor Maria Nikolaus Johann Jacob Philipp Franz Joseph Sylvester");
		assertEquals(c1.getLastName(), "Buhl-Freiherr von und zu Guttenberg");
	}


//	@AfterEach
//	void tearDown() throws Exception {
//		System.out.println("@AfterEach");
//	}
//
//	@AfterAll
//	static void tearDownAfterClass() throws Exception {
//		System.out.println("@AfterAll");
//	}
}
