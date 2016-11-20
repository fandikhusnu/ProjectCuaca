package id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by microsoft on 11/20/2016.
 */

public class LocationResult implements JSONPopulator {

    private String address;

    public String getAddress() {
        return address;
    }

    @Override
    public void populate(JSONObject data) {
        address = data.optString("formatted_address");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("formatted_address", address);
        } catch (JSONException e) {
        }

        return data;
    }
}
