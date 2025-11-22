package util;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class TableUtils {
	/**
	 * Nasconde una colonna
	 */
	public static void nascondiColonna(JTable tabella, int indice) {
		TableColumn colonna = tabella.getColumnModel().getColumn(indice);
		colonna.setMinWidth(0);
		colonna.setMaxWidth(0);
		colonna.setWidth(0);
		colonna.setPreferredWidth(0);
	}

	/**
	 * Fissa una colonna
	 */
	public static void fissaColonna(JTable tabella, int indice, int larghezza) {
		TableColumn colonna = tabella.getColumnModel().getColumn(indice);
		colonna.setPreferredWidth(larghezza);
		colonna.setMinWidth(larghezza);
		colonna.setMaxWidth(larghezza);
		colonna.setResizable(false);
	}

	/**
	 * Imposta la larghezza di una colonna
	 */
	public static void larghezzaColonna(JTable tabella, int indice, int larghezza) {
		TableColumn colonna = tabella.getColumnModel().getColumn(indice);
		colonna.setPreferredWidth(larghezza);
	}

}

