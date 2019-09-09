package com.lescepat.data.remote.retrofit;

import com.lescepat.model.DetailsOrder;
import com.lescepat.model.History;
import com.lescepat.model.PaidOrder;
import com.lescepat.model.Product;
import com.lescepat.model.StudyLevel;
import com.lescepat.model.Subject;
import com.lescepat.model.TeacherOrder;
import com.lescepat.model.UnpaidOrder;
import com.lescepat.model.UpcomingSchedule;
import com.lescepat.model.User;
import com.lescepat.model.WaitingOrder;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Maybe;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface LesCepatAPIService
{
    @FormUrlEncoded
    @GET
        //dynamic URL
    Maybe<JsonObject> dynamicRequest (@Url String url);

    /*
    * below are dummy URLs. Please change it into your API endpoints
    * TODO: replace below URLs with your own
    */

    @FormUrlEncoded
    @POST("auth/login")
    Maybe<JsonObject> login (
            @Field("email") String email,
            @Field("password") String password,
            @Field("app_firebase_id") String regid
    );

    @FormUrlEncoded
    @POST("auth/register")
    Maybe<JsonObject> register(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("email") String email,
            @Field("studylevelid") int studylevelid,
            @Field("parent_name") String parent_name,
            @Field("school_name") String school_name,
            @Field("address") String address,
            @Field("phone_number") String phone_number,
            @Field("password") String password,
            @Field("cpassword") String cpassword
    );

    @GET ("users/{id}")
    Maybe<JsonObject> forgotPassword (@Path ("id") String id);

    @FormUrlEncoded
    @POST("student/update_account")
    Maybe<JsonObject> updateAccount (
            @Field("name") String name,
            @Field("email") String email,
            @Field("address") String address,
            @Field("phone_number") String phoneNumber,
            @Field("encoded_image") String encodedImage
    );

    @GET ("users")
    Maybe<List<User>> getUsers ();

    @GET ("users/{id}")
    Maybe<User> getUser (@Path ("id") String id);

    //Home

    @GET ("study_levels")
    Maybe<List<StudyLevel>> getStudyLevels ();

    @FormUrlEncoded
    @POST ("subjects")
    Maybe<List<Subject>> getSubjects (
            @Field("study_level_id") int studyLevelId
    );

    @GET ("products")
    Maybe<List<Product>> getProducts ();

    //Order
    @FormUrlEncoded
    @POST ("order/teachers")
    Maybe<List<TeacherOrder>> getTeachersOrder(
            @Field("subject_id") Integer subjectId,
            @Field("study_level_id") Integer studyLevel,
            @Field("schedule") String schedule
    );

    @POST ("order/add")
    Maybe<ResponseBody> addOrderClass (@Body RequestBody object);

    @GET ("order/unpaid")
    Maybe<List<UnpaidOrder>> getUnpaidOrderList ();

    @GET ("order/waiting")
    Maybe<List<WaitingOrder>> getWaitingOrderList ();

    @GET ("order/paid")
    Maybe<List<PaidOrder>> getPaidOrderList ();

    @FormUrlEncoded
    @POST ("order/detail")
    Maybe<List<DetailsOrder>> getDetailsOrder (
            @Field("study_class_id") String studyclassid
    );

    @FormUrlEncoded
    @POST ("order/teacher_blank_schedules")
    Maybe<JsonObject> getTeacherBlankSchedule (
            @Field("teacher_id") String teacherid
    );

    @FormUrlEncoded
    @POST ("order/upload_trf_file")
    Maybe<JsonObject> uploadTrfFile (
            @Field("id") String id,
            @Field("trf_file") String trfFile
    );

    //Schedule
    @GET ("schedule/upcoming")
    Maybe<List<UpcomingSchedule>> getUpcomingScheduleList ();

    @FormUrlEncoded
    @POST ("schedule/confirm_schedule")
    Maybe<JsonObject> confirmSchedule (
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST ("schedule/reschedule")
    Maybe<JsonObject> reSchedule (
            @Field("id") int id
    );

    //History
    @GET ("history")
    Maybe<List<History>> getHistoryList ();

    @FormUrlEncoded
    @POST ("history/rating")
    Maybe<JsonObject> rating (
            @Field("id") int id,
            @Field("rating") float rating,
            @Field("comment") String comment
    );
}
