package uz.codic.ahmadtea.ui.sittings;

import android.content.Context;

import java.util.HashMap;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.ui.base.BasePresenter;

public class VersionInfoPresenter<V extends VersionInfoMvpView> extends BasePresenter<V> implements VersionInfoMvpPresenter<V>  {
    public VersionInfoPresenter(Context context) {
        super(context);
    }

    @Override
    public void onRequestCheckVersion() {
        getDataManager().getMobileCurrentVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<HashMap<String, HashMap<String, String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(HashMap<String, HashMap<String, String>> map) {
                        if (map.get("meta").get("code").equals("200")){
                            getMvpView().onResponseCurrentVersion(map);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
