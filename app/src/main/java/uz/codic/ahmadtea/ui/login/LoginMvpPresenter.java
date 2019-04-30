package uz.codic.ahmadtea.ui.login;

import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.data.network.model.Employee;
import uz.codic.ahmadtea.data.network.model.Login;
import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void userCheckFromDb(Login login);

    void onRequestLoginInfo(User user);

    void onRequestWorkspace(String login, String token);

    void onRequestGetWorkspaceRelations(String token);

    //This gets all users from database if there would not be any then it stays
    void checkUser();

    void onRequestBaseUrl(String code, String key);

    void onRequestResetToken(Login login);
}
