package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.util.Base64;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Base64ImageRenderer extends ColorazioneAlternataRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		final JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
				column);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		try {
			if (value instanceof String base64 && !base64.trim().isEmpty()) {
				// Decodifica Base64
				final byte[] imageData = Base64.getDecoder().decode(base64);
				final ImageIcon originalIcon = new ImageIcon(imageData);

				// Calcola dimensioni disponibili
				final int availableWidth = table.getColumnModel().getColumn(column).getWidth() - 15;
				final int availableHeight = table.getRowHeight(row) - 15;

				// Ridimensiona mantenendo le proporzioni
				final Image originalImage = originalIcon.getImage();
				final double widthRatio = (double) availableWidth / originalIcon.getIconWidth();
				final double heightRatio = (double) availableHeight / originalIcon.getIconHeight();
				final double ratio = Math.min(widthRatio, heightRatio);

				final int newWidth = (int) (originalIcon.getIconWidth() * ratio);
				final int newHeight = (int) (originalIcon.getIconHeight() * ratio);

				final Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				label.setIcon(new ImageIcon(scaledImage));
				label.setText("");
			} else {
				label.setIcon(null);
				label.setText("L'immobile non ha foto");
				label.setForeground(Color.GRAY);
			}
		} catch (Exception e) {
			label.setIcon(null);
			label.setText("❌");
		}

		return label;
	}
}