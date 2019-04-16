package uz.codic.ahmadtea.ui.visit;

import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.ui.base.MvpPresenter;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteApi;

public interface MerchantMvpPresenter<V extends MerchantMvpView> extends MvpPresenter<V> {

    void requestSend(CompleteApi completeApi);

    void requestSendDraft(CompleteApi completeApi);

    void saveAsPending(CompleteApi completeApi);

    void requestCompleteObject(int merchantId, String workspaceId);

    void requestCompleteObject(String id_merchant, String workspaceId);

    InfoAction getInfoAction(String id_workspace, String id_merchant);

}
