package uz.codic.ahmadtea.ui.visit;

import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.ui.base.MvpView;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteObject;

public interface MerchantMvpView extends MvpView {

    void onCompleteObjectReady(CompleteObject order);

    void openMainActivity();

    void goBack();

    InfoAction getInfoAction();
}
