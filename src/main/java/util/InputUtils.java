package util;

public class InputUtils {
	
	// Converte una stringa di testo in un valore intero
    public static Integer parseInteger(String text) {
        try {
            return text.isEmpty() ? null : Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
}
