package uz.codic.ahmadtea.ui.report;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface ReportMvpPresenter<V extends ReportMvpView> extends MvpPresenter<V> {

    void getOrderedList();
}
