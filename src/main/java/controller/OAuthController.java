package controller;

import javax.swing.JOptionPane;

import model.entity.Account;
import service.OAuthService;

public class OAuthController {

	private final OAuthService oauthService;
	private final AccessController accessController;

	public OAuthController() {
		oauthService = new OAuthService();
		accessController = new AccessController();
	}

	public String handleOAuthLogin(String token, String provider) {
		if (token == null || token.isEmpty()) {
			showMessage("Token non valido", "Errore", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		try {
			// 1. Processa l'autenticazione OAuth
			final Account account = oauthService.processOAuthLogin(token, provider);

			if (account == null) {
				showMessage("Autenticazione con " + provider + " fallita", "Errore", JOptionPane.ERROR_MESSAGE);
				return null;
			}

			final String email = account.getEmail();
			if (email == null || email.isEmpty()) {
				showMessage("Impossibile ottenere l'email da " + provider, "Errore", JOptionPane.ERROR_MESSAGE);
				return null;
			}

			// 2. Registra o aggiorna l'utente nel database
			// Il ruolo è sempre "Cliente" per OAuth
			final String ruolo = "Cliente";

			try {
				accessController.registraNuovoUtente(email, token, ruolo);
			} catch (Exception e) {
				// Se l'utente esiste già, potrebbe essere un login normale
				System.out.println("Utente già registrato o errore di registrazione: " + e.getMessage());
				// Proseguiamo comunque con il login
			}

			// 3. Log dell'accesso
			logAccesso(email, provider);

			return email;

		} catch (Exception e) {
			e.printStackTrace();
			showMessage("Errore durante l'autenticazione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	public boolean validateToken(String token, String provider) {
		try {
			final String email = oauthService.extractEmailFromToken(token, provider);
			return email != null && !email.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	public Account getAccountFromToken(String token, String provider) {
		return oauthService.processOAuthLogin(token, provider);
	}

	private void showMessage(String message, String title, int messageType) {
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}

	private void logAccesso(String email, String provider) {
		System.out.println("[LOG] Accesso OAuth: " + email + " via " + provider);
	}
}