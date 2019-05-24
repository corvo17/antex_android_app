package uz.codic.ahmadtea.ui.dashboard;

import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface DashboardMvpPresenter<V extends DashboardMvpView> extends MvpPresenter<V> {

    //void send();
    void requestDailyMerchants(String date);
    //void getBasketList(int priceId, String orderId, Integer id_payment_type);
    void getAllOrders();


}
