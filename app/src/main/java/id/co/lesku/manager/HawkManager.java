package id.co.lesku.manager;

import com.orhanobut.hawk.Hawk;

public class HawkManager {
    public void storeFirebaseRegId(String token) {
        Hawk.put(ConfigManager.PUSH_NOTIFICATION, token);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        Hawk.put(ConfigManager.IS_FIRST_TIME_LAUNCH, isFirstTime);
    }

    public void storeAppUserData(String email, String name, String token, String app_img) {
        Hawk.put(ConfigManager.APP_USER_EMAIL, email);
        Hawk.put(ConfigManager.APP_USER_NAME, name);
        Hawk.put(ConfigManager.APP_USER_TOKEN, token);
        Hawk.put(ConfigManager.APP_USER_IMG, app_img);
    }

    public void destroyAppData(){
        Hawk.deleteAll();
    }


    //GET DATA

    public boolean isFirstTimeLaunch() {
        return Hawk.get(ConfigManager.IS_FIRST_TIME_LAUNCH, true);
    }

    public String getFirebaseId() {
        return Hawk.get(ConfigManager.PUSH_NOTIFICATION, null);
    }

    public String getAppUserEmail() {
        return Hawk.get(ConfigManager.APP_USER_EMAIL, null);
    }

    public String getAppUserName() {
        return Hawk.get(ConfigManager.APP_USER_NAME, null);
    }

    public String getAppUserToken() {
        return Hawk.get(ConfigManager.APP_USER_TOKEN, null);
    }

    public String getAppUserImg() {
        return Hawk.get(ConfigManager.APP_USER_IMG, null);
    }
}
