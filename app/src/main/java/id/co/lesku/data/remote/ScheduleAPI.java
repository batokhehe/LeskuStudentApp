package id.co.lesku.data.remote;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.lesku.data.remote.contracts.Schedule;
import id.co.lesku.model.History;
import id.co.lesku.model.UpcomingSchedule;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class ScheduleAPI extends BaseAPI implements Schedule {

    @Override
    public Maybe<List<UpcomingSchedule>> getUpcomingScheduleList() {
        return app.mAPIService.getUpcomingScheduleList().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<JsonObject> confirmSchedule(int id) {
        return app.mAPIService.confirmSchedule(id).retry(1).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<JsonObject> reSchedule(int id) {
        return app.mAPIService.reSchedule(id).retry(1).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<History>> getHistoryList() {
        return app.mAPIService.getHistoryList().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<JsonObject> rating(int id, float rating, String comment) {
        return app.mAPIService.rating(id, rating, comment).retry(1).subscribeOn(Schedulers.io());
    }
}
