package uz.codic.ahmadtea.ui.visit.zakaz.camera;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface CameraFragmentPresenterView<v extends CameraFragmentView> extends MvpPresenter<v> {
    void LoadImages();
    String getString(int index);
}
