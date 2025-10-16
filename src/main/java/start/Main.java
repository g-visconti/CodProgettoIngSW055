package start;

import gui.ViewDashboard;

public class Main {

	public static void main(String[] args) {

		// Accesso generico
		// ViewAccesso frame = new ViewAccesso();

		// Admin
		// ViewDashboardAdmin frame = new
		// ViewDashboardAdmin("admimmobiliare@immobiliare.it");

		// Agente
		// ViewDashboardAgente frame = new
		// ViewDashboardAgente("lucamagnini@immobiliare.it");

		// Utente
		ViewDashboard frame = new ViewDashboard("marioros@gmail.com");

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
