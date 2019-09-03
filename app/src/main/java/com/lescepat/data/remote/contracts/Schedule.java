package com.lescepat.data.remote.contracts;

import com.lescepat.model.History;
import com.lescepat.model.UpcomingSchedule;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Maybe;

public interface Schedule {
    Maybe<List<UpcomingSchedule>> getUpcomingScheduleList();

    Maybe<JsonObject> confirmSchedule(int id);

    Maybe<JsonObject> reSchedule(int id);

    Maybe<List<History>> getHistoryList();

    Maybe<JsonObject> rating(int id, float rating, String comment);
}
