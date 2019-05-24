package uz.codic.ahmadtea.ui.synchronisation.setOrder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.MainActivity;
import uz.codic.ahmadtea.ui.base.BaseActivity;
import uz.codic.ahmadtea.ui.report.adapter.OrderedList;
import uz.codic.ahmadtea.ui.synchronisation.setOrder.adapter.CalbackSetOrder;
import uz.codic.ahmadtea.ui.synchronisation.setOrder.adapter.SetOrderAdapter;
import uz.codic.ahmadtea.ui.synchronisation.setOrder.dialog.SetOrderCalcDialog;
import uz.codic.ahmadtea.ui.synchronisation.setOrder.dialog.SetOrderDialogListener;
import uz.codic.ahmadtea.utils.CommonUtils;

public class SetOrderActivity extends BaseActivity implements SetOrderMvpView, SetOrderDialogListener, CalbackSetOrder {

    TextView tv_text_order;
    TextView title_toolbar;
    TextView tv_order_with_new_price;
    TextView tv_summa_value;
    TextView tv_products_count_value;
    TextView tv_kolvo_value;
    TextView tv_draf;
    TextView tv_send;

    ImageView iv_back_btn;
    OrderedList ordered;
    Visit visit;
    List<OrderBasket> orderBaskets;
    List<ProductAndProductPrice> productItems;
    Toolbar toolbar;
    RecyclerView recyclerView;
    SetOrderAdapter adapter;
    LinearLayout linearLayout_set_order;
    boolean isChangedRemaining = false, isChangedPrice = false, isSend = false;


