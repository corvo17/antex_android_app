package uz.codic.ahmadtea.ui.visit.zakaz.warehouse;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.paymentTypes.adapter.Callback;
import uz.codic.ahmadtea.ui.visit.zakaz.prices.PriceAdapter;
import uz.codic.ahmadtea.ui.visit.zakaz.prices.PricesFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.product.ProductFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;

import static uz.codic.ahmadtea.utils.Consts.pricesTag;
import static uz.codic.ahmadtea.utils.Consts.productTag;
import static uz.codic.ahmadtea.utils.Consts.visitTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class WareHouseFragment extends BaseFragment implements Callback {

    RecyclerView recyclerView;

    AdapterWarehouse adapter;

    OnFragmentInteractionListener listener;

    @Override
    public void onItemClick(int id) {
        Log.d("baxtiyor", "onItemClick: " + id);
        listener.getCompleteApi().getOrderObject().setId_warehouse(id);
        Log.d("baxtiyor", "onItemClick: order " + listener.getCompleteApi().getOrderObject().getId_warehouse());
        listener.transactionFragments(PricesFragment.newInstance(), pricesTag);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            StateProgressBar progressBar = getActivity().findViewById(R.id.progress_state);
            progressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            getActivity().findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.GONE);
                    getActivity().findViewById(R.id.btn_forward).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.lnr_buttons).setVisibility(View.VISIBLE);
                    //listener.onBackClick(visitTag);
                    listener.transactionFragments(VisitFragment.newInstance(), visitTag);
                }
            });
        }

        recyclerView = view.findViewById(R.id.warehouse_recyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterWarehouse();
        adapter.setCallback(this);
        adapter.updateList(listener.getPhysicalWareHouse());
        recyclerView.setAdapter(adapter);


    }


    public WareHouseFragment() {
        // Required empty public constructor
    }

    public static WareHouseFragment newInstance() {
        WareHouseFragment pricesFragment = new WareHouseFragment();
        Bundle bundle = new Bundle();
        pricesFragment.setArguments(bundle);
        return pricesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_warehouse, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {//if parentActivity implements listener,
            listener = (OnFragmentInteractionListener) context; // then we can get reference
        } else {
            throw new RuntimeException(context.toString() + "must implement listener");
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
