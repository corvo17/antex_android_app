package uz.codic.ahmadtea.ui.users;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.DataManager;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.ui.base.BasePresenter;

public class UsersPresenter<V extends UsersMvpView> extends BasePresenter<V>
        implements UsersMvpPresenter<V> {

    DataManager dataManager;
    public UsersPresenter(Context context){
        super(context);
    }

    @Override
    public void getAllUsers() {
        getDisposable().add(
                getDataManager().getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> list) throws Exception {
                        getMvpView().onUserListReady(list);
                    }
                })
        );


    }

    @Override
    public void setId_employee(String id_employee, String role) {
        getDataManager().setId_employee(id_employee);
        if (role.equals("admin")){
            getDataManager().changeIsAdmin(true);
        }else getDataManager().changeIsAdmin(false);

    }

    @Override
    public void setToken(String token) {
        getDataManager().putToken(token);
    }
}
