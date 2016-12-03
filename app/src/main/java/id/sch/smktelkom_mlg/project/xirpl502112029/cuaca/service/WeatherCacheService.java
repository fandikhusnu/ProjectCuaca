package id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.service;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.R;
import id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.data.Channel;
import id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.listener.WeatherServiceListener;

/**
 *
 * Created by Samudra~ on 20/11/2016.
 */

public class WeatherCacheService {
    private final String CACHED_WEATHER_FILE = "weather.data";
    private Context context;
    private Exception error;

    public WeatherCacheService(Context context) {
        this.context = context;
    }

    public void save(Channel channel) {
        new AsyncTask<Channel, Void, Void>() {
            @Override
            protected Void doInBackground(Channel[] channels) {

                FileOutputStream outputStream;

                try {
                    outputStream = context.openFileOutput(CACHED_WEATHER_FILE, Context.MODE_PRIVATE);
                    outputStream.write(channels[0].toJSON().toString().getBytes());
                    outputStream.close();

                } catch (IOException e) {
                    // TODO: file save operation failed
                }

                return null;
            }
        }.execute(channel);
    }

    public void load(final WeatherServiceListener listener) {

        new AsyncTask<WeatherServiceListener, Void, Channel>() {
            private WeatherServiceListener weatherListener;

            @Override
            protected Channel doInBackground(WeatherServiceListener[] serviceListeners) {
                weatherListener = serviceListeners[0];
                try {
                    FileInputStream inputStream = context.openFileInput(CACHED_WEATHER_FILE);

                    StringBuilder cache = new StringBuilder();
                    int content;
                    while ((content = inputStream.read()) != -1) {
                        cache.append((char) content);
                    }

                    inputStream.close();

                    JSONObject jsonCache = new JSONObject(cache.toString());

                    Channel channel = new Channel();
                    channel.populate(jsonCache);

                    return channel;

                } catch (FileNotFoundException e) {
                    error = new CacheException(context.getString(R.string.cache_exception));
                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Channel channel) {
                if (channel == null && error != null) {
                    weatherListener.serviceFailure(error);
                } else {
                    weatherListener.serviceSuccess(channel);
                }
            }
        }.execute(listener);
    }

    public class CacheException extends Exception {
        public CacheException(String detailMessage) {
            super(detailMessage);
        }
    }
}
