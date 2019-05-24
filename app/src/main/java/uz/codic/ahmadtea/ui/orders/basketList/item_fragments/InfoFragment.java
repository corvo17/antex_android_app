package uz.codic.ahmadtea.ui.orders.basketList.item_fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.BasketProduct;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
    private ArrayList<BasketProduct> basketProducts;
    private TextView tv_price, tv_type, tv_date;
    private Order order;
public InfoFragment(){

}
public static InfoFragment getInstance(List<BasketProduct> basketProducts, Order order){
    InfoFragment infoFragment = new InfoFragment();
    Bundle args = new Bundle();
    args.putParcelableArrayList("basketList",(ArrayList)basketProducts);
    args.putSerializable("order", order);
    infoFragment.setArguments(args);
    return infoFragment;
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    Bundle args = getArguments();
    basketProducts = args.getParcelableArrayList("basketList");
    order = (Order) args.getSerializable("order");
        return inflater.inflate(R.layout.fragment_from_which_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_price = view.findViewById(R.id.tv_price);
        tv_type = view.findViewById(R.id.tv_payment_type);
        tv_date = view.findViewById(R.id.tv_date);
        tv_price.setText(order.getTotal_cost()/100 + "");
        tv_type.setText(basketProducts.get(0).getPaymentType().getLabel());
        tv_date.setText(order.getDelivery_date());
    }
}
