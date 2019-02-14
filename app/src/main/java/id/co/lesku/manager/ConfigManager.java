package id.co.lesku.manager;

public class ConfigManager {
    //BASE URL
    public static final String BASE_URL = "http://192.168.1.5:8000/api/";
    public static final String BASE_URL_IMAGE = "http://192.168.1.5:8000/img/";
//    public static final String BASE_URL = "http://192.168.43.54:8000/api/";
//    public static final String BASE_URL_IMAGE = "http://192.168.43.54:8000/img/";

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "leskuFbRegId";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "lesku-app";
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    //User Data
    public static final String APP_USER_EMAIL = "appUserEmail";
    public static final String APP_USER_NAME = "appUserName";
    public static final String APP_USER_TOKEN = "appUserToken";
    public static final String APP_USER_IMG = "appUserImg";

    //Request Code
    public static final int REQUEST_CODE_TEACHER = 250;
}
