package id.co.lesku.data;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.lesku.data.local.DetailsOrderStorage;
import id.co.lesku.data.local.ProductStorage;
import id.co.lesku.data.local.StudyLevelStorage;
import id.co.lesku.data.local.SubjectStorage;
import id.co.lesku.data.local.TeacherOrderStorage;
import id.co.lesku.data.local.UnpaidOrderStorage;
import id.co.lesku.data.local.UserStorage;
import id.co.lesku.data.remote.AuthAPI;
import id.co.lesku.data.remote.HomeAPI;
import id.co.lesku.data.remote.OrderAPI;
import id.co.lesku.data.remote.StudyLevelAPI;
import id.co.lesku.data.remote.SubjectAPI;
import id.co.lesku.data.remote.TeacherOrderAPI;
import id.co.lesku.data.remote.UserAPI;
import id.co.lesku.model.DetailsOrder;
import id.co.lesku.model.Product;
import id.co.lesku.model.StudyLevel;
import id.co.lesku.model.Subject;
import id.co.lesku.model.TeacherOrder;
import id.co.lesku.model.UnpaidOrder;
import id.co.lesku.model.User;
import io.reactivex.Maybe;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class DataManager implements DataManagerType
{
    private static DataManager dm;

    public static DataManager can () // or use, or call (?)
    {
        if (dm == null)
        {
            dm = new DataManager();
        }
        return dm;
    }

    private static AuthAPI     sAuthAPI     = new AuthAPI();
    private static UserAPI     sUserAPI     = new UserAPI();
    private static StudyLevelAPI sStudyLevelAPI     = new StudyLevelAPI();
    private static HomeAPI     sHomeAPI     = new HomeAPI();
    private static OrderAPI     sOrderAPI     = new OrderAPI();
    private static TeacherOrderAPI     sTeacherOrderAPI     = new TeacherOrderAPI();
    private static SubjectAPI     sSubjectAPI     = new SubjectAPI();

    private static UserStorage sUserStorage = new UserStorage();
    private static ProductStorage sProductStorage = new ProductStorage();
    private static TeacherOrderStorage sTeacherOrderStorage = new TeacherOrderStorage();
    private static UnpaidOrderStorage sUnpaidOrderStorage = new UnpaidOrderStorage();
    private static DetailsOrderStorage sDetailsOrderStorage = new DetailsOrderStorage();
    private static StudyLevelStorage sStudyLevelStorage = new StudyLevelStorage();
    private static SubjectStorage sSubjectStorage = new SubjectStorage();

    //AUTH

    @Override
    public Maybe<JsonObject> login (String email, String password, String regid)
    {
        return sAuthAPI.login(email, password, regid);
    }

    @Override
    public Maybe<JsonObject> register(String fname, String lname, String email, int studylevelid, String parent_name, String school_name, String address, String phone_number, String password, String cpassword)
    {
        return sAuthAPI.register(fname, lname, email, studylevelid, parent_name, school_name, address, phone_number, password, cpassword);
    }

    @Override
    public void logout ()
    {
        sAuthAPI.logout();
    }

    @Override
    public Maybe<JsonObject> forgotPassword (String id) { return sAuthAPI.forgotPassword(id);}

    @Override
    public Maybe<List<User>> getUserList ()
    {
        return Maybe.concat(sUserStorage.getList(), sUserAPI.getList().doOnSuccess(new Consumer<List<User>>()
        {
            @Override
            public void accept (List<User> users) throws Exception
            {
                sUserStorage.addAll(users);
            }
        })).firstElement();
    }

    @Override
    public Maybe<User> getUser (Integer id)
    {
        return Maybe.concat(sUserStorage.get(id), sUserStorage.get(id).doOnSuccess(new Consumer<User>()
        {
            @Override
            public void accept (User user) throws Exception
            {
                sUserStorage.add(user);
            }
        })).firstElement();
    }

    @Override
    public Maybe<List<Product>> getProductList ()
    {
        return Maybe.concat(sProductStorage.getList(), sHomeAPI.getList().doOnSuccess(new Consumer<List<Product>>()
        {
            @Override
            public void accept (List<Product> product) throws Exception
            {
                sProductStorage.addAll(product);
            }
        })).firstElement();
    }

    @Override
    public Maybe<List<StudyLevel>> getStudyLevelList()
    {
        return Maybe.concat(sStudyLevelStorage.getList(), sStudyLevelAPI.getList().doOnSuccess(new Consumer<List<StudyLevel>>()
        {
            @Override
            public void accept (List<StudyLevel> studyLevels) throws Exception
            {
                sStudyLevelStorage.addAll(studyLevels);
            }
        })).firstElement();
    }

    @Override
    public Maybe<List<Subject>> getSubject()
    {
        return Maybe.concat(sSubjectStorage.getList(), sSubjectAPI.getList().doOnSuccess(new Consumer<List<Subject>>()
        {
            @Override
            public void accept (List<Subject> subjects) throws Exception
            {
                sSubjectStorage.addAll(subjects);
            }
        })).firstElement();
    }

    //ORDER
    @Override
    public Maybe<List<TeacherOrder>> getTeacherOrderList ()
    {
        return Maybe.concat(sTeacherOrderStorage.getList(), sTeacherOrderAPI.getList().doOnSuccess(new Consumer<List<TeacherOrder>>()
        {
            @Override
            public void accept (List<TeacherOrder> teacherOrder) throws Exception
            {
                sTeacherOrderStorage.addAll(teacherOrder);
            }
        })).firstElement();
    }

    @Override
    public Maybe<ResponseBody> addOrderClass (RequestBody object)
    {
        return sOrderAPI.store(object);
    }

    //UNPAID ORDER
    @Override
    public Maybe<List<UnpaidOrder>> getUnpaidOrderList ()
    {
        return Maybe.concat(sUnpaidOrderStorage.getList(), sOrderAPI.getUnpaidOrderList().doOnSuccess(new Consumer<List<UnpaidOrder>>()
        {
            @Override
            public void accept (List<UnpaidOrder> unpaidOrders) throws Exception
            {
                sUnpaidOrderStorage.addAll(unpaidOrders);
            }
        })).firstElement();
    }

    //ORDER DETAILS
    @Override
    public Maybe<List<DetailsOrder>> getDetailsOrderList(String studyClassId)
    {
        return Maybe.concat(sDetailsOrderStorage.getList(), sOrderAPI.getDetailsOrderList(studyClassId).doOnSuccess(new Consumer<List<DetailsOrder>>()
        {
            @Override
            public void accept (List<DetailsOrder> detailsOrder) throws Exception
            {
                sDetailsOrderStorage.addAll(detailsOrder);
            }
        })).firstElement();
    }

    @Override
    public Maybe<JsonObject> getTeacherBlankScheduleList(String teacherId)
    {
        return sOrderAPI.getTeacherBlankScheduleList(teacherId);
    }

    @Override
    public Maybe<JsonObject> uploadTrfFile (String id, String trfFile)
    {
        return sOrderAPI.uploadTrfFile(id, trfFile);
    }
}
