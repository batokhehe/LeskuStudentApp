package id.co.lesku.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    //STORE DATA
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(ConfigManager.SHARED_PREF, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void storeFirebaseRegId(String token) {
        editor.putString(ConfigManager.PUSH_NOTIFICATION, token);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(ConfigManager.IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void storeAppUserData(String email, String name, String token, String app_img) {
        editor.putString(ConfigManager.APP_USER_EMAIL, email);
        editor.putString(ConfigManager.APP_USER_NAME, name);
        editor.putString(ConfigManager.APP_USER_TOKEN, token);
        editor.putString(ConfigManager.APP_USER_IMG, app_img);
        editor.commit();
    }

    public void destroyAppData(){
        editor.clear().commit();
    }


    //GET DATA

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(ConfigManager.IS_FIRST_TIME_LAUNCH, true);
    }

    public String getFirebaseId() {
        return pref.getString(ConfigManager.PUSH_NOTIFICATION, null);
    }

    public String getAppUserEmail() {
        return pref.getString(ConfigManager.APP_USER_EMAIL, null);
    }

    public String getAppUserName() {
        return pref.getString(ConfigManager.APP_USER_NAME, null);
    }

    public String getAppUserToken() {
        return pref.getString(ConfigManager.APP_USER_TOKEN, null);
    }

    public String getAppUserImg() {
        return pref.getString(ConfigManager.APP_USER_IMG, null);
    }
}
