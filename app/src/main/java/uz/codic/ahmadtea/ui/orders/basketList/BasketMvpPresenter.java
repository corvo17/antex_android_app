package uz.codic.ahmadtea.ui.orders.basketList;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface BasketMvpPresenter<V extends BasketMvpView> extends MvpPresenter<V> {

    void getBasketList(int priceId, String orderId);
}
