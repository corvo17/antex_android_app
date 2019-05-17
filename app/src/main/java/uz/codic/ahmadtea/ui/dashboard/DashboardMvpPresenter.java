package uz.codic.ahmadtea.ui.dashboard;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface DashboardMvpPresenter<V extends DashboardMvpView> extends MvpPresenter<V> {

    //void send();
    void requestDailyMerchants(String date);

}
