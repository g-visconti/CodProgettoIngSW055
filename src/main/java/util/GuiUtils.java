package util;

import java.awt.Image;
import java.awt.Window;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Classe di utilità per operazioni grafiche comuni nell'interfaccia utente Swing.
 * Fornisce metodi statici per la gestione delle icone, immagini e configurazione
 * di componenti visivi.
 *
 * <p>Questa classe è progettata come utility class e non può essere istanziata.
 * Offre funzionalità per:
 * <ul>
 *   <li>Impostare icone per finestre e componenti</li>
 *   <li>Caricare e ridimensionare immagini</li>
 *   <li>Configurare gallerie di immagini</li>
 *   <li>Gestire immagini in formato byte[]</li>
 * </ul>
 *
 * @author IngSW2425_055 Team
 * @see Window
 * @see JLabel
 * @see JButton
 * @see ImageIcon
 */
public class GuiUtils {

	/**
	 * Costruttore privato per prevenire l'istanziazione.
	 * Questa è una utility class con solo metodi statici.
	 *
	 * @throws IllegalStateException Se si tenta di istanziare la classe
	 */
	private GuiUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Imposta l'icona dell'applicazione per una finestra.
	 * Carica l'icona dal percorso delle risorse "images/DietiEstatesIcona.png"
	 * e la applica alla finestra specificata.
	 *
	 * @param finestra La finestra a cui impostare l'icona
	 * @throws NullPointerException Se la finestra è null
	 * @see Window#setIconImage(Image)
	 */
	public static void setIconaFinestra(Window finestra) {
		// Carica l'immagine come icona
		final URL pathIcona = GuiUtils.class.getClassLoader().getResource("images/DietiEstatesIcona.png");
		if (pathIcona != null) {
			final ImageIcon icon = new ImageIcon(pathIcona);
			final Image img = icon.getImage();
			// Imposta l'icona alla finestra
			finestra.setIconImage(img);
		}
	}

	/**
	 * Imposta un'icona su un'etichetta JLabel, ridimensionandola automaticamente.
	 * L'icona viene caricata dal percorso "images/[nomeIcona].png" e adattata
	 * alle dimensioni dell'etichetta.
	 *
	 * @param etichetta L'etichetta su cui impostare l'icona
	 * @param nomeIcona Il nome del file icona (senza estensione .png)
	 * @throws NullPointerException Se l'etichetta è null o il nomeIcona è null
	 * @see JLabel
	 * @see Image#SCALE_SMOOTH
	 */
	public static void setIconaLabel(JLabel etichetta, String nomeIcona) {
		final URL pathIcona = GuiUtils.class.getClassLoader().getResource("images/" + nomeIcona + ".png");

		if (pathIcona != null) {
			final ImageIcon icon = new ImageIcon(pathIcona);

			if (etichetta.getWidth() <= 0 || etichetta.getHeight() <= 0) {
				etichetta.setIcon(icon);

				etichetta.addComponentListener(new java.awt.event.ComponentAdapter() {
					@Override
					public void componentResized(java.awt.event.ComponentEvent e) {
						if (etichetta.getWidth() > 0 && etichetta.getHeight() > 0) {
							final Image img = icon.getImage().getScaledInstance(etichetta.getWidth(),
									etichetta.getHeight(), Image.SCALE_SMOOTH);
							etichetta.setIcon(new ImageIcon(img));
							etichetta.setText("");
							etichetta.removeComponentListener(this);
						}
					}
				});
			} else {
				final Image img = icon.getImage().getScaledInstance(etichetta.getWidth(), etichetta.getHeight(),
						Image.SCALE_SMOOTH);
				etichetta.setIcon(new ImageIcon(img));
				etichetta.setText("");
			}
		} else {
			etichetta.setText("Icona non trovata");
			etichetta.setIcon(null);
		}
	}

	/**
	 * Imposta un'icona su un pulsante JButton.
	 * L'icona viene caricata dal percorso "images/[nomeIcona].png" e ridimensionata
	 * a dimensioni fisse di 20x20 pixel.
	 *
	 * @param pulsante Il pulsante su cui impostare l'icona
	 * @param nomeIcona Il nome del file icona (senza estensione .png)
	 * @throws NullPointerException Se il pulsante è null o il nomeIcona è null
	 * @see JButton
	 */
	public static void setIconaButton(JButton pulsante, String nomeIcona) {
		// Carica l'immagine come icona
		final URL pathIcona = GuiUtils.class.getClassLoader().getResource("images/" + nomeIcona + ".png");

		if (pathIcona != null) {
			final ImageIcon iconaOriginale = new ImageIcon(pathIcona);
			// Ridimensiona l'icona per adattarla al pulsante
			final Image img = iconaOriginale.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			pulsante.setIcon(new ImageIcon(img));
		} else {
			System.err.println("Icona non trovata: " + nomeIcona);
		}
	}

	/**
	 * Carica un'immagine da un array di byte in un'etichetta JLabel.
	 * L'immagine viene ridimensionata alle dimensioni specificate.
	 *
	 * @param etichetta L'etichetta in cui visualizzare l'immagine
	 * @param datiImmagine L'immagine come array di byte (formati supportati: PNG, JPEG, etc.)
	 * @param larghezza La larghezza desiderata per l'immagine ridimensionata
	 * @param altezza L'altezza desiderata per l'immagine ridimensionata
	 * @throws IllegalArgumentException Se larghezza o altezza sono ≤ 0
	 * @see JLabel
	 * @see ImageIcon
	 */
	public static void caricaImmagineInEtichetta(JLabel etichetta, byte[] datiImmagine, int larghezza, int altezza) {
		if (datiImmagine != null && datiImmagine.length > 0) {
			try {
				final ImageIcon icona = new ImageIcon(datiImmagine);
				final Image img = icona.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
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
	 * Configura una galleria di immagini su due etichette: una per l'immagine principale
	 * e una per il contatore delle immagini.
	 *
	 * @param etichettaPrincipale L'etichetta che mostra l'immagine corrente
	 * @param etichettaContatore L'etichetta che mostra il contatore (es: "1/5")
	 * @param immagini La lista di immagini come array di byte
	 * @param indiceCorrente L'indice dell'immagine corrente da visualizzare
	 * @throws NullPointerException Se una delle etichette è null
	 * @throws IndexOutOfBoundsException Se l'indice corrente è fuori dai limiti della lista
	 * @see List
	 */
	public static void configuraGalleriaImmagini(JLabel etichettaPrincipale, JLabel etichettaContatore,
			List<byte[]> immagini, int indiceCorrente) {
		if (immagini != null && !immagini.isEmpty() && indiceCorrente >= 0 && indiceCorrente < immagini.size()) {
			// Usa dimensioni di default se la label non ha dimensioni definite
			final int larghezza = etichettaPrincipale.getWidth() > 0 ? etichettaPrincipale.getWidth() : 300;
			final int altezza = etichettaPrincipale.getHeight() > 0 ? etichettaPrincipale.getHeight() : 200;

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