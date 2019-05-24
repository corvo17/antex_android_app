package uz.codic.ahmadtea.ui.report.report_activities;

import android.content.Context;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.ui.base.BasePresenter;

public class ReportFilterPresenter<V extends ReportFilterMvpView> extends BasePresenter<V> implements ReportFilterMvpPresenter<V> {
    public ReportFilterPresenter(Context context) {
        super(context);
    }

    @Override
    public void getDate() {
        getDataManager().getMerchantsForReport(getDataManager().getMyWorkspaceIds(getDataManager().getId_employee()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Merchant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Merchant> merchants) {
                        getPrices(merchants);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void getPrices(List<Merchant> merchants) {
        getMvpView().responseData(merchants, getDataManager().getPrices(), getDataManager().getPaymentTypes(), getDataManager().getPhysicalWareHouses());
    }
}
