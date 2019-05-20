package uz.codic.ahmadtea.ui.visit.zakaz.visit_photo;

import android.content.Context;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.VisitPhoto;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.ui.base.MvpView;

public class PhotoFragmentPresenter<V extends PhotoFragmentView & MvpView> extends BasePresenter<V> implements PhotoFragmentPresenterView<V> {
    public PhotoFragmentPresenter(Context context) {
        super(context);
    }

    @Override
    public void insertVisitPhoto(VisitPhoto visitPhoto) {
        getDataManager().insertVisitPhoto(visitPhoto);
    }

    @Override
    public void getVisitPhotos(String merchant_id, String workspace_id) {
        getDataManager().getVisitPhotos(merchant_id, workspace_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<VisitPhoto>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<VisitPhoto> visitPhotos) {
                        getMvpView().onResponseAllVisitPhotos(visitPhotos);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
