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
		URL pathIcona = GuiUtils.class.getClassLoader().getResource("images/" + nomeIcona + ".png");

		if (pathIcona != null) {
			ImageIcon icon = new ImageIcon(pathIcona);

			// Se la label non ha ancora dimensioni, carico l'icona "così com'è"
			if (etichetta.getWidth() <= 0 || etichetta.getHeight() <= 0) {
				etichetta.setIcon(icon);

				// Aggiungo un listener che scalerà l'immagine appena la label sarà visibile
				etichetta.addComponentListener(new java.awt.event.ComponentAdapter() {
					@Override
					public void componentResized(java.awt.event.ComponentEvent e) {
						if (etichetta.getWidth() > 0 && etichetta.getHeight() > 0) {
							Image img = icon.getImage().getScaledInstance(etichetta.getWidth(), etichetta.getHeight(),
									Image.SCALE_SMOOTH);
							etichetta.setIcon(new ImageIcon(img));
							etichetta.setText("");
							etichetta.removeComponentListener(this); // lo facciamo una volta sola
						}
					}
				});
			} else {
				// La label ha già dimensioni → possiamo scalare subito
				Image img = icon.getImage().getScaledInstance(etichetta.getWidth(), etichetta.getHeight(),
						Image.SCALE_SMOOTH);
				etichetta.setIcon(new ImageIcon(img));
				etichetta.setText("");
			}
		} else {
			etichetta.setText("Icona non trovata");
		}
	}

	public static void setIconaButton(JButton pulsante, String nomeIcona) {
		// Carica l'immagine come icona
		URL pathIcona = GuiUtils.class.getClassLoader().getResource("images/" + nomeIcona + ".png");

		// Imposta l'icona alla finestra
		pulsante.setIcon(new ImageIcon(pathIcona));
	}
}
