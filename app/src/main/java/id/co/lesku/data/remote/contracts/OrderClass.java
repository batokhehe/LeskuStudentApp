package id.co.lesku.data.remote.contracts;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.lesku.models.DetailsOrder;
import id.co.lesku.models.TeacherSchedule;
import id.co.lesku.models.UnpaidOrder;
import io.reactivex.Maybe;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public interface OrderClass {
    Maybe<ResponseBody> store(RequestBody object);

    Maybe<List<TeacherSchedule>> getTeacherBlankScheduleList(String teacherId);

    Maybe<JsonObject> uploadTrfFile(String id, String trfFile);
    Maybe<List<UnpaidOrder>> getUnpaidOrderList();
    Maybe<List<DetailsOrder>> getDetailsOrderList(String studyClassId);
}
