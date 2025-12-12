package auth.config;

/**
 * Configurazione per l'autenticazione AWS Cognito.
 * Le credenziali vengono caricate da variabili d'ambiente o file di properties.
 */
public class AuthConfig {

	private String region;
	private String clientId;
	private String clientSecret;
	private String tokenUrl;
	private String signupUrl;
	private String scope;
	private String identityPoolId;

	public AuthConfig() {
		// Carica valori da variabili d'ambiente (MODO SICURO)
		region = System.getenv("COGNITO_REGION");
		clientId = System.getenv("COGNITO_CLIENT_ID");
		clientSecret = System.getenv("COGNITO_CLIENT_SECRET");
		identityPoolId = System.getenv("COGNITO_IDENTITY_POOL_ID");

		// Fallback a valori di default (SOLO PER SVILUPPO)
		if (region == null) {
			region = "eu-west-1";
		}
		if (clientId == null) {
			clientId = "cuvf5rcnt2n3riofl9os0ja63";
		}
		if (identityPoolId == null) {
			identityPoolId = "eu-west-1:710dcdd9-c2f3-4039-baf0-7768cb3d4c02";
		}

		// Costruisci URL dinamicamente
		tokenUrl = String.format("https://cognito-idp.%s.amazonaws.com/", region);
		signupUrl = String.format("https://%s.auth.%s.amazoncognito.com/signup", "eu-west-1xrobh5glh", region);
		scope = "default-m2m-resource-server-2eb6f9/read";
	}

	// Getters
	public String getRegion() { return region; }
	public String getClientId() { return clientId; }
	public String getClientSecret() { return clientSecret; }
	public String getTokenUrl() { return tokenUrl; }
	public String getSignupUrl() { return signupUrl; }
	public String getScope() { return scope; }
	public String getIdentityPoolId() { return identityPoolId; }

	// Validazione
	public boolean isValid() {
		return region != null && !region.isEmpty() &&
				clientId != null && !clientId.isEmpty() &&
				tokenUrl != null && !tokenUrl.isEmpty();
	}
}