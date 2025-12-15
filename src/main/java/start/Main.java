package start;


import gui.ViewAccesso;
import gui.amministrazione.ViewDashboardDietiEstates;
import gui.utenza.ViewDashboard;


public class Main {

	public static void main(String[] args) {

		/*

		 *  Accesso generico
	 	 1  Admin
	 	 2  Supporto
	 	 3  Agente Magnini
	 	 4  Agente Torre
	 	 5  Utente

		 */

		int x=3;

		try {
			switch(x) {
			case 1:	// 								1	Admin
				ViewDashboardDietiEstates frameAdmin = new ViewDashboardDietiEstates("admdietiestates@de25.it");
				frameAdmin.setLocationRelativeTo(null);
				frameAdmin.setVisible(true);
				break;
			case 2:	// 								2	Supporto
				ViewDashboardDietiEstates frameHelp = new ViewDashboardDietiEstates("claudiagallo@de25.it");
				frameHelp.setLocationRelativeTo(null);
				frameHelp.setVisible(true);
				break;

			case 3:	// 								3	Agente Magnini
				ViewDashboardDietiEstates frameAgent1 = new ViewDashboardDietiEstates("lucamagnini@immobiliare.it");
				frameAgent1.setLocationRelativeTo(null);
				frameAgent1.setVisible(true);
				break;
			case 4:	// 								4	Agente Torre
				ViewDashboardDietiEstates frameAgent2 = new ViewDashboardDietiEstates("giamptorre@de25.it");
				frameAgent2.setLocationRelativeTo(null);
				frameAgent2.setVisible(true);
				break;
			case 5:	// 								5	Utente
				ViewDashboard frameUser = new ViewDashboard("gae.visconti@mail.it");
				frameUser.setLocationRelativeTo(null);
				frameUser.setVisible(true);
				break;
			default:	// 							*	Accesso generico
				ViewAccesso frame = new ViewAccesso();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
