package start;


import gui.ViewAccesso;


public class Main {

	public static void main(String[] args) {

		// Accesso generico
		ViewAccesso frame = new ViewAccesso();

		// Admin
		//ViewDashboardDietiEstates frame = new ViewDashboardDietiEstates("admimmobiliare@immobiliare.it");

		// Supporto
		//ViewDashboardDietiEstates frame = new ViewDashboardDietiEstates("claudiagallo@de25.it");

		// Agente
		//ViewDashboardDietiEstates frame = new ViewDashboardDietiEstates("lucamagnini@immobiliare.it");

		// Utente
		//ViewDashboard frame = new ViewDashboard("marioros@gmail.com");

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
