package uz.codic.ahmadtea.ui.dailyPlan;

import java.util.HashMap;
import java.util.List;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface DailyMvpPresenter<V extends DailyMvpView> extends MvpPresenter<V> {

    void requestDailyMerchants(String date);

}
