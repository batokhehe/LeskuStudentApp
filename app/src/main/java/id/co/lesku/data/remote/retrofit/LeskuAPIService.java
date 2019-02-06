package id.co.lesku.data.remote.retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.lesku.model.DetailsOrder;
import id.co.lesku.model.Product;
import id.co.lesku.model.StudyLevel;
import id.co.lesku.model.Subject;
import id.co.lesku.model.TeacherOrder;
import id.co.lesku.model.UnpaidOrder;
import id.co.lesku.model.User;
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

public interface LeskuAPIService
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
    @POST("auth/login/")
    Maybe<JsonObject> login (
            @Field("email") String email,
            @Field("password") String password,
            @Field("app_firebase_id") String regid
    );

    @FormUrlEncoded
    @POST("auth/register/")
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

    @GET ("users/")
    Maybe<List<User>> getUsers ();

    @GET ("users/{id}")
    Maybe<User> getUser (@Path ("id") String id);

    //Home

    @GET ("study_levels/")
    Maybe<List<StudyLevel>> getStudyLevels ();

    @GET ("subjects/")
    Maybe<List<Subject>> getSubjects ();

    @GET ("products/")
    Maybe<List<Product>> getProducts ();

    //OrderClass
    @GET ("order/teachers")
    Maybe<List<TeacherOrder>> getTeachersOrder ();

    @POST ("order/add")
    Maybe<ResponseBody> addOrderClass (@Body RequestBody object);

    @GET ("order/unpaid")
    Maybe<List<UnpaidOrder>> getUnpaidOrderList ();

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
}
