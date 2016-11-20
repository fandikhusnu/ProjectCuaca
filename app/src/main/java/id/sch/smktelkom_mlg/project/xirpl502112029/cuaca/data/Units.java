package id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.data;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 11/20/2016.
 */

public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("temperature", temperature);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
