package util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class TextAreaRenderer extends JTextArea implements TableCellRenderer {

	public TextAreaRenderer() {
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
		setEditable(false);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		setText(value == null ? "" : value.toString());

		if (!isSelected) {
			if (row % 2 == 0) {
				setBackground(Color.WHITE);
			} else {
				setBackground(new Color(248, 248, 248));
			}
		} else {
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());
		}

		if (!isSelected) {
			setForeground(table.getForeground());
		}

		setFont(table.getFont());

		// Imposta dimensioni per il wrapping
		int width = table.getColumnModel().getColumn(column).getWidth() - 10;
		setSize(width, table.getRowHeight());

		return this;
	}
}