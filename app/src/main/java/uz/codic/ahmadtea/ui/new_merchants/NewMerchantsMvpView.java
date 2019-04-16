package uz.codic.ahmadtea.ui.new_merchants;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface NewMerchantsMvpView extends MvpView {

    void resultNewMerchants(List<NewMerchant> merchants);
}
