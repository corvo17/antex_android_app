package uz.codic.ahmadtea.ui.synchronisation;


import uz.codic.ahmadtea.ui.base.MvpPresenter;
import uz.codic.ahmadtea.ui.report.adapter.OrderedList;

public interface SynchronisationMvpPresenter<V extends SynchronisationMvpView> extends MvpPresenter<V> {
    void startSynchronisation();

    void getPendingSize();

    void pendingNames();

    void getLastSyncTime();

    OrderedList getOrder(int which);
}
