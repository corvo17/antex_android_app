package uz.codic.ahmadtea.ui.report.basketList;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.base.BaseActivity;
import uz.codic.ahmadtea.ui.report.basketList.adapter.BasketProduct;
import uz.codic.ahmadtea.ui.report.basketList.adapter.PagerAdapter;

public class BasketActivity extends BaseActivity implements BasketMvpView {

    private static final String TAG = "TAG";
    BasketMvpPresenter<BasketMvpView> presenter;
   // RecyclerView  recyclerView;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
    private Order order;
    private List<Comment> list;
    private Visit visit;

    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        init();
        btnBack = findViewById(R.id.btn_back);
        presenter = new BasketPresenter<>(this);
        presenter.onAttach(this);
      //  recyclerView = findViewById(R.id.ordered_list_recycler_view);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView.setAdapter(adapter);

        int priceId = getIntent().getIntExtra("priceId",0);
        String orderId = getIntent().getStringExtra("orderId");
        order = (Order)getIntent().getSerializableExtra("order");

        visit = presenter.getVisit(order.getVisitId());
        if (visit.getId_comment() != null){
            List<Integer> ids = new ArrayList<>();
            String commentsIds = visit.getId_comment().substring(1,(visit.getId_comment().length() - 1));
            List<String> myList = new ArrayList<String>(Arrays.asList(commentsIds.split(", ")));
            for (String str : myList ) {
                ids.add(Integer.parseInt(str));
            }
            list = presenter.getComments(ids);
        }

        presenter.getBasketList(priceId, orderId, order.getId_payment_type());

        btnBack.setOnClickListener(v -> finish());

    }

    private void init() {
        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.visit_toolbar);
        tabLayout = findViewById(R.id.tabLayout);



       // setSupportActionBar(toolbar);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBasketListReady(List<BasketProduct> basketProducts) {
        if (list != null){
            pagerAdapter = new PagerAdapter(getSupportFragmentManager(),basketProducts,order,list,  visit.getNotes());
            viewPager.setAdapter(pagerAdapter);
        }
    }
}
