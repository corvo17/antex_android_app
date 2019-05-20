package uz.codic.ahmadtea.ui.synchronisation;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andremion.counterfab.CounterFab;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.report.adapter.OrderedList;
import uz.codic.ahmadtea.ui.synchronisation.setOrder.SetOrderActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SynchronisationFragment extends BaseFragment implements SynchronisationMvpView {

    SynchronisationMvpPresenter<SynchronisationMvpView> presenter;
    TextView synchLabel, working, lastSynch;
    Button tv_start_sync;
    ProgressBar progressBar;
    CharSequence[] items;

    LinearLayout container;

    private CounterFab counterFab;
    int pendings;


    public SynchronisationFragment() {
        // Required empty public constructor
    }

    public static SynchronisationFragment newInstance() {
        return new SynchronisationFragment();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_synchronisation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

    }

    private void init(View view) {
        presenter = new SynchronisationPresenter<>(getContext());
        presenter.onAttach(this);

        container = view.findViewById(R.id.container);
        synchLabel = view.findViewById(R.id.synch);
        working = view.findViewById(R.id.working);
        lastSynch = view.findViewById(R.id.time);
        tv_start_sync = view.findViewById(R.id.tv_start_synchronisation);
        progressBar = view.findViewById(R.id.progress_bar);
        counterFab = view.findViewById(R.id.counter_fab);
        counterFab.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select Order")
                    .setItems(items, (dialog, which) -> {
                        OrderedList ordered = presenter.getOrder(which);
                        Intent intent = new Intent(getActivity(), SetOrderActivity.class);
                        //intent.putExtra("order", (Serializable) ordered);
                        intent.putExtra("order", ordered);
                        startActivity(intent);
                    });
            builder.show();

        });


        presenter.getLastSyncTime();
        presenter.getPendingSize();


        tv_start_sync.setOnClickListener(v -> {
            synchLabel.setText("Synchronization...");
            progressBar.setVisibility(View.VISIBLE);
            synchLabel.setVisibility(View.VISIBLE);
            //working.setVisibility(View.INVISIBLE);
            container.setVisibility(View.INVISIBLE);
            presenter.startSynchronisation();
        });

//        tv_pendings.setOnClickListener(v -> {
//            if (items != null) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Select Order")
//                        .setItems(items, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                OrderedList ordered = presenter.getOrder(which);
//                                Intent intent = new Intent(getActivity(), SetOrderActivity.class);
//                                //intent.putExtra("order", (Serializable) ordered);
//                                intent.putExtra("order", (Serializable) ordered);
//                                startActivity(intent);
//                            }
//                        });
//                builder.show();
//            }
//        });
    }

    public void showPendings(List<String> pendingNames) {
        if (pendingNames != null) {
            items = pendingNames.toArray(new CharSequence[0]);
        } else items = null;
    }

    @Override
    public void relustSync(String result) {
        synchLabel.setText("Synchronization succesfull");
        //working.setVisibility(View.VISIBLE);
        //working.setText(result);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void relustPendingSize(int pendingCount) {
        pendings = pendingCount;
        if (pendingCount == 0) {
            counterFab.setEnabled(false);
        } else {
            counterFab.setEnabled(true);
            counterFab.setCount(pendingCount);
        }
        presenter.pendingNames();

    }

    @Override
    public void showLastSyncTime(String lastSyncTime) {
        lastSynch.setText(lastSyncTime);
    }
}
