package uz.codic.ahmadtea.ui.orders;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface OrderMvpPresenter<V extends OrderMvpView> extends MvpPresenter<V> {

    void getOrderedList();
}
