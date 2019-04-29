package uz.codic.ahmadtea.ui.mainpage;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface MainActivityView extends MvpView {
     void userListReady(List<User> all);

     void initializeUser(User user);

     void initializeUserForButton(User user);

}
