package uz.codic.ahmadtea.ui.report;

import java.util.List;

import uz.codic.ahmadtea.ui.base.MvpView;
import uz.codic.ahmadtea.ui.report.adapter.OrderedList;

public interface ReportMvpView extends MvpView {

    void onOrderedListReady(List<OrderedList> lists);
}
