
package controllertest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.AccessController;

public class AccessControllerTest {

    private AccessController controller;

    @BeforeEach
    void setUp() {
        // Arrange / Let
        controller = new AccessController();
    }

    /* =========================
       TEST isValidEmail (ridotto)
       ========================= */

    @Test
    void emailNulla_nonValida() {
        // Arrange / Let
        String email = null;

        // Act
        boolean result = controller.isValidEmail(email, false);

        // Assert
        assertFalse(result);
    }

    @Test
    void emailVuotaConsentita() {
        // Arrange / Let
        String email = "";

        // Act
        boolean result = controller.isValidEmail(email, true);

        // Assert
        assertTrue(result);
    }

    @Test
    void emailFormatoCorretto_valida() {
        // Arrange / Let
        String email = "utente@mail.com";

        // Act
        boolean result = controller.isValidEmail(email, false);

        // Assert
        assertTrue(result);
    }

    @Test
    void emailFormatoErrato_nonValida() {
        // Arrange / Let
        String email = "user@ma il.com";

        // Act
        boolean result = controller.isValidEmail(email, false);

        // Assert
        assertFalse(result);
    }

    /* #########################
       TEST isValidPassword
       ######################### */

    @Test
    void passwordNulla_nonValida() {
        // Arrange / Let
        String password = null;
        String conferma = null;

        // Act
        boolean result = controller.isValidPassword(password, conferma);

        // Assert
        assertFalse(result);
    }

    @Test
    void passwordDiversaDaConferma_nonValida() {
        // Arrange / Let
        String password = "abc123";
        String conferma = "abc124";

        // Act
        boolean result = controller.isValidPassword(password, conferma);

        // Assert
        assertFalse(result);
    }

    @Test
    void passwordValida() {
        // Arrange / Let
        String password = "abc123";
        String conferma = "abc123";

        // Act
        boolean result = controller.isValidPassword(password, conferma);

        // Assert
        assertTrue(result);
    }

    @Test
    void passwordSenzaNumeri_nonValida() {
        // Arrange / Let
        String password = "abcdef";
        String conferma = "abcdef";

        // Act
        boolean result = controller.isValidPassword(password, conferma);

        // Assert
        assertFalse(result);
    }
}
