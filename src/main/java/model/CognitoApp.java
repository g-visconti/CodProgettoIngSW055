package model;

import okhttp3.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CognitoApp {
	private static final String REGION = "eu-west-1";
	private static final String SIGNUP_URL = "https://eu-west-1xrobh5glh.auth.eu-west-1.amazoncognito.com/";
	private static final String CLIENT_ID = "cuvf5rcnt2n3riofl9os0ja63";
	private static final String CLIENT_SECRET = "1aaa1n2qa0b1kif0vv99hcm9qetl0s1o56mt1tqoc2255gc2n1bl";
	private static final String TOKEN_URL = "https://eu-west-1xrobh5glh.auth.eu-west-1.amazoncognito.com/oauth2/token";
	private static final String SCOPE = "default-m2m-resource-server-2eb6f9/read";

	public static String getAccessToken() {
		OkHttpClient client = new OkHttpClient();

		RequestBody formBody = new FormBody.Builder().add("client_id", CLIENT_ID).add("client_secret", CLIENT_SECRET)
				.add("scope", SCOPE).add("grant_type", "client_credentials").build();

		Request request = new Request.Builder().url(TOKEN_URL).post(formBody)
				.header("Content-Type", "application/x-www-form-urlencoded").build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful() && response.body() != null) {
				String responseBody = response.body().string();

				// Estrai l'access token dalla risposta JSON
				JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
				return jsonObject.get("access_token").getAsString();
			} else {
				System.out.println("Errore nella richiesta del token: " + response.code());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean registerUser(String username, String password, String email) {
		OkHttpClient client = new OkHttpClient();

		JsonObject jsonRequest = new JsonObject();
		jsonRequest.addProperty("ClientId", CLIENT_ID);
		jsonRequest.addProperty("Username", username);
		jsonRequest.addProperty("Password", password);

		JsonObject userAttributes = new JsonObject();
		userAttributes.addProperty("Name", "email");
		userAttributes.addProperty("Value", email);

		jsonRequest.add("UserAttributes", userAttributes);

		RequestBody body = RequestBody.create(jsonRequest.toString(), MediaType.parse("application/json"));

		Request request = new Request.Builder().url(SIGNUP_URL).post(body).header("Content-Type", "application/json")
				.header("X-Amz-Target", "AWSCognitoIdentityProviderService.SignUp").header("X-Amz-Region", REGION)
				.build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				System.out.println("Registrazione avvenuta con successo");
				return true;
			} else {
				System.out.println("Errore durante la registrazione: " + response.body().string());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean authenticateWithFacebook(String facebookAccessToken) {
		OkHttpClient client = new OkHttpClient();

		// Prepara la richiesta per federare il Facebook Access Token con Cognito
		JsonObject jsonRequest = new JsonObject();
		jsonRequest.addProperty("IdentityPoolId", "eu-west-1:710dcdd9-c2f3-4039-baf0-7768cb3d4c02");
		jsonRequest.addProperty("Logins", "graph.facebook.com:" + facebookAccessToken);

		RequestBody body = RequestBody.create(jsonRequest.toString(), MediaType.parse("application/json"));

		Request request = new Request.Builder().url("https://cognito-identity.eu-west-1.amazonaws.com/").post(body)
				.addHeader("Content-Type", "application/json").build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				String responseBody = response.body().string();

				System.out.println("Autenticazione avvenuta con successo, risposta: " + responseBody);

				return true;
			} else {
				System.out.println("Errore durante l'autenticazione con Facebook: " + response.body().string());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean authenticateWithGoogle(String googleAccessToken) {
		OkHttpClient client = new OkHttpClient();

		// Prepara la richiesta per federare il Google Access Token con Cognito
		JsonObject jsonRequest = new JsonObject();
		jsonRequest.addProperty("IdentityPoolId", "eu-west-1:710dcdd9-c2f3-4039-baf0-7768cb3d4c02"); // Sostituisci con
																										// il tuo
																										// Identity Pool
																										// ID
		jsonRequest.addProperty("Logins", "accounts.google.com:" + googleAccessToken); // Logins per Google

		RequestBody body = RequestBody.create(jsonRequest.toString(), MediaType.parse("application/json"));

		Request request = new Request.Builder().url("https://cognito-identity.eu-west-1.amazonaws.com/") // Cognito URL
				.post(body).addHeader("Content-Type", "application/json").build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				String responseBody = response.body().string();
				System.out.println("Autenticazione avvenuta con successo, risposta: " + responseBody);
				return true; // Successo nell'autenticazione
			} else {
				System.out.println("Errore durante l'autenticazione con Google: " + response.body().string());
				return false; // Fallimento nell'autenticazione
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Gestione degli errori
		}
	}

	public static boolean authenticateWithGitHub(String code) {
		OkHttpClient client = new OkHttpClient();

		HttpUrl tokenUrl = HttpUrl.parse("https://github.com/login/oauth/access_token").newBuilder()
				.addQueryParameter("client_id", "Ov23liiPkBEnvPNXer7V")
				.addQueryParameter("client_secret", "c1b38f9cd22ebec732466ac6c6cd4737c81c07f1")
				.addQueryParameter("code", code)
				.addQueryParameter("redirect_uri", "https://g-visconti.github.io/callback-github/githubcallback")
				.build();

		Request tokenRequest = new Request.Builder().url(tokenUrl).header("Accept", "application/json")
				.post(RequestBody.create("", null)).build();

		try (Response tokenResponse = client.newCall(tokenRequest).execute()) {
			if (!tokenResponse.isSuccessful()) {
				System.out.println("Errore scambio code/token: " + tokenResponse.body().string());
				return false;
			}

			String json = tokenResponse.body().string();
			JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
			String accessToken = obj.get("access_token").getAsString();

			// 🔁 Usa ora il tuo metodo esistente:
			return authenticateWithGitHub(accessToken);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
