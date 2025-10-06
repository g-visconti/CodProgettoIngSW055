package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class ControllerImmobile extends Controller {

	private final String apiKey;

	public ControllerImmobile(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getLuoghiVicini(String indirizzo) {
		try {
			// 1. Ottieni coordinate
			String geocodeUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="
					+ URLEncoder.encode(indirizzo, "UTF-8") + "&key=" + apiKey;

			String geocodeResponse = getHttpResponse(geocodeUrl);
			JSONObject geoJson = new JSONObject(geocodeResponse);
			JSONObject location = geoJson.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location");

			double lat = location.getDouble("lat");
			double lng = location.getDouble("lng");

			// 2. Cerca luoghi vicini
			String[] tipi = { "park", "school", "bus_station" };
			StringBuilder vicini = new StringBuilder("Vicino a: ");

			for (String tipo : tipi) {
				String placesUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + ","
						+ lng + "&radius=500&type=" + tipo + "&key=" + apiKey;

				String placesResponse = getHttpResponse(placesUrl);
				JSONObject placesJson = new JSONObject(placesResponse);
				JSONArray results = placesJson.getJSONArray("results");

				if (results.length() > 0) {
					JSONObject luogo = results.getJSONObject(0);
					String nome = luogo.getString("name");
					vicini.append(nome).append(", ");
				}
			}

			if (vicini.toString().endsWith(", ")) {
				vicini.setLength(vicini.length() - 2); // Rimuove ultima virgola
			}

			return vicini.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "Errore durante la ricerca dei luoghi vicini.";
		}
	}

	private String getHttpResponse(String urlStr) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
		conn.setRequestMethod("GET");

		try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			String line;
			StringBuilder content = new StringBuilder();
			while ((line = in.readLine()) != null) {
				content.append(line);
			}
			return content.toString();
		}
	}
}
