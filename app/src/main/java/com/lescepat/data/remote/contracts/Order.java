package com.lescepat.data.remote.contracts;

import com.lescepat.model.DetailsOrder;
import com.lescepat.model.PaidOrder;
import com.lescepat.model.UnpaidOrder;
import com.lescepat.model.WaitingOrder;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Maybe;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public interface Order {
    Maybe<ResponseBody> store(RequestBody object);

    Maybe<JsonObject> getTeacherBlankScheduleList(String teacherId);

    Maybe<JsonObject> uploadTrfFile(String id, String trfFile);
    Maybe<List<UnpaidOrder>> getUnpaidOrderList();

    Maybe<List<WaitingOrder>> getWaitingOrderList();

    Maybe<List<PaidOrder>> getPaidOrderList();

    Maybe<List<DetailsOrder>> getDetailsOrderList(String studyClassId);
}
