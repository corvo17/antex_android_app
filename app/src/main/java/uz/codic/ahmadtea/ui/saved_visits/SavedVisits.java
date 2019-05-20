package uz.codic.ahmadtea.ui.saved_visits;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.report.OrderMvpPresenter;
import uz.codic.ahmadtea.ui.report.OrderMvpView;
import uz.codic.ahmadtea.ui.report.OrderPresenter;
import uz.codic.ahmadtea.ui.report.adapter.OrderedList;
import uz.codic.ahmadtea.utils.Consts;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedVisits extends BaseFragment implements OrderMvpView {


    RecyclerView merchantRecyler;

    OrderMvpPresenter<OrderMvpView> presenter;

    AdapterSavedVisits adapter;

    @Override
    public void onOrderedListReady(List<OrderedList> lists) {
        List<OrderedList> savedOrders = new ArrayList();
        for (OrderedList order : lists) {
            if (order.getOrder().getStatus().equals(Consts.statusPending) ){
                savedOrders.add(order);
            }
        }
        adapter.updateList(savedOrders);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        merchantRecyler = view.findViewById(R.id.saved_visits_r_v);
        presenter = new OrderPresenter<>(getContext());
        presenter.onAttach(this);

        adapter = new AdapterSavedVisits();
        merchantRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        merchantRecyler.setAdapter(adapter);

        presenter.getOrderedList();
    }

    public static SavedVisits newInstance(){
        SavedVisits fg = new SavedVisits();
        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_visits, container, false);
    }

}
