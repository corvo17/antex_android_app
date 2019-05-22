package uz.codic.ahmadtea.ui.report.report_activities;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.PaymentType;
import uz.codic.ahmadtea.data.db.entities.PhysicalWareHouse;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.ui.base.MvpView;

public interface ReportFilterMvpView extends MvpView {

    void responseData(List<Merchant> merchants, List<Price> prices, List<PaymentType> paymentTypes, List<PhysicalWareHouse> wareHouses);
}
