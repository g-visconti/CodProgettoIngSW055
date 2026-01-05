package util;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class TableUtils {
	private TableUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Nasconde una colonna
	 */
	public static void nascondiColonna(JTable tabella, int indice) {
		final TableColumn colonna = tabella.getColumnModel().getColumn(indice);
		colonna.setMinWidth(0);
		colonna.setMaxWidth(0);
		colonna.setWidth(0);
		colonna.setPreferredWidth(0);
	}

	/**
	 * Fissa una colonna
	 */
	public static void fissaColonna(JTable tabella, int indice, int larghezza) {
		final TableColumn colonna = tabella.getColumnModel().getColumn(indice);
		colonna.setPreferredWidth(larghezza);
		colonna.setMinWidth(larghezza);
		colonna.setMaxWidth(larghezza);
		colonna.setResizable(false);
	}

	/**
	 * Imposta la larghezza di una colonna
	 */
	public static void larghezzaColonna(JTable tabella, int indice, int larghezza) {
		final TableColumn colonna = tabella.getColumnModel().getColumn(indice);
		colonna.setPreferredWidth(larghezza);
	}

	/**
	 * Imposta il formato italiano al prezzo
	 */
	public static String formattaPrezzo(Object importo) {
		final NumberFormat format = NumberFormat.getNumberInstance(Locale.ITALY);
		format.setGroupingUsed(true);
		format.setMaximumFractionDigits(0);
		format.setMinimumFractionDigits(0);

		if (importo instanceof Number) {
			return "€ " + format.format(importo);
		}
		return "€ 0"; // o un valore di default
	}

	/**
	 * Formattazione della data
	 */
	public static String formattaData(LocalDateTime data) {
		if (data == null) {
			return "";
		} else {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			return data.format(formatter);
		}
	}

}
