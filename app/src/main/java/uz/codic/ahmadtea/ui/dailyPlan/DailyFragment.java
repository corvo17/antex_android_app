package uz.codic.ahmadtea.ui.dailyPlan;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.dailyPlan.Adapter.DailyCallBack;
import uz.codic.ahmadtea.ui.dailyPlan.Adapter.DailyAdapter;
import uz.codic.ahmadtea.ui.login.LoginActivity;
import uz.codic.ahmadtea.ui.map.MerchantsMapActivity;
import uz.codic.ahmadtea.ui.merchants.MerchantListWorspaces;
import uz.codic.ahmadtea.utils.Consts;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends BaseFragment implements DailyMvpView, DailyCallBack {

    DailyAdapter adapter;

    RecyclerView recyclerView;

    DailyMvpPresenter<DailyMvpView> presenter;

    String id_employee;
    List<WorkspaceAndMerchant> dailyMerchants;
    List<MerchantListWorspaces> merchantListWorspaces;
    private int mYear, mMonth, mDay;
    String date;

    public DailyFragment() {
        // Required empty public constructor
    }

    public static DailyFragment newInstance(){
        return new DailyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_planning, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.daily_planning_recycler);

        id_employee = getActivity().getIntent().getStringExtra("id_employee");

        Log.d(Consts.TEST_TAG, "onViewCreated: " + id_employee);

        adapter = new DailyAdapter(getContext());
        adapter.setCallBack(this);
        presenter = new DailyPresenter<>(getContext());
        presenter.onAttach( this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
//        presenter.getMerchantsInWorkspace(id_employee);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        date = df.format("yyyy-MM-dd", new java.util.Date()).toString();
        presenter.requestDailyMerchants(date);
    }

    @Override
    public void onMerchantsListReady(List<WorkspaceAndMerchant> dailyMerchants,List<WorkspaceAndMerchant> outOfDailyMerchants) {
        dailyMerchants = dailyMerchants;
        adapter.updateList(dailyMerchants);
        merchantListWorspaces = new ArrayList<>();
        for (WorkspaceAndMerchant merchant:dailyMerchants) {
            MerchantListWorspaces newMerchantListWorspaces  = new MerchantListWorspaces();
            newMerchantListWorspaces.setMerchant(merchant.getMerchant());
            newMerchantListWorspaces.setWorkspace(merchant.getWorkspace());
            merchantListWorspaces.add(newMerchantListWorspaces);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestDailyMerchants(date);
    }

    @Override
    public String id_workspace(String id_merchant) {
        return null;
    }

    public void openMap(){

        Intent intent = new Intent(getActivity(), MerchantsMapActivity.class);
        intent.putExtra("isPlan", true);
        intent.putExtra("dailyMerchant", (Serializable) merchantListWorspaces);
        startActivity(intent);
    }

    public void openCanlendar(){
        //CalendarContract.CalendarAlerts.
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = year +"-0"+(month+1)+"-"+dayOfMonth;
                presenter.requestDailyMerchants(date);
            }
        },mYear, mMonth, mDay);
        dialog.show();


    }

    @Override
    public void goLoginActivity(String error_label) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra("error_label", error_label);
        intent.putExtra("isFirstTime", false);
        startActivity(intent);
    }
}
