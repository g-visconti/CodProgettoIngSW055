package start;

import gui.ViewAccesso;
import gui.amministrazione.ViewDashboardDietiEstates;
import gui.utenza.ViewDashboard;

public class Main {

	public static void main(String[] args) {

		/*
		 *
		 * Accesso generico 1 Admin 2 Supporto Gallo 3 Agente Magnini 4 Agente Torre 5
		 * Utente
		 *
		 */

		final int x = 5;

		try {
			switch (x) {
			case 1 -> {
				final ViewDashboardDietiEstates frameAdmin = new ViewDashboardDietiEstates("admdietiestates@de25.it");
				frameAdmin.setLocationRelativeTo(null);
				frameAdmin.setVisible(true);
			}
			case 2 -> {
				final ViewDashboardDietiEstates frameHelp = new ViewDashboardDietiEstates("claudiagallo@de25.it");
				frameHelp.setLocationRelativeTo(null);
				frameHelp.setVisible(true);
			}
			case 3 -> {
				final ViewDashboardDietiEstates frameAgent1 = new ViewDashboardDietiEstates(
						"lucamagnini@immobiliare.it");
				frameAgent1.setLocationRelativeTo(null);
				frameAgent1.setVisible(true);
			}
			case 4 -> {
				final ViewDashboardDietiEstates frameAgent2 = new ViewDashboardDietiEstates("giamptorre@de25.it");
				frameAgent2.setLocationRelativeTo(null);
				frameAgent2.setVisible(true);
			}
			case 5 -> {
				final ViewDashboard frameUser = new ViewDashboard("gae.visconti@mail.it");
				frameUser.setLocationRelativeTo(null);
				frameUser.setVisible(true);
			}
			default -> {
				final ViewAccesso frame = new ViewAccesso();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
