package start;

import gui.ViewDashboardAgente;

public class Main {

	public static void main(String[] args) {

		//ViewAccesso frame = new ViewAccesso();
		//ViewDashboardAdmin frame = new ViewDashboardAdmin("admin@de25.it");
		ViewDashboardAgente frame = new ViewDashboardAgente("agent3@immobiliare.it");
		//ViewDashboard frame = new ViewDashboard("c.gallo@mail.it");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
