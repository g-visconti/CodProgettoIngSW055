package auth;

/**
 * Servizio per l'autenticazione tramite AWS Cognito.
 */
public interface CognitoAuthService {

	/**
	 * Ottiene un token di accesso per l'API
	 * @return Token di accesso o null in caso di errore
	 */
	String getAccessToken();

	/**
	 * Registra un nuovo utente
	 * @param username Nome utente
	 * @param password Password
	 * @param email Email
	 * @return true se registrazione avvenuta con successo
	 */
	boolean registerUser(String username, String password, String email);

	/**
	 * Autentica con Facebook
	 * @param facebookAccessToken Token di Facebook
	 * @return true se autenticazione riuscita
	 */
	boolean authenticateWithFacebook(String facebookAccessToken);

	/**
	 * Autentica con Google
	 * @param googleAccessToken Token di Google
	 * @return true se autenticazione riuscita
	 */
	boolean authenticateWithGoogle(String googleAccessToken);



	/**
	 * Autenticazione con GitHub oscurata a causa di imperfezioni
	 * lasciata per mostrare il lavoro precedentemente fatto
	 */

	/**
	 * Autentica con GitHub
	 * @param githubAccessToken Token di GitHub
	 * @return true se autenticazione riuscita
	 */
	// boolean authenticateWithGitHub(String githubAccessToken);

	/**
	 * Scambia codice OAuth di GitHub con access token
	 * @param code Codice di autorizzazione
	 * @return true se scambio riuscito
	 */
	// boolean exchangeGitHubCode(String code);
}