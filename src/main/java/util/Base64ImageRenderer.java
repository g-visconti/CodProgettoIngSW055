package util;

import java.awt.Component;
import java.awt.Image;
import java.util.Base64;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class Base64ImageRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
                                                  boolean isSelected, boolean hasFocus, 
                                                  int row, int column) {
        
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Colori di selezione
        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        } else {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
        }

        try {
            if (value instanceof String base64 && !base64.isBlank()) {
                // Decodifica Base64
                byte[] imageData = Base64.getDecoder().decode(base64);
                ImageIcon originalIcon = new ImageIcon(imageData);
                
                // Calcola dimensioni della cella
                int cellWidth = table.getColumnModel().getColumn(column).getWidth() - 10;
                int cellHeight = table.getRowHeight(row) - 10;
                
                // Ridimensiona mantenendo le proporzioni
                Image originalImage = originalIcon.getImage();
                double widthRatio = (double) cellWidth / originalIcon.getIconWidth();
                double heightRatio = (double) cellHeight / originalIcon.getIconHeight();
                double ratio = Math.min(widthRatio, heightRatio);
                
                int newWidth = (int) (originalIcon.getIconWidth() * ratio);
                int newHeight = (int) (originalIcon.getIconHeight() * ratio);
                
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
                label.setText("");
            } else {
                label.setIcon(null);
                label.setText("NO IMG");
            }
        } catch (Exception e) {
            label.setIcon(null);
            label.setText("ERRORE");
        }
        
        return label;
    }
}