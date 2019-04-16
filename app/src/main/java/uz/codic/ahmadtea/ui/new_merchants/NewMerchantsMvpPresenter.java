package uz.codic.ahmadtea.ui.new_merchants;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface NewMerchantsMvpPresenter<V extends NewMerchantsMvpView> extends MvpPresenter<V> {

    void getNewMerchants();
}
