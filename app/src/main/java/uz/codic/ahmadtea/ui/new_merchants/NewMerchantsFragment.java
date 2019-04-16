package uz.codic.ahmadtea.ui.new_merchants;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.new_merchants.adapter.AdapterNewMerchants;

public class NewMerchantsFragment extends BaseFragment implements NewMerchantsMvpView {

    NewMerchantsMvpPresenter<NewMerchantsMvpView> presenter;
    RecyclerView recyclerView;
    AdapterNewMerchants adapter;

    public NewMerchantsFragment() {
    }

    public static NewMerchantsFragment newInstance() {
        NewMerchantsFragment fragment = new NewMerchantsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_merchants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new NewMerchantsPresenter<>(getActivity());
        presenter.onAttach(this);

        recyclerView = getActivity().findViewById(R.id.id_new_merchents_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterNewMerchants();
        recyclerView.setAdapter(adapter);

        presenter.getNewMerchants();


    }

    @Override
    public void resultNewMerchants(List<NewMerchant> merchants) {
        adapter.setMerchants(merchants);
    }
}
