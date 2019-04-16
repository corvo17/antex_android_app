package uz.codic.ahmadtea.ui.new_merchants;

import android.content.Context;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.ui.base.BasePresenter;

public class NewMerchantsPresenter<V extends NewMerchantsMvpView> extends BasePresenter<V> implements NewMerchantsMvpPresenter<V>{

    public NewMerchantsPresenter(Context context) {
        super(context);
    }

    @Override
    public void getNewMerchants() {
        getDataManager().getNewMerchants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<NewMerchant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<NewMerchant> newMerchants) {
                        getMvpView().resultNewMerchants(newMerchants);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
