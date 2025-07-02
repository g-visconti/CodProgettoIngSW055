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
    
    
}
