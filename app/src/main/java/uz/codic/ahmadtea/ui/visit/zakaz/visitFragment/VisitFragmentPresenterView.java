package uz.codic.ahmadtea.ui.visit.zakaz.visitFragment;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface VisitFragmentPresenterView<v extends VisitFragmentView> extends MvpPresenter<v> {
    void initialize();
}
