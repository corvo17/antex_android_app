package uz.codic.ahmadtea.ui.synchronisation.setOrder;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface SetOrderMvpPresenter<V extends SetOrderMvpView> extends MvpPresenter<V> {

    void getOrderBaskets(String id_order);

    void getProductItems(int id_price, String  id_workspace, String id_product);

    void requestSend(List<OrderBasket> baskets, Order order, Visit visit);

    void requestSendDraft(List<OrderBasket> baskets, Order order, Visit visit);

    void setOrder(List<OrderBasket> baskets, Order order, Visit visit);

    Visit getVisit(String id_visit);
}
