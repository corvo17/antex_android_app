package uz.codic.ahmadtea.ui.visit.zakaz.visit_photo;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.VisitPhoto;
import uz.codic.ahmadtea.ui.base.MvpPresenter;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface PhotoFragmentView extends MvpView {

    void onResponseAllVisitPhotos(List<VisitPhoto> visitPhotos);
}
