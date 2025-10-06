package util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {

	public static ImageIcon decodeToIcon(String base64, int width, int height) {
		try {
			if (base64 == null || base64.isBlank()) {
				return getDefaultIcon(width, height);
			}

			byte[] bytes = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			BufferedImage img = ImageIO.read(bis);
			System.out.println("Dimensione immagine originale: " + img.getWidth() + "x" + img.getHeight());

			if (img == null)
				return getDefaultIcon(width, height);

			BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = resized.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(img, 0, 0, width, height, null);
			g2.dispose();

			return new ImageIcon(resized);

		} catch (Exception e) {
			return getDefaultIcon(width, height);
		}
	}

	private static ImageIcon getDefaultIcon(int width, int height) {
		BufferedImage placeholder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = placeholder.createGraphics();
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawRect(1, 1, width - 2, height - 2);
		g2d.dispose();
		return new ImageIcon(placeholder);

	}

}