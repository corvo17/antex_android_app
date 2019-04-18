package uz.codic.ahmadtea.ui.sittings;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface VersionInfoMvpPresenter<V extends VersionInfoMvpView> extends MvpPresenter<V> {

    void onRequestCheckVersion();

}
