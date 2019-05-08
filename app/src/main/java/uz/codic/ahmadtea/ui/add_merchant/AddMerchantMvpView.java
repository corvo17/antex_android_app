package uz.codic.ahmadtea.ui.add_merchant;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.network.model.SendResponse;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface AddMerchantMvpView extends MvpView {

    void onMyWorkspaces(List<Workspace> workspaces);

    void resultAddMerchant();

}
