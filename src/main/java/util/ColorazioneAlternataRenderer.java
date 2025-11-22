package util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ColorazioneAlternataRenderer extends DefaultTableCellRenderer {

	private final Color coloreRighePari;
	private final Color coloreRigheDispari;

	// Costruttore con colori di default
	public ColorazioneAlternataRenderer() {
		coloreRighePari = Color.WHITE;
		coloreRigheDispari = new Color(248, 248, 248); // Grigio molto chiaro
	}

	// Costruttore con colori personalizzati
	public ColorazioneAlternataRenderer(Color coloreRighePari, Color coloreRigheDispari) {
		this.coloreRighePari = coloreRighePari;
		this.coloreRigheDispari = coloreRigheDispari;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// Applica colorazione alternata solo se la riga non è selezionata
		if (!isSelected) {
			if (row % 2 == 0) {
				componente.setBackground(coloreRighePari);
			} else {
				componente.setBackground(coloreRigheDispari);
			}
		} else {
			componente.setBackground(table.getSelectionBackground());
		}

		return componente;
	}
}