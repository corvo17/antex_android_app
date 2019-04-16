package uz.codic.ahmadtea.ui.orders;

import java.util.List;

import uz.codic.ahmadtea.ui.base.MvpView;
import uz.codic.ahmadtea.ui.orders.adapter.OrderedList;

public interface OrderMvpView extends MvpView {

    void onOrderedListReady(List<OrderedList> lists);
}
