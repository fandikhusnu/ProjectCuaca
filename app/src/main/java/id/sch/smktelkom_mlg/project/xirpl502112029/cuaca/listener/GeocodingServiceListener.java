package id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.listener;

import id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.data.LocationResult;

/**
 * Created by Samudra~ on 20/11/2016.
 */

public interface GeocodingServiceListener {
    void geocodeSuccess(LocationResult location);

    void geocodeFailure(Exception exception);
}
