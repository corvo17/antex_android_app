package uz.codic.ahmadtea.ui.visit.zakaz.visit_photo;

import uz.codic.ahmadtea.data.db.entities.VisitPhoto;
import uz.codic.ahmadtea.ui.base.MvpPresenter;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface PhotoFragmentPresenterView<V extends PhotoFragmentView & MvpView> extends MvpPresenter<V> {

    void insertVisitPhoto(VisitPhoto visitPhoto);

    void getVisitPhotos(String merchant_id, String workspace_id);
}
