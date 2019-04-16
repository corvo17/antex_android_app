package uz.codic.ahmadtea.ui.map;

import uz.codic.ahmadtea.ui.base.MvpPresenter;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface MapMvpPresenter<V extends MapMvpView> extends MvpPresenter<V> {
    void getMerchant(int pid);
    void getMerchantForZakas(int pid);
    void getMerchants();
    String id_workspace(String id_merchant);
}
