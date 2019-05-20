package uz.codic.ahmadtea.ui.report.basketList;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface BasketMvpPresenter<V extends BasketMvpView> extends MvpPresenter<V> {

    void getBasketList(int priceId, String orderId, Integer id_payment_type);

    Visit getVisit(String id);
    List<Comment> getComments(List<Integer> ids);
}
