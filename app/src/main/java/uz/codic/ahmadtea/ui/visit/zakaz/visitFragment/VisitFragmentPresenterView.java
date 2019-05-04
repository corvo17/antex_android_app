package uz.codic.ahmadtea.ui.visit.zakaz.visitFragment;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.FileReportType;
import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface VisitFragmentPresenterView<v extends VisitFragmentView> extends MvpPresenter<v> {
    void initialize();

    void getFileReportTypes();
}
