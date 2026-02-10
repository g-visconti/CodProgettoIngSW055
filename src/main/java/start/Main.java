package start;

import dao.AccountDAO;
import java.util.logging.Logger;
import java.util.logging.Level;
import gui.ViewAccesso;
import gui.amministrazione.ViewDashboardDietiEstates;
import gui.utenza.ViewDashboard;

/**
 * Classe principale di avvio dell'applicazione DietiEstates25.
 * Contiene il metodo {@code main} che funge da punto di ingresso per l'applicazione
 * e gestisce la configurazione iniziale dell'interfaccia grafica.
 *
 * <p>Questa classe fornisce diverse modalità di avvio per facilitare lo sviluppo
 * e il testing:
 * <ul>
 *   <li>Accesso generico: schermata di login standard per nuovi utenti</li>
 *   <li>Accesso diretto con ruoli predefiniti: Admin, Supporto, Agente, Utente</li>
 * </ul>
 *
 * <p>Modalità di selezione:
 * La variabile {@code x} determina quale schermata viene aperta all'avvio:
 * <ul>
 *   <li>Valori 1-5: accesso diretto con account specifico</li>
 *   <li>Altri valori: schermata di login standard</li>
 * </ul>
 *
 * @author IngSW2425_055 Team
 * @see ViewAccesso
 * @see ViewDashboard
 * @see ViewDashboardDietiEstates
 */
public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	/**
	 * Metodo principale di avvio dell'applicazione DietiEstates25.
	 * Gestisce l'inizializzazione dell'interfaccia grafica in base alla modalità
	 * selezionata per facilitare sviluppo, testing e dimostrazioni.
	 *
	 * <p>Il metodo utilizza un intero {@code x} per determinare il comportamento:
	 * <ul>
	 *   <li>Se {@code x} è tra 1 e 5: apre direttamente la dashboard corrispondente
	 *       all'account preconfigurato</li>
	 *   <li>Altrimenti: apre la schermata di accesso standard per nuovo login</li>
	 * </ul>
	 *
	 * @param args Argomenti da riga di comando (non utilizzati nell'applicazione corrente)
	 * @throws Exception Se si verifica un errore durante l'inizializzazione delle finestre grafiche
	 */
	public static void main(String[] args) {

		/*
		 * * Accesso generico
		 * 1 Admin
		 * 2 Supporto Gallo
		 * 3 Agente Magnini
		 * 4 Agente Torre
		 * 5 Utente
		 *
		 */

		final int x = 6;

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
			LOGGER.log(Level.SEVERE, "Errore", e);
		}
	}

}
