package id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.service;

import android.location.Location;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.data.LocationResult;
import id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.listener.GeocodingServiceListener;

/**
 * Menentukan lokasi dari inputan [ada Setting Manual Lat & Long
 *
 * Created by Samudra~ on 20/11/2016.
 */

public class GoogleMapsGeocodingService {
    private static final String API_KEY = "";
    private GeocodingServiceListener listener;
    private Exception error;

    public GoogleMapsGeocodingService(GeocodingServiceListener listener) {
        this.listener = listener;
    }

    public void refreshLocation(Location location) {
        new AsyncTask<Location, Void, LocationResult>() {
            @Override
            protected LocationResult doInBackground(Location... locations) {

                Location location = locations[0];

                String endpoint = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s", location.getLatitude(), location.getLongitude(), API_KEY);

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    JSONObject data = new JSONObject(result.toString());

                    JSONArray results = data.optJSONArray("results");

                    if (results.length() == 0) {
                        error = new ReverseGeolocationException("Could not reverse geocode " + location.getLatitude() + ", " + location.getLongitude());

                        return null;
                    }

                    LocationResult locationResult = new LocationResult();
                    locationResult.populate(results.optJSONObject(0));

                    return locationResult;

                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(LocationResult location) {

                if (location == null && error != null) {
                    listener.geocodeFailure(error);
                } else {
                    listener.geocodeSuccess(location);
                }

            }

        }.execute(location);
    }

    private class ReverseGeolocationException extends Exception {
        public ReverseGeolocationException(String detailMessage) {
            super(detailMessage);
        }
    }
}

