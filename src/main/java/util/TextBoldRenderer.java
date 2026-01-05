package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class TextBoldRenderer extends ColorazioneAlternataRenderer {

	private final boolean center;
	private final Color textColor;

	public TextBoldRenderer(boolean center, Color textColor) {
		// ✅ Chiama il costruttore parent
		this.center = center;
		this.textColor = textColor;
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		final JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
				column);

		// Imposta font più grande e in grassetto
		label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));

		// Centra se richiesto
		if (center) {
			label.setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			label.setHorizontalAlignment(SwingConstants.LEFT);
		}

		// Colore testo personalizzato (solo se non selezionato)
		if (!isSelected && textColor != null) {
			label.setForeground(textColor);
		}

		return label;
	}
}