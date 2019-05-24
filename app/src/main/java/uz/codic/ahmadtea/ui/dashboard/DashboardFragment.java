package uz.codic.ahmadtea.ui.dashboard;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.utils.CommonUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends BaseFragment implements DashboardMvpView {

    DashboardPresenter<DashboardMvpView> presenter;
    private TextView tv_count, tv_count1, tv_count2, tv_sum, tv_sum1, tv_sum2;
    private static IUpdateDashboard updateDashboard;
    private List<WorkspaceAndMerchant> dailyMerchants, outOfDailyMerchants, doneMerchants;
    private Context context;
    private long totalSum1 = 0, totalSum12 = 0, totalSum13 = 0;

    public static void setUpdater(IUpdateDashboard updater){
        updateDashboard = updater;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        //presenter.send();
        presenter.requestDailyMerchants(CommonUtils.getToday());

    }

    private void initViews(View view) {
        context = view.getContext();
        presenter = new DashboardPresenter<>(getContext());
        presenter.onAttach(this);
        tv_count = view.findViewById(R.id.tVDailyPlanCount);
        tv_count1 = view.findViewById(R.id.tv_done_order_count);
        tv_count2 = view.findViewById(R.id.tv_order_count3);
        tv_sum = view.findViewById(R.id.tv_daily_sum);
        tv_sum1 = view.findViewById(R.id.tv_sum2);
        tv_sum2 = view.findViewById(R.id.tv_sum3);
        tv_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDashboard.updateDailyPlan();
            }
        });
        tv_count1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDashboard.updateDailyOutOfDaily();
            }
        });
        tv_count2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDashboard.updateAllDoneVisits();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }


    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment getInstance() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        return dashboardFragment;
    }
    public interface IUpdateDashboard{
         void updateDailyPlan();
         void updateDailyOutOfDaily();
         void updateAllDoneVisits();
    }

    @Override
    public void onMerchantsListReady(List<WorkspaceAndMerchant> dailyMerchants, List<WorkspaceAndMerchant> outOfDailyMerchants1) {
        //outOfDailyMerchants.remove(dailyMerchants);
        this.dailyMerchants = dailyMerchants;

        List<WorkspaceAndMerchant>allDailyMerchants = new ArrayList<>();
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


        doneMerchants = new ArrayList<>();
        for (WorkspaceAndMerchant it : dailyMerchants ) {
            if (it.getInfoAction() != null){
                if (it.getInfoAction().isSend() || it.getInfoAction().isSend_draft()){
                    doneMerchants.add(it);
                }
            }
        }
//        for (WorkspaceAndMerchant item : outOfDailyMerchants1) {
//            if (item.getInfoAction() != null){
//                if (!item.getInfoAction().isSend() || !item.getInfoAction().isSend_draft()){
//                    outOfDailyMerchants1.remove(item);
//                }
//            }else outOfDailyMerchants1.remove(item);
//        }
//        this.outOfDailyMerchants = new ArrayList<>();
//        outOfDailyMerchants.addAll(outOfDailyMerchants1);
//        for (int i = 0; i < outOfDailyMerchants1.size(); i++) {
//            for (int j = 0; j < dailyMerchants.size(); j++) {
//                if (outOfDailyMerchants1.get(i).getMerchant().getId().equals(dailyMerchants.get(j).getMerchant().getId())){
//                    this.outOfDailyMerchants.remove(i); //7a303b56-114b-4db1-9a73-4c83855e889b
//                    break;
//                }
//
//            }
//        }
//        if (outOfDailyMerchants1.size() >= doneMerchants.size()){
//            outOfDailyMerchants1.remove(doneMerchants);
//        }
//        for (WorkspaceAndMerchant item : outOfDailyMerchants) {
//            if (item.getInfoAction().isSend_draft() && item.getInfoAction().isSend()){
//                this.outOfDailyMerchants.add(item);
//            }
//        }
        android.support.v4.util.ArraySet<WorkspaceAndMerchant> set = new ArraySet<>();
        set.addAll(outOfDailyMerchants1);
        tv_count.setText(this.dailyMerchants.size()+ "/" + doneMerchants.size());
        tv_count1.setText(this.outOfDailyMerchants.size()+"");
        tv_count2.setText(doneMerchants.size()+this.outOfDailyMerchants.size()+"");

        presenter.getAllOrders();
    }

    @Override
    public void onOrdersReady(List<Order> orders) {
        for (Order it : orders) {
            for (int i = 0; i < doneMerchants.size(); i++) {
                if (it.getId_merchant().equals(doneMerchants.get(i).getInfoAction().getId_merchant()))
                    totalSum1 = totalSum1 + it.getTotal_cost();
            }

            for (int i = 0; i < outOfDailyMerchants.size(); i++) {
                if (it.getId_merchant().equals(outOfDailyMerchants.get(i).getInfoAction().getId_merchant()))
                    totalSum12 = totalSum12 + it.getTotal_cost();
            }
        }
            totalSum1 = totalSum1/100;
            totalSum12 = totalSum12/100;
            totalSum13 = totalSum1 + totalSum12;
            String str1, str2, str3;
            str1 = totalSum1+"";
            str2 = totalSum12+"";
            str3 = totalSum13+"";

            str1 = "" + totalSum1;
            str2 = "" + totalSum12;
            str3 = "" + totalSum13;
            String newStr = new String();
            if (str1.toCharArray().length%3 == 0 && str1.toCharArray().length > 3 ){
                if (str1.toCharArray().length == 6){
                    for (int i = 0; i < str1.length(); i ++){
                        newStr += str1.charAt(i);
                        if (i==2)newStr = newStr + " ";
                    }
                }
                if (str1.toCharArray().length == 9){
                    for (int i = 0; i < str1.length(); i ++){
                        newStr += str1.charAt(i);
                        if (i==2 || i == 5)newStr = newStr + " ";
                    }
                }
                str1 = newStr;
            }else if (str1.toCharArray().length%3 == 1 && str1.toCharArray().length > 3){
                if (str1.toCharArray().length == 4){
                    for (int i = 0; i < str1.length(); i ++){
                        newStr += str1.charAt(i);
                        if (i==0)newStr = newStr + " ";
                    }
                }
                if (str1.toCharArray().length == 7 ){
                    for (int i = 0; i < str1.length(); i ++){
                        newStr += str1.charAt(i);
                        if (i==0 || i == 3)newStr = newStr + " ";
                    }
                }
                str1 = newStr;
            }else if (str1.toCharArray().length%3 == 2 && str1.toCharArray().length > 3){
                if (str1.toCharArray().length == 5){
                    for (int i = 0; i < str1.length(); i ++){
                        newStr += str1.charAt(i);
                        if (i==1)newStr = newStr + " ";
                    }
                }
                if (str1.toCharArray().length == 8){
                    for (int i = 0; i < str1.length(); i ++){
                        newStr += str1.charAt(i);
                        if (i==1 || i == 4)newStr = newStr + " ";
                    }
                }
                str1 = newStr;
            }

            newStr = "";
            if (str2.toCharArray().length%3 == 0 && str2.toCharArray().length > 3 ){
                if (str2.toCharArray().length == 6){
                    for (int i = 0; i < str2.length(); i ++){
                        newStr += str2.charAt(i);
                        if (i==2)newStr = newStr + " ";
                    }
                }
                if (str2.toCharArray().length == 9){
                    for (int i = 0; i < str2.length(); i ++){
                        newStr += str2.charAt(i);
                        if (i==2 || i == 5)newStr = newStr + " ";
                    }
                }
                str2 = newStr;
            }else if (str2.toCharArray().length%3 == 1 && str2.toCharArray().length > 3){
                if (str2.toCharArray().length == 4){
                    for (int i = 0; i < str2.length(); i ++){
                        newStr += str2.charAt(i);
                        if (i==0)newStr = newStr + " ";
                    }
                }
                if (str2.toCharArray().length == 7 ){
                    for (int i = 0; i < str2.length(); i ++){
                        newStr += str2.charAt(i);
                        if (i==0 || i == 3)newStr = newStr + " ";
                    }
                }
                str2 = newStr;
            }else if (str2.toCharArray().length%3 == 2 && str2.toCharArray().length > 3){
                if (str2.toCharArray().length == 5){
                    for (int i = 0; i < str2.length(); i ++){
                        newStr += str2.charAt(i);
                        if (i==1)newStr = newStr + " ";
                    }
                }
                if (str2.toCharArray().length == 8){
                    for (int i = 0; i < str2.length(); i ++){
                        newStr += str2.charAt(i);
                        if (i==1 || i == 4)newStr = newStr + " ";
                    }
                }
                str2 = newStr;
            }

            newStr = "";
            if (str3.toCharArray().length%3 == 0 && str3.toCharArray().length > 3 ){
                if (str3.toCharArray().length == 6){
                    for (int i = 0; i < str3.length(); i ++){
                        newStr += str3.charAt(i);
                        if (i==2)newStr = newStr + " ";
                    }
                }
                if (str3.toCharArray().length == 9){
                    for (int i = 0; i < str3.length(); i ++){
                        newStr += str3.charAt(i);
                        if (i==2 || i == 5)newStr = newStr + " ";
                    }
                }
                str3 = newStr;
            }else if (str3.toCharArray().length%3 == 1 && str3.toCharArray().length > 3){
                if (str3.toCharArray().length == 4){
                    for (int i = 0; i < str3.length(); i ++){
                        newStr += str3.charAt(i);
                        if (i==0)newStr = newStr + " ";
                    }
                }
                if (str3.toCharArray().length == 7 ){
                    for (int i = 0; i < str3.length(); i ++){
                        newStr += str3.charAt(i);
                        if (i==0 || i == 3)newStr = newStr + " ";
                    }
                }
                str3 = newStr;
            }else if (str3.toCharArray().length%3 == 2 && str3.toCharArray().length > 3){
                if (str3.toCharArray().length == 5){
                    for (int i = 0; i < str3.length(); i ++){
                        newStr += str3.charAt(i);
                        if (i==1)newStr = newStr + " ";
                    }
                }
                if (str3.toCharArray().length == 8){
                    for (int i = 0; i < str3.length(); i ++){
                        newStr += str3.charAt(i);
                        if (i==1 || i == 4)newStr = newStr + " ";
                    }
                }
                str3 = newStr;
            }




            String totalCost1 = "Sum : " + str1;
            String totalCost2 = "Sum : " + str2;
            String totalCost3 = "Sum : "  + str3;
            tv_sum.setText(totalCost1);
            tv_sum1.setText(totalCost2);
            tv_sum2.setText(totalCost3);
    }

}
