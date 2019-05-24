package uz.codic.ahmadtea.ui.report;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.report.adapter.OrderAdapter;
import uz.codic.ahmadtea.ui.report.adapter.OrderedList;
import uz.codic.ahmadtea.ui.report.report_activities.ReportFilterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends BaseFragment implements ReportMvpView {


    RecyclerView merchantRecyler;

    ReportMvpPresenter<ReportMvpView> presenter;

    OrderAdapter adapter;
    Button button1, button2, button3 ,button4 ,button5;

    @Override
    public void onOrderedListReady(List<OrderedList> lists) {
        adapter.updateList(lists);
    }

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        merchantRecyler = view.findViewById(R.id.orders_merchant_recyler);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);

        presenter = new ReportPresenter<>(getContext());
        presenter.onAttach(this);



        adapter = new OrderAdapter();
        merchantRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        merchantRecyler.setAdapter(adapter);

        button1.setOnClickListener(v -> {
            openReportFilterActivity(1);
        });

        button2.setOnClickListener(v -> {
            openReportFilterActivity(2);
        });

        button3.setOnClickListener(v -> {
            openReportFilterActivity(3);
        });

        button4.setOnClickListener(v -> {
            openReportFilterActivity(4);
        });

        button5.setOnClickListener(v -> {
            openReportFilterActivity(5);
        });


        //presenter.getOrderedList();
    }

    private void openReportFilterActivity(int type) {
        Intent intent = new Intent(getContext(), ReportFilterActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public static ReportFragment newInstance(){
        ReportFragment fg = new ReportFragment();
        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }



}
