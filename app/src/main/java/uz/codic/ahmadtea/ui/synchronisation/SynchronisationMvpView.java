package uz.codic.ahmadtea.ui.synchronisation;

import java.util.List;

import uz.codic.ahmadtea.ui.base.MvpView;

public interface SynchronisationMvpView extends MvpView {

    void relustSync(String result);

    void hideLoading();

    void relustPendingSize(int pendingCount);

    void showPendings(List<String> pandengNames);

    void showLastSyncTime(String lastSyncTime);
}
