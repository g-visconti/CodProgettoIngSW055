
package controllertest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.OfferteController;

public class OfferteControllerTest {

    private OfferteController controller;

    @BeforeEach
    void setUp() {
        controller = new OfferteController();
    }

    @Test
    void contropropostaValida() {
        // Arrange / Let
        double controproposta = 1200.0;
        double offertaIniziale = 1000.0;

        // Act
        boolean result = controller.isValidControproposta(controproposta, offertaIniziale);

        // Assert
        assertTrue(result);
    }

    @Test
    void contropropostaMinoreOffertaIniziale_nonValida() {
        // Arrange / Let
        double controproposta = 800.0;
        double offertaIniziale = 1000.0;

        // Act
        boolean result = controller.isValidControproposta(controproposta, offertaIniziale);

        // Assert
        assertFalse(result);
    }

    @Test
    void contropropostaUgualeOffertaIniziale_nonValida() {
        // Arrange / Let
        double controproposta = 1000.0;
        double offertaIniziale = 1000.0;

        // Act
        boolean result = controller.isValidControproposta(controproposta, offertaIniziale);

        // Assert
        assertFalse(result);
    }

    @Test
    void contropropostaNegativa_nonValida() {
        // Arrange / Let
        double controproposta = -500.0;
        double offertaIniziale = 1000.0;

        // Act
        boolean result = controller.isValidControproposta(controproposta, offertaIniziale);

        // Assert
        assertFalse(result);
    }

    @Test
    void contropropostaZero_nonValida() {
        // Arrange / Let
        double controproposta = 0;
        double offertaIniziale = 1000.0;

        // Act
        boolean result = controller.isValidControproposta(controproposta, offertaIniziale);

        // Assert
        assertFalse(result);
    }
}
