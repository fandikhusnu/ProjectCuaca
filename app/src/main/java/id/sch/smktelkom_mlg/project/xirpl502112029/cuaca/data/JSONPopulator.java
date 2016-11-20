package id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.data;

import org.json.JSONObject;

/**
 * Created by user on 11/20/2016.
 */

public interface JSONPopulator {
    void populate(JSONObject data);

    JSONObject toJSON();
}
