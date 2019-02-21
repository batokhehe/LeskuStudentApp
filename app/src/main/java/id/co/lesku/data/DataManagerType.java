package id.co.lesku.data;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.lesku.model.DetailsOrder;
import id.co.lesku.model.History;
import id.co.lesku.model.PaidOrder;
import id.co.lesku.model.Product;
import id.co.lesku.model.StudyLevel;
import id.co.lesku.model.Subject;
import id.co.lesku.model.TeacherOrder;
import id.co.lesku.model.UnpaidOrder;
import id.co.lesku.model.UpcomingSchedule;
import id.co.lesku.model.User;
import id.co.lesku.model.WaitingOrder;
import io.reactivex.Maybe;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

//import id.co.flipbox.mvvmstarter.models.User;

public interface DataManagerType
{
    /**
     * IMPORTANT !
     * ALL METHODS INSIDE DATAMANAGER MUST BE DEFINED HERE.
     * GROUP THE METHODS BASED ON THE MODULE.
     */

    Maybe<JsonObject> login (String id, String password, String regid);

    Maybe<JsonObject> register(String fname, String lname, String email, int studylevelid, String parentname, String schoolname, String address, String phone_number, String password, String cpassword);

    Maybe<JsonObject> updateAccount(String name, String email, String address, String phoneNumber, String encodedImage);

    void logout();

    Maybe<JsonObject> forgotPassword (String id);

    Maybe<List<User>> getUserList();

    Maybe<User> getUser(Integer id);

    //HOME
    Maybe<List<Product>> getProductList();

    Maybe<List<StudyLevel>> getStudyLevelList();

    Maybe<List<Subject>> getSubject();

    //TEACHER ORDER
    Maybe<List<TeacherOrder>> getTeacherOrderList();

    //ORDER
    Maybe<ResponseBody> addOrderClass(RequestBody object);

    //UNPAID ORDER
    Maybe<List<UnpaidOrder>> getUnpaidOrderList();

    //WAITING ORDER
    Maybe<List<WaitingOrder>> getWaitingOrderList();

    //PAID ORDER
    Maybe<List<PaidOrder>> getPaidOrderList();

    //ORDER DETAILS
    Maybe<List<DetailsOrder>> getDetailsOrderList(String studyClassId);

    Maybe<JsonObject> getTeacherBlankScheduleList(String teacherId);

    Maybe<JsonObject> uploadTrfFile(String id, String trfFile);

    //UPCOMING SCHEDULE
    Maybe<List<UpcomingSchedule>> getUpcomingScheduleList();

    Maybe<JsonObject> confirmSchedule(int id);

    Maybe<JsonObject> reSchedule(int id);

    Maybe<List<History>> getHistoryList();

    Maybe<JsonObject> rating(int id, float rating, String comment);
}
