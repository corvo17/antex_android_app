package uz.codic.ahmadtea.data.prefs;

public interface PrefHelper {

    void putKey(String key);

    String getKey();

    void putToken(String token);

    String getToken();

    void setId_employee(String id_employee);

    String getId_employee();

    String getLastSyncTime();

    void setLastSyncTime(String lastSyncTime);

    boolean isAdmin();

    void changeIsAdmin(boolean bool);

    String getBaseUrl();

    void setBaseUrl(String baseUrl);

    boolean isLocalized();

    void setisLocalized(boolean isLogin);

    boolean isLogin();

    void setIslogin(boolean islogin);

    void setDate(String date);

    String getDate();

}
