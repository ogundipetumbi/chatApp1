

import com.mycompany.chatapp1.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    Login login = new Login();

    // =========================
    // USERNAME TESTS
    // =========================

    @Test
    public void testUsernameCorrectlyFormatted() {
        boolean result = login.checkUserName("kyl_1");
        assertTrue(result);
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        boolean result = login.checkUserName("kyle!!!!!!!");
        assertFalse(result);
    }

    // =========================
    // PASSWORD TESTS
    // =========================

    @Test
    public void testPasswordMeetsComplexity() {
        boolean result = login.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(result);
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        boolean result = login.checkPasswordComplexity("password");
        assertFalse(result);
    }

    // =========================
    // CELL PHONE TESTS
    // =========================

    @Test
    public void testCellPhoneCorrectFormat() {
        boolean result = login.checkCellPhoneNumber("+27838968976");
        assertTrue(result);
    }

    @Test
    public void testCellPhoneIncorrectFormat() {
        boolean result = login.checkCellPhoneNumber("08966553");
        assertFalse(result);
    }

    // =========================
    // REGISTER USER TEST
    // =========================

    @Test
    public void testRegisterUserSuccess() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("User registered successfully.", result);
    }

    @Test
    public void testRegisterUserUsernameFail() {
        String result = login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }

    @Test
    public void testRegisterUserPasswordFail() {
        String result = login.registerUser("kyl_1", "password", "+27838968976");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
    }

    @Test
    public void testRegisterUserCellFail() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);
    }

    // =========================
    // LOGIN TESTS
    // =========================

    @Test
    public void testLoginSuccessful() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        boolean result = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(result);
    }

    @Test
    public void testLoginFailed() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        boolean result = login.loginUser("wrong", "wrong");
        assertFalse(result);
    }
}