package id.co.lesku.data.remote.contracts;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.lesku.model.History;
import id.co.lesku.model.UpcomingSchedule;
import io.reactivex.Maybe;

public interface Schedule {
    Maybe<List<UpcomingSchedule>> getUpcomingScheduleList();

    Maybe<JsonObject> confirmSchedule(int id);

    Maybe<JsonObject> reSchedule(int id);

    Maybe<List<History>> getHistoryList();

    Maybe<JsonObject> rating(int id, float rating, String comment);
}
