package uz.codic.ahmadtea.ui.visit.zakaz.prices;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.paymentTypes.adapter.Callback;
import uz.codic.ahmadtea.ui.visit.zakaz.product.ProductFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;

import static uz.codic.ahmadtea.utils.Consts.productTag;
import static uz.codic.ahmadtea.utils.Consts.visitTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class PricesFragment extends BaseFragment implements Callback {

    RecyclerView pricesRecycler;

    PriceAdapter adapter;

    OnFragmentInteractionListener listener;

    @Override
    public void onItemClick(int id) {
        if (listener.getCompleteApi().getOrderObject().getId_price() == null){
        getActivity().findViewById(R.id.btn_filter).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.btn_search).setVisibility(View.VISIBLE);
        listener.getCompleteApi().getOrderObject().setId_price(id);
        listener.transactionFragments(ProductFragment.newInstance(), productTag);
        }else if (id != listener.getCompleteApi().getOrderObject().getId_price()){
            getActivity().findViewById(R.id.btn_filter).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.btn_search).setVisibility(View.VISIBLE);
            listener.getCompleteApi().getOrderObject().setId_price(id);
            listener.transactionFragments(ProductFragment.newInstanceWithNewPrice(), productTag);
        } else if (listener.getCompleteObject().getPriceList().size() >1){
            getActivity().findViewById(R.id.btn_filter).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.btn_search).setVisibility(View.VISIBLE);
            listener.getCompleteApi().getOrderObject().setId_price(id);
            listener.transactionFragments(ProductFragment.newInstance(), productTag);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (listener.getCompleteObject().getPriceList().size() == 1) {
//            getActivity().findViewById(R.id.btn_filter).setVisibility(View.VISIBLE);
//            getActivity().findViewById(R.id.btn_search).setVisibility(View.VISIBLE);
//            listener.transactionFragments(ProductFragment.newInstance(), pricesTag);
//        }

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

            getActivity().findViewById(R.id.btn_forward).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener.getCompleteApi().getOrderObject().getId_price() != null)
                        listener.transactionFragments(ProductFragment.newInstance(), productTag);
                    else {
                        Toast.makeText(getActivity(), "Choose price", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        adapter = new PriceAdapter();
        adapter.setCallback(this);
        pricesRecycler = view.findViewById(R.id.prices_recyler);
        pricesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        pricesRecycler.setAdapter(adapter);
        adapter.updateList(listener.getCompleteObject().getPriceList());

        getActivity().findViewById(R.id.btn_filter).setVisibility(View.GONE);
        getActivity().findViewById(R.id.btn_search).setVisibility(View.GONE);

    }


    public PricesFragment() {
        // Required empty public constructor
    }

    public static PricesFragment newInstance() {
        PricesFragment pricesFragment = new PricesFragment();
        Bundle bundle = new Bundle();
        pricesFragment.setArguments(bundle);
        return pricesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prices, container, false);
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
