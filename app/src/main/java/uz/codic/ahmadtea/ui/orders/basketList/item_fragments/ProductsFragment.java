package uz.codic.ahmadtea.ui.orders.basketList.item_fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.AllProductsAdapter;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.BasketProduct;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {
    private List<BasketProduct> basketProducts;
    private Order order;
    private RecyclerView recycler_all_products;
    private TextView totalCount, totalSum;

    public ProductsFragment() {
        // Required empty public constructor
    }
    public static ProductsFragment getInstance(List<BasketProduct> basketProducts, Order order){
        Bundle args = new Bundle();
        args.putParcelableArrayList("list", (ArrayList)basketProducts);
        args.putSerializable("order",order);
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        order = (Order)getArguments().getSerializable("order");
        basketProducts = getArguments().getParcelableArrayList("list");
        recycler_all_products = view.findViewById(R.id.saved_products_recycler);
        AllProductsAdapter adapter = new AllProductsAdapter((ArrayList) basketProducts);
        recycler_all_products.setAdapter(adapter);

        int allCount = 0;
        int allSum = 0;
        for (BasketProduct product :basketProducts) {
            allCount = allCount + product.getOrderBasket().getTotal_count();
            allSum = allSum + product.getProductPrice().getValue() * product.getOrderBasket().getTotal_count();
        }
        totalCount = view.findViewById(R.id.count);
        totalSum = view.findViewById(R.id.total);
        totalCount.setText(allCount + "");
        totalSum.setText(allSum/100 + "");
    }
}
