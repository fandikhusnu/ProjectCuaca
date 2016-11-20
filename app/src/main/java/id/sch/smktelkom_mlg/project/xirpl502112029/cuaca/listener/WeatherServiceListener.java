package id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.listener;

import id.sch.smktelkom_mlg.project.xirpl502112029.cuaca.data.Channel;

/**
 * Created by microsoft on 11/20/2016.
 */

public interface WeatherServiceListener {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
