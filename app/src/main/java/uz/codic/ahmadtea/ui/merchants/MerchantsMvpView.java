package uz.codic.ahmadtea.ui.merchants;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface MerchantsMvpView extends MvpView {

    void getMerchants(List<MerchantListWorspaces> merchants);

    void onReadyMyWorkspaces(List<Workspace> workspaces);

    void onReadyMerchantsInWorkspace(List<MerchantListWorspaces> merchantInWorkspace);

    void onResultInfoActionUpdate();
}
