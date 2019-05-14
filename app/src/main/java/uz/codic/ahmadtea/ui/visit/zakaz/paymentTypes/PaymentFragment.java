package uz.codic.ahmadtea.ui.visit.zakaz.paymentTypes;


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
import uz.codic.ahmadtea.ui.visit.zakaz.paymentTypes.adapter.PaymentTypesAdapter;
import uz.codic.ahmadtea.ui.visit.zakaz.product.ProductFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.shippingDate.ShippingDateFragment;

import static uz.codic.ahmadtea.utils.Consts.productTag;
import static uz.codic.ahmadtea.utils.Consts.shippingTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends BaseFragment implements Callback {


    RecyclerView recyclerView;

    PaymentTypesAdapter adapter;


    OnFragmentInteractionListener listener;

    /**
     * once the payment types chosen, listener invokes Prices Fragment
     */

    @Override
    public void onItemClick(int priceId) {
        if (listener.getCompleteApi().getOrderObject().getId_payment_type() == null) {
            listener.getCompleteApi().getOrderObject().setId_payment_type(priceId);
            listener.transactionFragments(ShippingDateFragment.newInstance(), shippingTag);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StateProgressBar progressBar = getActivity().findViewById(R.id.progress_state);
        progressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);

        recyclerView = view.findViewById(R.id.payment_recycler);

        getActivity().findViewById(R.id.btn_filter).setVisibility(View.GONE);
        getActivity().findViewById(R.id.btn_search).setVisibility(View.GONE);

        if (getActivity() != null) {
            getActivity().findViewById(R.id.btn_back).setOnClickListener(v -> {
                getActivity().findViewById(R.id.btn_filter).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.btn_search).setVisibility(View.VISIBLE);
                listener.transactionFragments(ProductFragment.newInstance(), productTag);
                //listener.onBackClick(visitTag);
            });

            getActivity().findViewById(R.id.btn_forward).setOnClickListener(v -> {
                if (listener.getCompleteApi().getOrderObject().getId_payment_type() != null)
                    listener.transactionFragments(ShippingDateFragment.newInstance(), shippingTag);
                else
                    Toast.makeText(getActivity(), "Choose payment type", Toast.LENGTH_SHORT).show();

            });
        }


        adapter = new PaymentTypesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setCallback(this);
        adapter.updateList(listener.getCompleteObject().getPaymentTypeList());

    }


    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance() {
        PaymentFragment paymentFragment = new PaymentFragment();
        return paymentFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_types, container, false);
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
