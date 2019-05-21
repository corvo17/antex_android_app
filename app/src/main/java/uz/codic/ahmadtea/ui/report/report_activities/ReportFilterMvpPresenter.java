package uz.codic.ahmadtea.ui.report.report_activities;

import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface ReportFilterMvpPresenter<V extends ReportFilterMvpView> extends MvpPresenter<V> {

    void  getDate();
}
