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
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.dailyPlan.Adapter.DailyCallBack;
import uz.codic.ahmadtea.ui.dailyPlan.Adapter.DailyAdapter;
import uz.codic.ahmadtea.ui.login.LoginActivity;
import uz.codic.ahmadtea.ui.map.MerchantsMapActivity;
import uz.codic.ahmadtea.ui.merchants.MerchantListWorspaces;
import uz.codic.ahmadtea.utils.Consts;

import static uz.codic.ahmadtea.ui.MainActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends BaseFragment implements DailyMvpView, DailyCallBack {

    DailyAdapter adapter;
    DailyAdapter outOfDailyAdapter;
    DailyAdapter allMerchantsAdapter;

    RecyclerView recyclerView;

    TextView merchants_size;

    DailyMvpPresenter<DailyMvpView> presenter;

    String id_employee;
    List<WorkspaceAndMerchant> dailyMerchants;
    List<WorkspaceAndMerchant> outOfDailyMerchants;
    List<WorkspaceAndMerchant> allDailyMerchants;

    List<MerchantListWorspaces> merchantListWorspaces;
    List<MerchantListWorspaces> outOfmerchantsWorkspace;
    List<MerchantListWorspaces> allDailyMerchantListWorspaces;
    private int mYear, mMonth, mDay;
    String date;
    private int whichPage = 0;

    public DailyFragment() {
        // Required empty public constructor
    }

    public static DailyFragment newInstance(int whichPage){
        DailyFragment dailyFragment = new DailyFragment();
        Bundle args = new Bundle();
        args.putInt("page",whichPage);
        dailyFragment.setArguments(args);
        return dailyFragment;
    }
    public static DailyFragment newInstance(){
        return new DailyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments()!=null)whichPage = getArguments().getInt("page");
        return inflater.inflate(R.layout.fragment_daily_planning, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.daily_planning_recycler);
        merchants_size = view.findViewById(R.id.id_tv_merchant_size);

        id_employee = getActivity().getIntent().getStringExtra("id_employee");

        Log.d(Consts.TEST_TAG, "onViewCreated: " + id_employee);

        adapter = new DailyAdapter(getContext());
        outOfDailyAdapter = new DailyAdapter(getContext());
        allMerchantsAdapter = new DailyAdapter(getContext());
        adapter.setCallBack(this);
        outOfDailyAdapter.setCallBack(this);
        allMerchantsAdapter.setCallBack(this);
        presenter = new DailyPresenter<>(getContext());
        presenter.onAttach( this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (whichPage == 1 || whichPage == 0){
            recyclerView.setAdapter(adapter);
        }else if(whichPage == 2){
            recyclerView.setAdapter(outOfDailyAdapter);
        }else if (whichPage == 3){
            recyclerView.setAdapter(allMerchantsAdapter);
        }
//        presenter.getMerchantsInWorkspace(id_employee);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        date = df.format("yyyy-MM-dd", new java.util.Date()).toString();
        presenter.requestDailyMerchants(date);
    }

    @Override
    public void onMerchantsListReady(List<WorkspaceAndMerchant> dailyMerchants,List<WorkspaceAndMerchant> outOfDailyMerchants1) {
       // Log.d(TAG, "onMerchantsListReady: OKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOK " + dailyMerchants.size() + " ______ " + outOfDailyMerchants.size());
        this.dailyMerchants = dailyMerchants;
        merchants_size.setText("Merchants: " + dailyMerchants.size());
        allDailyMerchants = new ArrayList<>();
        allDailyMerchants.addAll(outOfDailyMerchants1);
        this.outOfDailyMerchants = outOfDailyMerchants1;
        for (int i = 0; i < outOfDailyMerchants1.size(); i++) {
            for (int j = 0; j < dailyMerchants.size(); j++) {
                if (outOfDailyMerchants1.get(i).getMerchant().getId().equals(dailyMerchants.get(j).getMerchant().getId())){
                    this.outOfDailyMerchants.remove(i); //7a303b56-114b-4db1-9a73-4c83855e889b
                    break;
                }

            }
        }
//        allDailyMerchants.addAll(dailyMerchants);
//        allDailyMerchants.addAll(this.outOfDailyMerchants);
        if (whichPage == 1 || whichPage == 0 )merchants_size.setText("Merchants: " + dailyMerchants.size());
        else if (whichPage == 2)merchants_size.setText("Merchants: " + outOfDailyMerchants.size());
        else if (whichPage == 3) merchants_size.setText("Merchants: " + allDailyMerchants.size());
        adapter.updateList(dailyMerchants);
        outOfDailyAdapter.updateList(outOfDailyMerchants);
        allMerchantsAdapter.updateList(allDailyMerchants);
        merchantListWorspaces = new ArrayList<>();
        outOfmerchantsWorkspace = new ArrayList<>();
        allDailyMerchantListWorspaces = new ArrayList<>();

        for (WorkspaceAndMerchant merchant:dailyMerchants) {
            MerchantListWorspaces newMerchantListWorspaces  = new MerchantListWorspaces();
            newMerchantListWorspaces.setMerchant(merchant.getMerchant());
            newMerchantListWorspaces.setWorkspace(merchant.getWorkspace());
            merchantListWorspaces.add(newMerchantListWorspaces);
        }
        for (WorkspaceAndMerchant merchant:outOfDailyMerchants) {
            MerchantListWorspaces newMerchantListWorspaces  = new MerchantListWorspaces();
            newMerchantListWorspaces.setMerchant(merchant.getMerchant());
            newMerchantListWorspaces.setWorkspace(merchant.getWorkspace());
            outOfmerchantsWorkspace.add(newMerchantListWorspaces);
        }
        for (WorkspaceAndMerchant merchant:allDailyMerchants) {
            MerchantListWorspaces newMerchantListWorspaces  = new MerchantListWorspaces();
            newMerchantListWorspaces.setMerchant(merchant.getMerchant());
            newMerchantListWorspaces.setWorkspace(merchant.getWorkspace());
            allDailyMerchantListWorspaces.add(newMerchantListWorspaces);
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
        if (whichPage == 0 || whichPage == 1){
            intent.putExtra("dailyMerchant", (Serializable) merchantListWorspaces);
        }else if (whichPage == 2){
            intent.putExtra("dailyMerchant", (Serializable) outOfmerchantsWorkspace);
        }else if (whichPage == 3){
            intent.putExtra("dailyMerchant", (Serializable) allDailyMerchantListWorspaces);
        }

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

    public void filter(){
        presenter.getMyWorkspaces();
    }

    @Override
    public void onReadyMyWorkspaces(List<Workspace> workspaces) {
        Workspace allworkspace = new Workspace();
        allworkspace.setLabel("All Workspaces");
        workspaces.add(0, allworkspace);
        List<String> nameWorkspaces = new ArrayList<>();
        for (int i = 0; i < workspaces.size(); i++) {
            nameWorkspaces.add(workspaces.get(i).getLabel());
        }
        CharSequence[] items = nameWorkspaces.toArray(new CharSequence[0]);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    adapter.updateList(dailyMerchants);
                    outOfDailyAdapter.updateList(outOfDailyMerchants);
                    allMerchantsAdapter.updateList(allDailyMerchants);
                    merchants_size.setText("Merchants: " + dailyMerchants.size());
                }else getMerchatsInWorkspace(workspaces.get(which));
            }
        });
        builder.show();
    }

    private void getMerchatsInWorkspace(Workspace workspace) {
        List<WorkspaceAndMerchant> andMerchants = new ArrayList<>();
        for (WorkspaceAndMerchant merchant :dailyMerchants) {
            if (merchant.getWorkspace().getId().equals(workspace.getId())){
                andMerchants.add(merchant);
            }
        }
        adapter.updateList(andMerchants);
        outOfDailyAdapter.updateList(outOfDailyMerchants);
        allMerchantsAdapter.updateList(allDailyMerchants);
        merchants_size.setText("Merchants: " + andMerchants.size());
    }

    @Override
    public void goLoginActivity(String error_label) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra("error_label", error_label);
        intent.putExtra("isFirstTime", false);
        startActivity(intent);
    }
}
