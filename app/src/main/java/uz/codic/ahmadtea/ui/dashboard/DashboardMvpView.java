package uz.codic.ahmadtea.ui.dashboard;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface DashboardMvpView extends MvpView {

    void onMerchantsListReady(List<WorkspaceAndMerchant> dailyMerchants, List<WorkspaceAndMerchant> outOfDailyMerchants);
    void onOrdersReady(List<Order> orders);


}
