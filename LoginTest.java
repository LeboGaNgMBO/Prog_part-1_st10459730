import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LoginTest {

    // Test username: correctly formatted
    @Test
    public void testUsernameCorrectlyFormatted() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkUserName());
    }

    // Test username: incorrectly formatted
    @Test
    public void testUsernameIncorrectlyFormatted() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(user.checkUserName());
    }

    // Test password: correctly formatted
    @Test
    public void testPasswordCorrectlyFormatted() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    // Test password: incorrectly formatted
    @Test
    public void testPasswordIncorrectlyFormatted() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    // Test cellphone number: correctly formatted
    @Test
    public void testCellPhoneCorrectlyFormatted() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    // Test cellphone number: incorrectly formatted
    @Test
    public void testCellPhoneIncorrectlyFormatted() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertFalse(user.checkCellPhoneNumber());
    }

    // Test successful login
    @Test
    public void testLoginSuccessful() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(
                user.loginUser("kyl_1", "Ch&&sec@ke99!")
        );
    }

    // Test failed login
    @Test
    public void testLoginFailed() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(
                user.loginUser("wrong_username", "wrong_password")
        );
    }

    // Test successful login message
    @Test
    public void testSuccessfulLoginMessage() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                "Welcome Kyle Smith, it is great to see you again.",
                user.returnLoginStatus("kyl_1", "Ch&&sec@ke99!")
        );
    }

    // Test failed login message
    @Test
    public void testFailedLoginMessage() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                "Username or password incorrect, please try again.",
                user.returnLoginStatus(
                        "wrong_username",
                        "wrong_password"
                )
        );
    }

    // Test registration message
    @Test
    public void testSuccessfulRegistration() {

        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                "User successfully registered.",
                user.registerUser()
        );
    }
}

