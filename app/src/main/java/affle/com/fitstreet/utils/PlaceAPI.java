package affle.com.fitstreet.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import affle.com.fitstreet.models.PlaceModel;

/**
 * Created by Affle AppStudioz on 19/1/16.
 */
public class PlaceAPI {
    private static final String TAG = PlaceAPI.class.getSimpleName();
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyA0xHvf_tkThWG5x1Hq0EOzFWEsNBkCkcE";

    public ArrayList<PlaceModel> autocomplete(String input, String type) {
        ArrayList<PlaceModel> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            if (!type.isEmpty())
                sb.append("&type=" + type);
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Log.d(TAG, jsonResults.toString());

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            Logger.e("PlaceApi Result" + jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<PlaceModel>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                PlaceModel model = new PlaceModel();
                JSONObject predictionsJsonObject = predsJsonArray.getJSONObject(i);
                String placeDescription = predictionsJsonObject.getString("description");
                int indexOfSeparator = placeDescription.indexOf(',');
                model.setDescription(placeDescription);
                model.setName(indexOfSeparator > -1 ? placeDescription.substring(0, indexOfSeparator) : placeDescription);
                JSONArray termsArray = predictionsJsonObject.getJSONArray("terms");
                if (termsArray.length() >= 2) {
                    model.setCity(model.getName());
                    model.setState(termsArray.getJSONObject(termsArray.length() - 2).getString("value"));
                    model.setCountry(termsArray.getJSONObject(termsArray.length() - 1).getString("value"));
                } else if (termsArray.length() >= 3) {
                    model.setCity(termsArray.getJSONObject(termsArray.length() - 3).getString("value"));
                    model.setState(termsArray.getJSONObject(termsArray.length() - 2).getString("value"));
                    model.setCountry(termsArray.getJSONObject(termsArray.length() - 1).getString("value"));
                } else {
                    model.setCity(model.getName());
                    model.setState(model.getName());
                    model.setCountry(model.getName());
                }
                resultList.add(model);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }
}