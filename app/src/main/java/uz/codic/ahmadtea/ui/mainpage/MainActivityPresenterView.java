package uz.codic.ahmadtea.ui.mainpage;

import uz.codic.ahmadtea.ui.base.MvpPresenter;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface MainActivityPresenterView<v extends MvpView> extends MvpPresenter<v> {

    void getWorkspaceAndMerchant();

}
