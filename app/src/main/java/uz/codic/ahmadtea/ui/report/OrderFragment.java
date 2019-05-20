package uz.codic.ahmadtea.ui.report;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.report.adapter.OrderAdapter;
import uz.codic.ahmadtea.ui.report.adapter.OrderedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment implements OrderMvpView {


    RecyclerView merchantRecyler;

    OrderMvpPresenter<OrderMvpView> presenter;

    OrderAdapter adapter;

    @Override
    public void onOrderedListReady(List<OrderedList> lists) {
        adapter.updateList(lists);
    }

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        merchantRecyler = view.findViewById(R.id.orders_merchant_recyler);
        presenter = new OrderPresenter<>(getContext());
        presenter.onAttach(this);

        adapter = new OrderAdapter();
        merchantRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        merchantRecyler.setAdapter(adapter);

        //presenter.getOrderedList();
    }

    public static OrderFragment newInstance(){
        OrderFragment fg = new OrderFragment();
        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

}
