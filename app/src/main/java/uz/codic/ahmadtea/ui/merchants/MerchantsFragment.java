package uz.codic.ahmadtea.ui.merchants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.ui.add_merchant.AddMerchantActivity;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.mainpage.FragmentsListener;
import uz.codic.ahmadtea.ui.map.MerchantsMapActivity;
import uz.codic.ahmadtea.ui.merchants.adapter.CallBackMerchants;
import uz.codic.ahmadtea.ui.merchants.adapter.MerchantsAdapter;
import uz.codic.ahmadtea.utils.CyrillicLatin;

@SuppressLint("NewApi")
public class MerchantsFragment extends BaseFragment implements MerchantsMvpView, CallBackMerchants {

    private MerchantsMvpPresenter<MerchantsMvpView> presenter;
    private RecyclerView recyclerView;
    private MerchantsAdapter adapter;
    public static List<MerchantListWorspaces> merchants;
    private CyrillicLatin cyrillicLatin;
    private boolean isChecked;
    FragmentsListener listener;
    private FloatingActionButton add_merchant_bn;
    boolean isScroll = false;
    private ImageButton go_to_top;
    private TextView tv_merchants_size;

    public MerchantsFragment() {
        // Required empty public constructor
    }


    public static MerchantsFragment newInstance() {
        MerchantsFragment fragment = new MerchantsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_merchants, container, false);
    }

    @SuppressLint({"RestrictedApi", "NewApi"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //# create and connect presenter
        presenter = new MerchantsPresenter<>(getContext());
        presenter.onAttach(this);

        cyrillicLatin = new CyrillicLatin();

        //# binding recyclerView
        recyclerView = view.findViewById(R.id.id_merchents_recycler_view);
        add_merchant_bn = view.findViewById(R.id.id_add_merchant_fab);
        go_to_top = view.findViewById(R.id.id_merchents_go_top);
        tv_merchants_size = view.findViewById(R.id.id_tv_merchant_size);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //# defining adapter
        adapter = new MerchantsAdapter();
        adapter.setCallback(this);
        //# add adapter to recyclerview
        recyclerView.setAdapter(adapter);
        if (presenter.isAdmin()){
            add_merchant_bn.setVisibility(View.VISIBLE);
//            if (listener.getmMerchants()!= null){
//                presenter.getMerchantListWorkspaces(listener.getmMerchants());
//            }else
                presenter.getWorkspaceAndMerchants();
        }else {
            add_merchant_bn.setVisibility(View.GONE);
//            if (listener.getmMerchants()!= null){
//                Log.d("baxtiyor", "m size: " + listener.getmMerchants().size());
//                presenter.getMerchantListWorkspaces(listener.getmMerchants());
//            }else
                presenter.getWorkspaceAndMerchants();
        }

        add_merchant_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), AddMerchantActivity.class));
            }
        });

        go_to_top.animate().translationY(500).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(0);
        go_to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });

    }


    @Override
    public void openMapsActivity(int pid) {
        Intent intent = new Intent(getContext(), MerchantsMapActivity.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentsListener){
            listener = (FragmentsListener) context;
        }else {
            throw new RuntimeException(context.toString() + "must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void getMerchants(List<MerchantListWorspaces> merchants) {
        adapter.setMerchants(merchants);
        this.merchants = merchants;
        tv_merchants_size.setText("Merchants: " + merchants.size());
    }

    @Override
    public void onReadyMerchantsInWorkspace(List<MerchantListWorspaces> merchantInWorkspace) {
        adapter.setMerchants(merchantInWorkspace);
        this.merchants = merchantInWorkspace;
        tv_merchants_size.setText("Merchants: " + merchantInWorkspace.size());
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
        CharSequence [] items = nameWorkspaces.toArray(new CharSequence[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.getMerchantsInWorkspace(workspaces.get(which).getId());
                listener.setToolbarTitle(workspaces.get(which).getLabel());
            }
        });
        builder.show();


    }

    @Override
    public Context getMerchantContext() {
        return getContext();
    }

    @Override
    public Activity getThisActivity() {
        return getActivity();
    }

    public void openMap(){

        Intent intent = new Intent(getActivity(), MerchantsMapActivity.class);
        startActivity(intent);
    }

    public void filter(){
        presenter.getMyWorkspaces();
    }

    public void searcMerchant(String text){
        MerchantsAdapter resultAdapter = new MerchantsAdapter();
        List<MerchantListWorspaces> resultMerchants = new ArrayList<>();
        String otherVersion = cyrillicLatin.getOtherVersion(text);
        text = text.toLowerCase();
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getMerchant().getLabel().toLowerCase().indexOf(text)>=0
                    || merchants.get(i).getMerchant().getLabel().toLowerCase().indexOf(otherVersion)>=0
                    || merchants.get(i).getMerchant().getLabel().toLowerCase().indexOf(replaceStringToCyrilli(text))>=0){
                resultMerchants.add(0, merchants.get(i));
            }
        }
        resultAdapter.setMerchants(resultMerchants);
        tv_merchants_size.setText("Merchants: " + resultMerchants.size());
        resultAdapter.setCallback(this);
        recyclerView.setAdapter(resultAdapter);
        if (text.equals("")){
            recyclerView.setAdapter(adapter);
            tv_merchants_size.setText("Merchants: " + merchants.size());
        }

    }

    public String replaceStringToCyrilli(String string){

        ///lotin
        string = string.replace("ch", "ч");
        string = string.replace("sh", "ш");
        string = string.replace("ya", "я");
        string = string.replace("yo", "ё");
        string = string.replace("yu", "ю");
        string = string.replace("a", "а");
        string = string.replace("b", "б");
        string = string.replace("c", "с");
        string = string.replace("d", "д");
        string = string.replace("e", "э");
        string = string.replace("f", "ф");
        string = string.replace("g", "г");
        string = string.replace("h", "х");
        string = string.replace("i", "и");
        string = string.replace("j", "ж");
        string = string.replace("k", "к");
        string = string.replace("l", "л");
        string = string.replace("m", "м");
        string = string.replace("n", "н");
        string = string.replace("o", "о");
        string = string.replace("p", "п");
        string = string.replace("q", "қ");
        string = string.replace("r", "р");
        string = string.replace("s", "с");
        string = string.replace("t", "т");
        string = string.replace("u", "у");
        string = string.replace("v", "в");
        string = string.replace("w", "в");
        string = string.replace("x", "х");
        string = string.replace("y", "й");
        string = string.replace("z", "з");

        return string;
    }

    @Override
    public void changedPosition(int position) {
        if (position>20 /*&& go_to_top.getVisibility() == View.GONE*/){
            //lnrButtons.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(600);
            //go_to_top.setVisibility(View.VISIBLE);
            go_to_top.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(600);
        }else if (position<20 /*&& go_to_top.getVisibility() == View.VISIBLE*/){
            go_to_top.animate().translationY(500).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(600);
            //go_to_top.setVisibility(View.GONE);
        }
    }
}
