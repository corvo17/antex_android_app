package uz.codic.ahmadtea.ui.mainpage;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
                                      getMvpView().initializeUser(x);
                                      break;
                                  }
                              }
                          })
          );

      }

    @Override
    public void getWorkspaceAndMerchant() {
        getDataManager().getWorkspaceAndMerchants(getDataManager().getMyWorkspaceIds(getDataManager().getId_employee()), CommonUtils.getToday())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WorkspaceAndMerchant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<WorkspaceAndMerchant> merchants) {
                        getMvpView().setmMerchants(merchants);
                        Log.d("baxtiyor", "MainActivityPresenter: " + merchants);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
