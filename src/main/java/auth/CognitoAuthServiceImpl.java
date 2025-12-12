package auth;

import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import auth.config.AuthConfig;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CognitoAuthServiceImpl implements CognitoAuthService {

	private final AuthConfig config;
	private final OkHttpClient httpClient;

	public CognitoAuthServiceImpl() {
		config = new AuthConfig();
		httpClient = new OkHttpClient.Builder()
				.connectTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS)
				.build();
	}

	public CognitoAuthServiceImpl(AuthConfig config) {
		this.config = config;
		httpClient = new OkHttpClient.Builder()
				.connectTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS)
				.build();
	}

	@Override
	public String getAccessToken() {
		if (!config.isValid()) {
			System.err.println("Configurazione Cognito non valida");
			return null;
		}

		String clientSecret = config.getClientSecret();
		if (clientSecret == null || clientSecret.isEmpty()) {
			System.err.println("Client secret non configurato. Imposta COGNITO_CLIENT_SECRET");
			return null;
		}

		RequestBody formBody = new FormBody.Builder()
				.add("client_id", config.getClientId())
				.add("client_secret", clientSecret)
				.add("scope", config.getScope())
				.add("grant_type", "client_credentials")
				.build();

		Request request = new Request.Builder()
				.url("https://eu-west-1xrobh5glh.auth.eu-west-1.amazoncognito.com/oauth2/token")
				.post(formBody)
				.header("Content-Type", "application/x-www-form-urlencoded")
				.build();

		try (Response response = httpClient.newCall(request).execute()) {
			if (response.isSuccessful() && response.body() != null) {
				String responseBody = response.body().string();
				JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
				return jsonObject.get("access_token").getAsString();
			} else {
				System.err.println("Errore nella richiesta del token: " + response.code());
				if (response.body() != null) {
					System.err.println("Response body: " + response.body().string());
				}
				return null;
			}
		} catch (Exception e) {
			System.err.println("Errore durante la richiesta del token: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean registerUser(String username, String password, String email) {
		JsonObject jsonRequest = new JsonObject();
		jsonRequest.addProperty("ClientId", config.getClientId());
		jsonRequest.addProperty("Username", username);
		jsonRequest.addProperty("Password", password);

		JsonArray userAttributes = new JsonArray();
		JsonObject emailAttr = new JsonObject();
		emailAttr.addProperty("Name", "email");
		emailAttr.addProperty("Value", email);
		userAttributes.add(emailAttr);

		jsonRequest.add("UserAttributes", userAttributes);

		RequestBody body = RequestBody.create(
				jsonRequest.toString(),
				MediaType.parse("application/json")
				);

		Request request = new Request.Builder()
				.url("https://cognito-idp." + config.getRegion() + ".amazonaws.com/")
				.post(body)
				.header("Content-Type", "application/json")
				.header("X-Amz-Target", "AWSCognitoIdentityProviderService.SignUp")
				.header("X-Amz-Region", config.getRegion())
				.build();

		try (Response response = httpClient.newCall(request).execute()) {
			if (response.isSuccessful()) {
				System.out.println("Registrazione avvenuta con successo");
				return true;
			} else {
				String errorBody = response.body() != null ? response.body().string() : "Nessun body";
				System.err.println("Errore durante la registrazione: " + response.code() + " - " + errorBody);
				return false;
			}
		} catch (Exception e) {
			System.err.println("Errore durante la registrazione: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean authenticateWithFacebook(String facebookAccessToken) {
		JsonObject jsonRequest = new JsonObject();
		jsonRequest.addProperty("IdentityPoolId", config.getIdentityPoolId());
		jsonRequest.addProperty("Logins", "graph.facebook.com:" + facebookAccessToken);

		RequestBody body = RequestBody.create(
				jsonRequest.toString(),
				MediaType.parse("application/json")
				);

		Request request = new Request.Builder()
				.url("https://cognito-identity.eu-west-1.amazonaws.com/")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("X-Amz-Target", "AWSCognitoIdentityService.GetId")
				.build();

		try (Response response = httpClient.newCall(request).execute()) {
			if (response.isSuccessful()) {
				if (response.body() != null) {
					response.body().string();
				}
				System.out.println("Autenticazione Facebook avvenuta con successo");
				return true;
			} else {
				System.err.println("Errore durante l'autenticazione con Facebook: " + response.code());
				return false;
			}
		} catch (Exception e) {
			System.err.println("Errore durante l'autenticazione Facebook: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean authenticateWithGoogle(String googleAccessToken) {
		JsonObject jsonRequest = new JsonObject();
		jsonRequest.addProperty("IdentityPoolId", config.getIdentityPoolId());
		jsonRequest.addProperty("Logins", "accounts.google.com:" + googleAccessToken);

		RequestBody body = RequestBody.create(
				jsonRequest.toString(),
				MediaType.parse("application/json")
				);

		Request request = new Request.Builder()
				.url("https://cognito-identity.eu-west-1.amazonaws.com/")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("X-Amz-Target", "AWSCognitoIdentityService.GetId")
				.build();

		try (Response response = httpClient.newCall(request).execute()) {
			if (response.isSuccessful()) {
				if (response.body() != null) {
					response.body().string();
				}
				System.out.println("Autenticazione Google avvenuta con successo");
				return true;
			} else {
				System.err.println("Errore durante l'autenticazione con Google: " + response.code());
				return false;
			}
		} catch (Exception e) {
			System.err.println("Errore durante l'autenticazione Google: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Autenticazione con GitHub oscurata a causa di imperfezioni
	 * lasciata per mostrare il lavoro precedentemente fatto
	 */

	/*
	@Override
	public boolean authenticateWithGitHub(String githubAccessToken) {
		JsonObject jsonRequest = new JsonObject();
		jsonRequest.addProperty("IdentityPoolId", config.getIdentityPoolId());
		jsonRequest.addProperty("Logins", "github.com:" + githubAccessToken);

		RequestBody body = RequestBody.create(
				jsonRequest.toString(),
				MediaType.parse("application/json")
				);

		Request request = new Request.Builder()
				.url("https://cognito-identity.eu-west-1.amazonaws.com/")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("X-Amz-Target", "AWSCognitoIdentityService.GetId")
				.build();

		try (Response response = httpClient.newCall(request).execute()) {
			if (response.isSuccessful()) {
				if (response.body() != null) {
					response.body().string();
				}
				System.out.println("Autenticazione GitHub avvenuta con successo");
				return true;
			} else {
				System.err.println("Errore durante l'autenticazione con GitHub: " + response.code());
				return false;
			}
		} catch (Exception e) {
			System.err.println("Errore durante l'autenticazione GitHub: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean exchangeGitHubCode(String code) {
		HttpUrl tokenUrl = HttpUrl.parse("https://github.com/login/oauth/access_token").newBuilder()
				.addQueryParameter("client_id", "Ov23liiPkBEnvPNXer7V")
				.addQueryParameter("client_secret", "c1b38f9cd22ebec732466ac6c6cd4737c81c07f1")
				.addQueryParameter("code", code)
				.addQueryParameter("redirect_uri", "https://g-visconti.github.io/callback-github/githubcallback")
				.build();

		Request tokenRequest = new Request.Builder()
				.url(tokenUrl)
				.header("Accept", "application/json")
				.post(RequestBody.create("", null))
				.build();

		try (Response tokenResponse = httpClient.newCall(tokenRequest).execute()) {
			if (!tokenResponse.isSuccessful() || tokenResponse.body() == null) {
				System.err.println("Errore scambio code/token GitHub: " + tokenResponse.code());
				return false;
			}

			String json = tokenResponse.body().string();
			JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

			if (obj.has("access_token")) {
				String accessToken = obj.get("access_token").getAsString();
				return authenticateWithGitHub(accessToken);
			} else {
				System.err.println("Access token non trovato nella risposta GitHub");
				return false;
			}

		} catch (Exception e) {
			System.err.println("Errore durante lo scambio code GitHub: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}*/
}