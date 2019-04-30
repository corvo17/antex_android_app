package uz.codic.ahmadtea.ui.mainpage;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.MyWorkspace;
import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.utils.CommonUtils;

public class MainActivityPresenter<v extends MainActivityView> extends BasePresenter<v> implements MainActivityPresenterView<v> {

    public MainActivityPresenter(Context context) {
        super(context);
    }

      public void LoadUsers(){
          getDisposable().add(
                  getDataManager().getAllUsers()
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribeOn(Schedulers.io())
                          .subscribe(new Consumer<List<User>>() {
                              @Override
                              public void accept(List<User> list) throws Exception {
                                  getMvpView().userListReady(list);
                                  //To not get an error I used foreach loop rather than getting 0's index

                                  for (User x : list) {
                                      if (x.getId().equals(getDataManager().getId_employee())){
                                      getMvpView().initializeUser(x);
                                      break;}
                                  }
                              }
                          })
          );

      }

      public void logOut(){
          User user = getDataManager().getUserById(getDataManager().getId_employee());
          getDataManager().removeUserWorkspaces(getDataManager().getUserWorkspaceForLogOut(user.getId()));
          getDataManager().removeUser(user);
          getDataManager().getAllUsers()
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeOn(Schedulers.io())
                  .subscribe(new SingleObserver<List<User>>() {
                      @Override
                      public void onSubscribe(Disposable d) {

                      }

                      @Override
                      public void onSuccess(List<User> users) {
                          if (users.size() > 0){
                              getMvpView().initializeUserForButton(users.get(0));
                              getMvpView().userListReady(users);
                          }else {
                              getDataManager().setIslogin(false);
                          }
                          Log.d("baxtiyor", "onSuccess islogin: " + getDataManager().isLogin());
                          getMvpView().functionBeforeLogOut(getDataManager().isLogin());
                      }

                      @Override
                      public void onError(Throwable e) {

                      }
                  });

      }
}
