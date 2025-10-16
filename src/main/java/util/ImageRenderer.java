package util;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ImageRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());

		if (value instanceof ImageIcon icon) {
			int cellWidth = table.getColumnModel().getColumn(column).getWidth();
			int cellHeight = table.getRowHeight();

			// Ottieni immagine e scala mantenendo proporzioni
			Image img = icon.getImage();
			double ratio = (double) img.getWidth(null) / img.getHeight(null);
			int newWidth = cellWidth;
			int newHeight = (int) (cellWidth / ratio);
			if (newHeight > cellHeight) {
				newHeight = cellHeight;
				newWidth = (int) (cellHeight * ratio);
			}

			Image scaled = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
			label.setIcon(new ImageIcon(scaled));
		} else {
			label.setText("Nessuna foto");
		}

		return label;
	}
}
