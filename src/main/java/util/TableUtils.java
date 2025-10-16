package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class TableUtils {
	@SuppressWarnings("serial")
	public static void setImageRenderer(JTable table, int columnIndex) {
		table.getColumnModel().getColumn(columnIndex).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				JLabel label = new JLabel();
				label.setOpaque(true);

				// Allineamento e padding
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				if (value instanceof ImageIcon icon) {
					// Ottieni dimensioni della cella
					int width = table.getColumnModel().getColumn(column).getWidth();
					int height = table.getRowHeight(row);

					// Ridimensiona immagine alla cella
					Image img = icon.getImage().getScaledInstance(width - 10, height - 10, Image.SCALE_SMOOTH);
					label.setIcon(new ImageIcon(img));
					label.setText("");
				} else {
					label.setText("Nessuna foto");
					label.setForeground(Color.GRAY);
				}

				return label;
			}
		});
	}
}
