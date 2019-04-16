package uz.codic.ahmadtea.ui.map;

import android.content.Context;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.utils.CommonUtils;

public class MapPresenter<V extends MapMvpView> extends BasePresenter<V> implements MapMvpPresenter<V> {
    public MapPresenter(Context context) {
        super(context);
    }

    @Override
    public void getMerchant(int pid) {
        getDataManager().getMerchantById(pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Merchant>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Merchant merchant) {
                        getMvpView().onMerchantReady(merchant);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showMessage(e.getMessage());
                    }
                });


    }

    @Override
    public void getMerchantForZakas(int pid) {
        getDataManager().getMerchantById(pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Merchant>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Merchant merchant) {
                        getMvpView().sentZakas(merchant);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showMessage(e.getMessage());
                    }
                });
    }

    @Override
    public void getMerchants() {
        getDataManager().getWorkspaceAndMerchants(getDataManager().getMyWorkspaceIds(getDataManager().getId_employee()), CommonUtils.getToday())
                //Run on background thread
                .subscribeOn(Schedulers.io())
                //Notify on main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WorkspaceAndMerchant>>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onSuccess(List<WorkspaceAndMerchant> merchants) {
                                   getMvpView().onMerchantsListReady(merchants);

                               }

                               @Override
                               public void onError(Throwable e) {
                                   getMvpView().showMessage(e.getMessage());
                               }
                           }
                );
    }

    @Override
    public String id_workspace(String id_merchant) {
        return getDataManager().getId_workspace(id_merchant);
    }
}
