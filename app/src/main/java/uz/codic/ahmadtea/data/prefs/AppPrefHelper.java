package uz.codic.ahmadtea.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class AppPrefHelper implements PrefHelper{

    private final static String API_KEY = "key";
    private final static String TOKEN = "token";

    SharedPreferences mPref;

    public AppPrefHelper(Context context){
        mPref = context.getSharedPreferences("Users", Context.MODE_PRIVATE);
    }

    public void putToken(String token){
        mPref.edit().putString(TOKEN, "Bearer " + token).apply();
    }

    public String getToken(){
        return mPref.getString(TOKEN, null);
    }

    @Override
    public void putKey(String key){
        mPref.edit().putString(API_KEY, key).apply();
    }

    @Override
    public String getKey(){
        return mPref.getString(API_KEY, "");
    }

    @Override
    public String getId_employee(){
        return mPref.getString("id_employee", null);
    }

    @Override
    public void setId_employee(String id_employee){
        mPref.edit().putString("id_employee", id_employee).apply();
    }

    @Override
    public String getLastSyncTime(){
        return mPref.getString("last_sync_time", "not synchronisation yet");
    }

    @Override
    public void setLastSyncTime(String lastSyncTime){
        mPref.edit().putString("last_sync_time", lastSyncTime).apply();
    }

    @Override
    public boolean isAdmin() {
        return mPref.getBoolean("isAdmin", false);
    }

    @Override
    public void changeIsAdmin(boolean bool) {
        mPref.edit().putBoolean("isAdmin", bool).apply();
    }

    @Override
    public String getBaseUrl() {
        return mPref.getString("baseUrl", "http://central.oltinolma.uz");
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        mPref.edit().putString("baseUrl", baseUrl).apply();
    }

    @Override
    public boolean isLocalized() {
        return mPref.getBoolean("isLocalized", false);
    }

    @Override
    public void setisLocalized(boolean isLogin) {
        mPref.edit().putBoolean("isLocalized", isLogin).apply();
    }

    @Override
    public boolean isLogin() {
        return mPref.getBoolean("islogin", false);
    }

    @Override
    public void setIslogin(boolean islogin) {
        mPref.edit().putBoolean("islogin", islogin).apply();
    }
}
