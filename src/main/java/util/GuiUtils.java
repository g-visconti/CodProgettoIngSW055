package util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GuiUtils {

    public static void setIconaFinestra(Window finestra) {
        // Carica l'immagine come icona
 		URL pathIcona = GuiUtils.class.getClassLoader().getResource("images/DietiEstatesIcona.png");
        ImageIcon icon = new ImageIcon(pathIcona);
        Image img = icon.getImage();
         
        // Imposta l'icona alla finestra
        finestra.setIconImage(img);
    }
    
    public static void setIconaLabel(JLabel etichetta, String nomeIcona) {
    	// Carica l'immagine come icona
 		URL pathIcona = GuiUtils.class.getClassLoader().getResource("images/"+nomeIcona+".png");
        
        // Imposta l'icona alla finestra
        etichetta.setIcon(new ImageIcon(pathIcona));
    }
    
    public static void setIconaButton(JButton pulsante, String nomeIcona) {
    	// Carica l'immagine come icona
 		URL pathIcona = GuiUtils.class.getClassLoader().getResource("images/"+nomeIcona+".png");
        
        // Imposta l'icona alla finestra
        pulsante.setIcon(new ImageIcon(pathIcona));
    }
}
