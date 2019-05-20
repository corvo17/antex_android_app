package uz.codic.ahmadtea.ui.dashboard;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.data.network.model.DailyMerchants;
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
        this.outOfDailyMerchants = outOfDailyMerchants1;
        for (int i = 0; i < outOfDailyMerchants1.size(); i++) {
            for (int j = 0; j < dailyMerchants.size(); j++) {
                if (outOfDailyMerchants1.get(i).getMerchant().getId().equals(dailyMerchants.get(j).getMerchant().getId())){
                    outOfDailyMerchants.remove(i); //7a303b56-114b-4db1-9a73-4c83855e889b
                    break;
                }

            }
        }
        if (outOfDailyMerchants1.size() >= doneMerchants.size()){
            outOfDailyMerchants1.remove(doneMerchants);
        }
//        for (WorkspaceAndMerchant item : outOfDailyMerchants) {
//            if (item.getInfoAction().isSend_draft() && item.getInfoAction().isSend()){
//                this.outOfDailyMerchants.add(item);
//            }
//        }
        tv_count.setText(this.dailyMerchants.size()+ "/" + doneMerchants.size());
        tv_count1.setText(this.outOfDailyMerchants.size()+"");
        tv_count2.setText(doneMerchants.size()+this.outOfDailyMerchants.size()+"");
    }
}
