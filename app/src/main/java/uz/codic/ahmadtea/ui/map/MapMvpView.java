package uz.codic.ahmadtea.ui.map;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface MapMvpView extends MvpView {
    void onMerchantReady(Merchant merchant);
    void sentZakas(Merchant merchant);
    void onMerchantsListReady(List<WorkspaceAndMerchant> list);
}
