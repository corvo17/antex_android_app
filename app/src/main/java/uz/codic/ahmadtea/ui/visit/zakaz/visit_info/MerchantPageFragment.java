package uz.codic.ahmadtea.ui.visit.zakaz.visit_info;


import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.AppDataManager;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.ui.visit.zakaz.InformationFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;
import uz.codic.ahmadtea.utils.CommonUtils;

import static uz.codic.ahmadtea.utils.Consts.informationTag;
import static uz.codic.ahmadtea.utils.Consts.statusPending;
import static uz.codic.ahmadtea.utils.Consts.statusSaveAsDraft;
import static uz.codic.ahmadtea.utils.Consts.statusSendAsDraft;
import static uz.codic.ahmadtea.utils.Consts.statusSent;
import static uz.codic.ahmadtea.utils.Consts.visitTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantPageFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    LinearLayout lnr_visit;
    LinearLayout lnr_information;
    RecyclerView recyclerView;
    VisitInfoAdapter adapter;
    AppDataManager dataManager;
    boolean gps_enabled = false;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lnr_visit = view.findViewById(R.id.lnr_visit);
        lnr_information = view.findViewById(R.id.lnr_information);
        recyclerView = view.findViewById(R.id.info_action_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VisitInfoAdapter();
        recyclerView.setAdapter(adapter);
        dataManager = new AppDataManager(getContext());
        getVisits();
        LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);

        //Back Button
        if(getActivity()!= null){
            getActivity().findViewById(R.id.btn_back).setOnClickListener(v -> getActivity().onBackPressed());
        }

        //Visit fragment
        lnr_visit.setOnClickListener(v ->{
//            try {
//                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            if (gps_enabled) {
//                listener.getLocation();
            getActivity().findViewById(R.id.lnr_buttons).setVisibility(View.VISIBLE);
                listener.transactionFragments(VisitFragment.newInstance(), visitTag);
//            }
//            else {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
//                dialog.setMessage("Turn On Location");
//                dialog.setPositiveButton("ON", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        getContext().startActivity(intent);
//                    }
//                });
//                dialog.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                dialog.show();
//            }

        } );

        lnr_information.setOnClickListener(v ->
                listener.transactionFragments(InformationFragment.newInstance(), informationTag));
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        }else {
            throw new RuntimeException(context.toString() + "must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public MerchantPageFragment() {
        // Required empty public constructor
    }

    public static MerchantPageFragment newInstance(){
        MerchantPageFragment fragment = new MerchantPageFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_merchant_page, container, false);
    }

    private void getVisits() {
        dataManager.getVisitInfoObjects(listener.getCompleteObject().getMerchant().getId(), listener.getCompleteObject().getWorkspace().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Order>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Order> orders) {
                        Log.d("baxtiyor", "onSuccess: " + orders);
                        List<Order> filterOrders = new ArrayList<>();
                        for (Order order :orders) {
                            if (order.getDate().equals(CommonUtils.getToday()) || order.getStatus().equals(statusPending) || order.getStatus().equals(statusSaveAsDraft)){
                                filterOrders.add(order);
                            }
                        }
                        adapter.setOrders(filterOrders);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


}
