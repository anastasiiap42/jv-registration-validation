package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.add(new User(1L, "firstUser", "somePassword", 19));
        Storage.people.add(new User(2L, "secondUser", "somePassword", 30));
        Storage.people.add(new User(3L, "thirdUser", "somePassword", 19));
        Storage.people.add(new User(4L, "forthUser", "somePassword", 101));
    }

    @Test
    void register_nullUser_throws() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_validUserData_ok() {
        User newUser = new User(5L, "fifthUser", "123456", 19);
        User result = registrationService.register(newUser);
        assertEquals(newUser, result);
    }

    @Test
    void register_existingLogin_notOk() {
        User newUser = new User(6L, "firstUser", "123456", 19);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullLogin_notOk() {
        User newUser = new User(6L, null, "123456", 19);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_loginTooShort_notOk() {
        User newUser = new User(6L, "login", "123456", 19);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_loginEmpty_notOk() {
        User newUser = new User(6L, "", "123456", 19);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_passwordTooShort_notOk() {
        User newUser = new User(6L, "sixthUser", "12345", 19);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_passwordEmpty_notOk() {
        User newUser = new User(6L, "seventhUser", "", 19);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User newUser = new User(6L, "eighthUser", null, 19);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_ageTooLittle_notOk() {
        User newUser = new User(6L, "ninthUser", "password", 17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_ageNegative_notOk() {
        User newUser = new User(6L, "tenthUser", "password", -1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_ageIsEighteen_ok() {
        User newUser = new User(6L, "eleventhUser", "password", 18);
        User result = registrationService.register(newUser);
        assertEquals(newUser, result);
    }

    @Test
    void register_nullAge_notOk() {
        User newUser = new User(6L, "twelfthUser", "password", null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @AfterEach
    void tearDown() {
    }
}
