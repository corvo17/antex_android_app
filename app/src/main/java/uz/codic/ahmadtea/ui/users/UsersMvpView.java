package uz.codic.ahmadtea.ui.users;


import java.util.List;

import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface UsersMvpView extends MvpView {

    void onUserListReady(List<User> list);

    void finishActivity();
    void goNext();
}
