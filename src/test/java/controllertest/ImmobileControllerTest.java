
package controllertest;


import static org.junit.jupiter.api.Assertions.*;








import org.junit.jupiter.api.Test;

import controller.ImmobileController;

public class ImmobileControllerTest {

    private final ImmobileController controller = new ImmobileController();

    /**
     * Classe di equivalenza: INPUT VALIDO
     */
    @Test
    void testInputValido() {
        assertDoesNotThrow(() ->
            controller.validaImmobile(
                    "Casa",
                    "Via Roma 10",
                    "Milano",
                    "Appartamento luminoso",
                    "Vendita",
                    80,
                    200000,
                    2
            )
        );
    }

    /**
     * Classe di equivalenza: CAMPI TESTUALI NON VALIDI
     * Almeno un campo testuale vuoto
     */
    @Test
    void testCampoTestualeVuoto() {
        assertThrows(IllegalArgumentException.class, () ->
            controller.validaImmobile(
                    "",                // titolo non valido
                    "Via Roma 10",
                    "Milano",
                    "Descrizione",
                    "Affitto",
                    70,
                    1000,
                    1
            )
        );
    }

    /**
     * Classe di equivalenza: TIPOLOGIA NON VALIDA
     */
    @Test
    void testTipologiaNonValida() {
        assertThrows(IllegalArgumentException.class, () ->
            controller.validaImmobile(
                    "Casa",
                    "Via Roma",
                    "Milano",
                    "Descrizione",
                    "-",               // tipologia non valida
                    70,
                    1000,
                    1
            )
        );
    }

    /**
     * Classe di equivalenza: VALORI NUMERICI NON VALIDI
     * Dimensione <= 0 (caso rappresentativo)
     */
    @Test
    void testValoriNumericiNonValidi() {
        assertThrows(IllegalArgumentException.class, () ->
            controller.validaImmobile(
                    "Casa",
                    "Via Roma",
                    "Milano",
                    "Descrizione",
                    "Vendita",
                    0,                // dimensione non valida
                    1000,
                    1
            )
        );
    }

    /**
     * Classe di equivalenza: PIANO NON VALIDO
     * Piano < -1
     */
    @Test
    void testPianoNonValido() {
        assertThrows(IllegalArgumentException.class, () ->
            controller.validaImmobile(
                    "Casa",
                    "Via Roma",
                    "Milano",
                    "Descrizione",
                    "Vendita",
                    80,
                    1000,
                    -5               // piano non valido
            )
        );
    }
}
