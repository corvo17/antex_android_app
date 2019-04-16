package uz.codic.ahmadtea.ui.visit.zakaz.product.dialog;

import uz.codic.ahmadtea.data.db.entities.OrderBasket;

public interface ProductDialogListener {

    void onFinishedDialog(int quantity,int box, int count, int list_position, boolean isOrderBasket);

    OrderBasket getOrderBasketByProductId(String id);
}
