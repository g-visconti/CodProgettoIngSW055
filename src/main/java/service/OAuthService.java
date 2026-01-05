package service;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import auth.CognitoAuthService;
import auth.CognitoAuthServiceImpl;
import model.entity.Account;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OAuthService {

	private final CognitoAuthService cognitoAuthService;
	private final OkHttpClient httpClient;

	public OAuthService() {
		cognitoAuthService = new CognitoAuthServiceImpl();
		httpClient = new OkHttpClient();
	}

	public OAuthService(CognitoAuthService cognitoAuthService) {
		this.cognitoAuthService = cognitoAuthService;
		httpClient = new OkHttpClient();
	}

	public Account processOAuthLogin(String token, String provider) {
		if (token == null || token.isEmpty()) {
			return null;
		}

		// 1. Autentica con Cognito
		final boolean cognitoSuccess = authenticateWithCognito(token, provider);
		if (!cognitoSuccess) {
			return null;
		}

		// 2. Recupera i dati dell'utente dal provider
		return getUserInfoFromProvider(token, provider);
	}

	public String extractEmailFromToken(String token, String provider) {
		final Account account = getUserInfoFromProvider(token, provider);
		return account != null ? account.getEmail() : null;
	}

	private boolean authenticateWithCognito(String token, String provider) {
		return switch (provider.toLowerCase()) {
		case "facebook" -> cognitoAuthService.authenticateWithFacebook(token);
		case "google" -> cognitoAuthService.authenticateWithGoogle(token);
		default -> throw new IllegalArgumentException("Provider non supportato: " + provider);
		};
	}

	private Account getUserInfoFromProvider(String token, String provider) {
		return switch (provider.toLowerCase()) {
		case "facebook" -> getFacebookAccount(token);
		case "google" -> getGoogleAccount(token);
		default -> throw new IllegalArgumentException("Provider non supportato: " + provider);
		};
	}

	private Account getFacebookAccount(String facebookAccessToken) {
		final String url = "https://graph.facebook.com/me?fields=email,first_name,last_name&access_token="
				+ facebookAccessToken;
		final Request request = new Request.Builder().url(url).get().build();

		try (Response response = httpClient.newCall(request).execute()) {
			if (response.isSuccessful() && response.body() != null) {
				final String responseBody = response.body().string();
				final JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

				final String email = jsonObject.has("email") ? jsonObject.get("email").getAsString() : null;
				final String ruolo = "Cliente";
				final Account account = new Account(email, facebookAccessToken, ruolo);

				// Imposta nome e cognome se presenti
				if (jsonObject.has("first_name")) {
					account.setNome(jsonObject.get("first_name").getAsString());
				}
				if (jsonObject.has("last_name")) {
					account.setCognome(jsonObject.get("last_name").getAsString());
				}

				return account;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Account getGoogleAccount(String googleAccessToken) {
		final Request request = new Request.Builder().url("https://www.googleapis.com/oauth2/v3/userinfo")
				.addHeader("Authorization", "Bearer " + googleAccessToken).get().build();

		try (Response response = httpClient.newCall(request).execute()) {
			if (response.isSuccessful() && response.body() != null) {
				final String responseBody = response.body().string();
				final JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

				final String email = jsonObject.has("email") ? jsonObject.get("email").getAsString() : null;
				final String ruolo = "Cliente";
				final Account account = new Account(email, googleAccessToken, ruolo);

				if (jsonObject.has("given_name")) {
					account.setNome(jsonObject.get("given_name").getAsString());
				}
				if (jsonObject.has("family_name")) {
					account.setCognome(jsonObject.get("family_name").getAsString());
				}

				return account;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}