package uz.codic.ahmadtea.ui.merchants;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface MerchantsMvpPresenter<V extends MerchantsMvpView> extends MvpPresenter<V> {

    void getWorkspaceAndMerchants();

    void getMyWorkspaces();

    void getMerchantsInWorkspace(String id_workspace);

    boolean isAdmin();

    void getMerchantListWorkspaces(List<WorkspaceAndMerchant> merchant);

}
