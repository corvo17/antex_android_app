package uz.codic.ahmadtea.ui.orders.basketList;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.base.MvpPresenter;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteApi;

public interface BasketMvpPresenter<V extends BasketMvpView> extends MvpPresenter<V> {

    void getBasketList(int priceId, String orderId, Integer id_payment_type);

    Visit getVisit(String id);
    List<Comment> getComments(List<Integer> ids);
    void requestSend(CompleteApi completeApi);

    void requestSendDraft(CompleteApi completeApi);

    void saveAsPending(CompleteApi completeApi, String status);
    void requestCompleteObject(int merchantId, String workspaceId);

    void requestCompleteObject(String id_merchant, String workspaceId);

    InfoAction getInfoAction(String id_workspace, String id_merchant);
}
