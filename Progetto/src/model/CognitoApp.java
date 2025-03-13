package model;

import okhttp3.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CognitoApp {
    private static final String CLIENT_ID = "cuvf5rcnt2n3riofl9os0ja63";
    private static final String CLIENT_SECRET = "1aaa1n2qa0b1kif0vv99hcm9qetl0s1o56mt1tqoc2255gc2n1bl";
    private static final String TOKEN_URL = "https://eu-west-1xrobh5glh.auth.eu-west-1.amazoncognito.com/oauth2/token";
    private static final String SCOPE = "default-m2m-resource-server-2eb6f9/read";
    private static final String GRANT_TYPE = "client_credentials";

    public static String getAccessToken() {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
            .add("client_id", CLIENT_ID)
            .add("client_secret", CLIENT_SECRET)
            .add("scope", SCOPE)
            .add("grant_type", "client_credentials")
            .build();

        Request request = new Request.Builder()
            .url(TOKEN_URL)
            .post(formBody)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build();

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
}
