package uz.codic.ahmadtea.ui.login;

import uz.codic.ahmadtea.ui.base.MvpView;

public interface LoginMvpView extends MvpView {

    void hideLoading();

    void onStay();

    void dontStay();

    void onResponseCentral();
}
