package id.co.lesku.data.remote.contracts;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.lesku.model.DetailsOrder;
import id.co.lesku.model.PaidOrder;
import id.co.lesku.model.UnpaidOrder;
import id.co.lesku.model.WaitingOrder;
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
