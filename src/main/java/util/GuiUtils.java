package util;

import java.awt.Image;
import java.awt.Window;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class GuiUtils {

	public static void setIconaFinestra(Window finestra) {
		// Carica l'immagine come icona
		URL pathIcona = GuiUtils.class.getClassLoader().getResource("images/DietiEstatesIcona.png");
		if (pathIcona != null) {
			ImageIcon icon = new ImageIcon(pathIcona);
			Image img = icon.getImage();
			// Imposta l'icona alla finestra
			finestra.setIconImage(img);
		}
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
			etichetta.setIcon(null);
		}
	}

	public static void setIconaButton(JButton pulsante, String nomeIcona) {
		// Carica l'immagine come icona
		URL pathIcona = GuiUtils.class.getClassLoader().getResource("images/" + nomeIcona + ".png");

		if (pathIcona != null) {
			ImageIcon iconaOriginale = new ImageIcon(pathIcona);
			// Ridimensiona l'icona per adattarla al pulsante
			Image img = iconaOriginale.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			pulsante.setIcon(new ImageIcon(img));
		} else {
			System.err.println("Icona non trovata: " + nomeIcona);
		}
	}


	/**
	 * Carica un'immagine in un'etichetta
	 */
	public static void caricaImmagineInEtichetta(JLabel etichetta, byte[] datiImmagine, int larghezza, int altezza) {
		if (datiImmagine != null && datiImmagine.length > 0) {
			try {
				ImageIcon icona = new ImageIcon(datiImmagine);
				Image img = icona.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
				etichetta.setIcon(new ImageIcon(img));
				etichetta.setText("");
			} catch (Exception ex) {
				etichetta.setText("Immagine non valida");
				etichetta.setIcon(null);
				System.err.println("Errore caricamento immagine: " + ex.getMessage());
			}
		} else {
			etichetta.setText("Nessuna immagine");
			etichetta.setIcon(null);
		}
	}

	/**
	 * Configura una galleria immagini
	 */
	public static void configuraGalleriaImmagini(JLabel etichettaPrincipale, JLabel etichettaContatore,
			List<byte[]> immagini, int indiceCorrente) {
		if (immagini != null && !immagini.isEmpty() && indiceCorrente >= 0 && indiceCorrente < immagini.size()) {
			// Usa dimensioni ragionevoli di default se la label non ha dimensioni definite
			int larghezza = etichettaPrincipale.getWidth() > 0 ? etichettaPrincipale.getWidth() : 300;
			int altezza = etichettaPrincipale.getHeight() > 0 ? etichettaPrincipale.getHeight() : 200;

			caricaImmagineInEtichetta(etichettaPrincipale, immagini.get(indiceCorrente), larghezza, altezza);

			if (immagini.size() > 1) {
				etichettaContatore.setText((indiceCorrente + 1) + "/" + immagini.size());
			} else {
				etichettaContatore.setText("");
			}
		} else {
			etichettaPrincipale.setText("Nessuna immagine disponibile");
			etichettaPrincipale.setIcon(null);
			etichettaContatore.setText("");
		}
	}
}