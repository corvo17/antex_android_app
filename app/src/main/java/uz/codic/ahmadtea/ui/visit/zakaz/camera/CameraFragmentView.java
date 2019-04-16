package uz.codic.ahmadtea.ui.visit.zakaz.camera;

import java.io.File;
import java.util.List;

import uz.codic.ahmadtea.ui.base.MvpView;
import uz.codic.ahmadtea.ui.visit.zakaz.camera.imageAdapter.CameraModule;

public interface CameraFragmentView extends MvpView {
    void loadDataImage(List<CameraModule> alldataImages);
    void openImage(File file);
}
