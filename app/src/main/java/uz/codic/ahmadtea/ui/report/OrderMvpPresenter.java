package uz.codic.ahmadtea.ui.report;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface OrderMvpPresenter<V extends OrderMvpView> extends MvpPresenter<V> {

    void getOrderedList();
}
