package uz.codic.ahmadtea.ui.users;

import java.util.List;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface UsersMvpPresenter<V extends UsersMvpView> extends MvpPresenter<V> {

    void getAllUsers();

    void setId_employee(String id_employee, String role);

    void setToken(String token);

}