    SetOrderMvpPresenter<SetOrderMvpView> presenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_order);

        toolbar = findViewById(R.id.id_set_order_toolbar);
        setSupportActionBar(toolbar);


        init();

    }

    private void init() {

        ordered = (OrderedList) getIntent().getSerializableExtra("order");
        presenter = new SerOrderPresenter<>(SetOrderActivity.this);
        presenter.onAttach(this);

        productItems = new ArrayList<>();

        title_toolbar = findViewById(R.id.id_title_toolbar_set_order);
        tv_text_order = findViewById(R.id.tv_text_order);
        iv_back_btn = findViewById(R.id.id_set_order_back);
        recyclerView = findViewById(R.id.id_recycler_view_set_order);
        linearLayout_set_order = findViewById(R.id.linearLayout_set_order);
        tv_order_with_new_price = findViewById(R.id.tv_set_order_price_changed);
        tv_summa_value = findViewById(R.id.id_set_order_cumma_value);
        tv_products_count_value = findViewById(R.id.id_set_order_products_count_value);
        tv_kolvo_value = findViewById(R.id.id_set_order_kolvo_value);
        tv_draf = findViewById(R.id.id_set_order_draf);
        tv_send = findViewById(R.id.id_set_order_send);

        tv_summa_value.setText(CommonUtils.getFormattedNumber(ordered.getOrder().getTotal_cost() / 100) + "");


        adapter = new SetOrderAdapter();
        adapter.setCalback(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //adapter.setOrderBaskets(presenter.getOrderBaskets(ordered.getOrder().getId()));


        presenter.getOrderBaskets(ordered.getOrder().getId());
        try {
            visit = presenter.getVisit(ordered.getOrder().getVisitId());
        } catch (Exception e) {
            e.printStackTrace();
            visit = null;
        }
        title_toolbar.setText(ordered.getMerchant().getLabel());
        tv_text_order.setText("");

        iv_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_order_with_new_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SetOrderActivity.this);
                builder.setMessage("Siz rostan ham zakasni yangi narxda hisoblaysizmi? (eski narxdagi ma'lumotlar o'chib ketadi!)");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO orderni yangi narxda hisobla
                        orderYangiNarxi();
                        showMessage("order yangi narxga otkazildi");

                        tv_order_with_new_price.setVisibility(View.GONE);
                        if (isChangedRemaining) {
                            checkOrder();
                        } else {
                            linearLayout_set_order.setVisibility(View.VISIBLE);
                            tv_send.setBackgroundColor(Color.parseColor("#00BCD4"));
                            isSend = true;

                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        tv_draf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SetOrderActivity.this);
                    builder.setMessage("Siz rostan ham zakasni send as Draft qilib jo'natasizmi?");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.requestSendDraft(orderBaskets, ordered.getOrder(), visit);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    showMessage("Iltomos internet qoshinganini tekshirib ko'ring!");
                }
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSend) {
                    showMessage("jonatildi");
                    if (isOnline()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SetOrderActivity.this);
                        builder.setMessage("Siz rostan ham bu zakasga buyurtma bermoqchimisiz?");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.requestSend(orderBaskets, ordered.getOrder(), visit);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    } else {
                        showMessage("Iltomos internet qoshinganini tekshirib ko'ring!");
                    }

                }

            }
        });
    }

    private void orderYangiNarxi() {
        Long cost = 0l;
        for (int i = 0; i < orderBaskets.size(); i++) {
            cost += 1l * orderBaskets.get(i).getTotal_count() * productItems.get(i).getProductPrices().getValue();
        }
        ordered.getOrder().setTotal_cost(cost);
        tv_summa_value.setText(CommonUtils.getFormattedNumber(ordered.getOrder().getTotal_cost() / 100) + "");

    }

    private void getProductItems() {
        int kolvo = 0;

        for (int i = 0; i < orderBaskets.size(); i++) {
            kolvo += orderBaskets.get(i).getTotal_count();
            presenter.getProductItems(ordered.getOrder().getId_price(), ordered.getOrder().getId_workspace(), orderBaskets.get(i).getId_product());
        }
        tv_kolvo_value.setText(CommonUtils.getFormattedNumber(kolvo) + "");

    }

    @Override
    public void resultOrderBaskets(List<OrderBasket> orderBaskets) {
        this.orderBaskets = orderBaskets;
        tv_products_count_value.setText(CommonUtils.getFormattedNumber(orderBaskets.size()) + "");
        adapter.setOrderBaskets(orderBaskets);
        getProductItems();
    }

    @Override
    public void resultProductItems(ProductAndProductPrice productItem) {
        this.productItems.add(productItem);
        adapter.setProductItems(this.productItems);
        checkOrder();
    }

    private void checkOrder() {

        tv_text_order.setText("");
        isChangedPrice = false;
        isChangedRemaining = false;
        adapter.setChanged(false);
        adapter.updatePositions();
        for (int i = 0; i < productItems.size(); i++) {
            ProductAndProductPrice thisItem = productItems.get(i);
            OrderBasket thisBasket = orderBaskets.get(i);
            int remaining_amount = thisItem.getStocks().getTotal_count();
            if (remaining_amount < thisBasket.getTotal_count()) {
                linearLayout_set_order.setVisibility(View.VISIBLE);
                adapter.setPositions(i);
                isChangedRemaining = true;
                adapter.setChanged(true);
                tv_text_order.append(thisItem.getProduct().getLabel() + " bu mahsulot omborda siz buyurtma berayotgan miqdordan kam qolgan!\n");
            }
            Log.d("baxtiyor", "checkOrder: " + thisItem.getProductPrices().getValue() + " " + thisBasket.getPrice_value());
            Log.d("baxtiyor", "checkOrder: " + thisItem.getProductPrices().toString() + " " + thisBasket);
            Log.d("baxtiyor", "position: " + (productItems.size() - 1));
            if (thisItem.getProductPrices().getValue() != thisBasket.getPrice_value()) {
                isChangedPrice = true;
                adapter.setChanged(true);
                tv_text_order.append(thisItem.getProduct().getLabel() + " bu mahsulot narxi o'zgargan!\n");
                linearLayout_set_order.setVisibility(View.GONE);
                tv_order_with_new_price.setVisibility(View.VISIBLE);
            }
        }

        if (!isChangedRemaining) {
            tv_send.setBackgroundColor(Color.parseColor("#00BCD4"));
            isSend = true;
        } else {
            tv_send.setBackgroundColor(Color.parseColor("#9E9E9E"));
            isSend = false;

        }
    }


    @Override
    public void onFinishedDialog(int quantity, int position) {
        int count, count_boxes, total_count;
        total_count = quantity;
        count_boxes = quantity/productItems.get(position).getProduct().getCount_in_box();
        count = quantity%productItems.get(position).getProduct().getCount_in_box();
        orderBaskets.get(position).setTotal_count(total_count);
        orderBaskets.get(position).setCount(count);
        orderBaskets.get(position).setCount_boxes(count_boxes);
        checkOrder();
        orderYangiNarxi();
        adapter.setOrderBaskets(orderBaskets);
    }

    @Override
    public OrderBasket getOrderBasketByProductId(String id) {
        OrderBasket orderBasket = null;
        for (int i = 0; i < orderBaskets.size(); i++) {
            if (orderBaskets.get(i).getId_product().equals(id)) {
                orderBasket = orderBaskets.get(i);
                break;
            }
        }
        return orderBasket;
    }

    @Override
    public void itemClick(ProductAndProductPrice products, int position) {
        if (!isChangedPrice) {
            int price = products.getProductPrices().getValue();

            SetOrderCalcDialog calcDialog = new SetOrderCalcDialog().newInstance(
                    products.getProduct().getId(),
                    products.getProduct().getLabel(),
                    products.getStocks().getTotal_count(),
                    price,
                    products.getProduct().getCount_in_box(),
                    position
            );
            calcDialog.setOrderDialogListener(this);
            calcDialog.show(getSupportFragmentManager(), "calculator");
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void back() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        presenter.setOrder(orderBaskets, ordered.getOrder(), visit);
        super.onBackPressed();
        finish();
    }
}
