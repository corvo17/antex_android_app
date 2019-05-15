package uz.codic.ahmadtea.ui.dailyPlan;

import java.util.HashMap;
import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.data.network.model.DailyMerchants;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface DailyMvpView extends MvpView {

    void onMerchantsListReady(List<WorkspaceAndMerchant> merchants);

    void goLoginActivity(String error_label);

    void onReadyMyWorkspaces(List<Workspace> workspaces);
}
