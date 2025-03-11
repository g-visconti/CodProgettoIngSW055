package model;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.FormBody;

public class App {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        RequestBody formBody = new FormBody.Builder()
            .add("client_id", "cuvf5rcnt2n3riofl9os0ja63")
            .add("client_secret", "1aaa1n2qa0b1kif0vv99hcm9qetl0s1o56mt1tqoc2255gc2n1bl")
            .add("scope", "default-m2m-resource-server-2eb6f9/read")
            .add("grant_type", "client_credentials")
            .build();
            
            
        Request request = new Request.Builder()
            .url("https://eu-west-1xrobh5glh.auth.eu-west-1.amazoncognito.com/oauth2/token")
            .post(formBody)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build();
            
            
        try (Response response = client.newCall(request).execute()) {
        	System.out.println("Response code: " + response.code());
        	
            if (response.body() != null) {
                System.out.println(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

