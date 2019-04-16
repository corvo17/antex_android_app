package uz.codic.ahmadtea.ui.synchronisation.setOrder.dialog;

import uz.codic.ahmadtea.data.db.entities.OrderBasket;

public interface SetOrderDialogListener {

    void onFinishedDialog(int quantity,int list_position);

    OrderBasket getOrderBasketByProductId(String id);
}
