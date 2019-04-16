package uz.codic.ahmadtea.ui.orders.basketList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.base.BaseActivity;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.BasketAdapter;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.BasketProduct;

public class BasketActivity extends BaseActivity implements BasketMvpView {

    BasketMvpPresenter<BasketMvpView> presenter;
    BasketAdapter adapter;
    RecyclerView  recyclerView;

    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        btnBack = findViewById(R.id.btn_back);
        presenter = new BasketPresenter<>(this);
        presenter.onAttach(this);
        recyclerView = findViewById(R.id.ordered_list_recycler_view);
        adapter = new BasketAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        int priceId = getIntent().getIntExtra("priceId",0);
        String orderId = getIntent().getStringExtra("orderId");
        presenter.getBasketList(priceId, orderId);

        btnBack.setOnClickListener(v -> finish());

    }

    @Override
    public void onBasketListReady(List<BasketProduct> basketProducts) {
        adapter.updateList(basketProducts);
    }
}
