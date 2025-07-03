package util;

import javax.swing.JComboBox;

public class InputUtils {
	
	// Converte una stringa di testo in un valore intero
    public static Integer parseInteger(String text) {
        try {
            return text.isEmpty() ? null : Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    
    // Converte il valore testuale di una combobox in un valore intero
    public static Integer parseComboInteger(JComboBox<String> combo) {
        String selected = (String) combo.getSelectedItem();
        if (selected == null || selected.equalsIgnoreCase("Indifferente") || selected.isEmpty()) {
            return null;
        }
        try {
            return parseInteger(selected);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    // Ogni iniziale di una parola è posta in maiuscolo
    public static String capitalizzaParole(String input) {
    	// Converto l'input tutto in lettere minuscole
    	// divido in stringhe rimuovendo una o più occorenze degli spazi bianchi
    	String[] parole = input.toLowerCase().split("\\s+");
    	// Alternativa per costruire stringhe come concatenazione di parole
        StringBuilder risultato = new StringBuilder();

        for (String parola : parole) {
            if (!parola.isEmpty()) {
                risultato.append(Character.toUpperCase(parola.charAt(0)))
                         .append(parola.substring(1))
                         .append(" ");
            }
        }
        
        // Prima di ritornare al chiamante
        // Converto l'oggetto risultato (che è una seq. di caratteri) StringBuilder
        // in una stringa, infine rimuovo eventuali spazi bianchi ad inizio e fine stringa
        return risultato.toString().trim();
    }
}
