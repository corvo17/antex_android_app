package uz.codic.ahmadtea.ui.report.report_activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.PaymentType;
import uz.codic.ahmadtea.data.db.entities.PhysicalWareHouse;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.ui.base.BaseActivity;

public class ReportFilterActivity extends BaseActivity implements ReportFilterMvpView {

    ReportFilterMvpPresenter<ReportFilterMvpView> presenter;

    List<Merchant> merchants;
    List<Price> prices;
    List<PaymentType> paymentTypes;
    List<PhysicalWareHouse> wareHouses;
    CharSequence[] merchant_items;
    CharSequence[] price_items;
    CharSequence[] payment_items;
    CharSequence[] warehouse_items;
    CharSequence[] agent_items;
    boolean[] merchant_isCheckList;
    boolean[] price_isCheckList;
    boolean[] payment_isCheckList;
    boolean[] warehouse_isCheckList;
    boolean[] agent_isCheckList;
    DatePickerDialog dialog;
    int which;


    int type = 0;
    Button select_merchant, select_warehouse, select_price, select_payment_type, select_agent;
    EditText start_period, end_period;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        select_merchant = findViewById(R.id.select_merchant);
        select_warehouse = findViewById(R.id.select_warehouse);
        select_price = findViewById(R.id.select_price);
        select_payment_type = findViewById(R.id.select_payment_type);
        select_agent = findViewById(R.id.select_agent);
        start_period = findViewById(R.id.et_start_period);
        end_period = findViewById(R.id.et_end_period);

        presenter = new ReportFilterPresenter<>(this);
        presenter.onAttach(this);
        presenter.getDate();

        type = getIntent().getIntExtra("type", 0);
        createUI(type);
        openCalendar();
        setTextWithCalendar();

    }


    //Акт сверки
    private void funcfive() {
        select_warehouse.setVisibility(View.GONE);
        select_price.setVisibility(View.GONE);
        select_payment_type.setVisibility(View.GONE);
        select_agent.setVisibility(View.GONE);
        select_merchant.setOnClickListener(v -> {
            openSearchDialog();
        });

    }

    //Фактуры по Дате
    private void funcfour() {
        select_merchant.setVisibility(View.GONE);
        select_warehouse.setVisibility(View.GONE);
        select_price.setVisibility(View.GONE);
        select_payment_type.setVisibility(View.GONE);
        select_agent.setVisibility(View.GONE);
    }

    //Фактуры по контрагентам
    private void functhree() {

        select_merchant.setOnClickListener(v -> {
            if (merchants != null && merchant_items != null) {
//                creatSingleChoiceDialog("Выберите Контрагент", merchant_items);
                openSearchDialog();
            }
        });

        select_price.setOnClickListener(v -> {
            if (prices != null && price_items != null) {
                createMultiChoiceDialog("Выберите Цена", payment_items);
            }
        });
        select_payment_type.setOnClickListener(v -> {
            if (paymentTypes != null && payment_items != null) {
                createMultiChoiceDialog("Выберите Тип Оплата", payment_items);
            }
        });
        select_warehouse.setOnClickListener(v -> {
            if (wareHouses != null && warehouse_items != null) {
                createMultiChoiceDialog("Выберите Склад", warehouse_items);
            }
        });
    }

    //Общая сумма по продуктам
    private void functwo() {

        select_merchant.setOnClickListener(v -> {
            if (merchants != null && merchant_items != null) {
                //creatSingleChoiceDialog("Выберите Контрагент", merchant_items);
                openSearchDialog();
            }
        });

        select_price.setOnClickListener(v -> {
            if (prices != null && price_items != null) {
                createMultiChoiceDialog("Выберите Цена", payment_items);
            }
        });
        select_payment_type.setOnClickListener(v -> {
            if (paymentTypes != null && payment_items != null) {
                createMultiChoiceDialog("Выберите Тип Оплата", payment_items);
            }
        });
        select_warehouse.setOnClickListener(v -> {
            if (wareHouses != null && warehouse_items != null) {
                createMultiChoiceDialog("Выберите Склад", warehouse_items);
            }
        });
    }

    //Общая сумма по контрагентам
    @SuppressLint("ClickableViewAccessibility")
    private void funcOne() {
        select_merchant.setVisibility(View.GONE);
        select_price.setOnClickListener(v -> {
            if (prices != null && price_items != null) {
                createMultiChoiceDialog("Выберите Цена", payment_items);
            }
        });
        select_payment_type.setOnClickListener(v -> {
            if (paymentTypes != null && payment_items != null) {
                createMultiChoiceDialog("Выберите Тип Оплата", payment_items);
            }
        });
        select_warehouse.setOnClickListener(v -> {
            if (wareHouses != null && warehouse_items != null) {
                createMultiChoiceDialog("Выберите Склад", warehouse_items);
            }
        });

    }

    private void setTextWithCalendar() {
        start_period.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                which = 1;
                dialog.show();
                return true;
            }
        });
        end_period.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                which = 2;
                dialog.show();
                return true;
            }
        });
    }

    private void createUI(int type) {
        switch (type) {
            case 1:
                setTitle("Общая сумма по контрагентам");
                funcOne();
                break;
            case 2:
                setTitle("Общая сумма по продуктам");
                functwo();
                break;
            case 3:
                setTitle("Фактуры по контрагентам");
                functhree();
                break;
            case 4:
                setTitle("Мой Фактуры по Дате");
                funcfour();
                break;
            case 5:
                setTitle("Акт сверки");
                funcfive();
                break;
            case 0:
                break;
        }
    }

    private void openSearchDialog() {

        SimpleSearchDialogCompat<Merchant> searchDialogCompat = new SimpleSearchDialogCompat<Merchant>(this, "Search...", "What are you looking for...?", null, (ArrayList<Merchant>) merchants, new SearchResultListener<Merchant>() {
            @Override
            public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Merchant merchant, int i) {
                showMessage(merchant.getLabel() + " " + merchant.getId());
                baseSearchDialogCompat.dismiss();
            }
        });
        searchDialogCompat.show();
    }


    private void creatSingleChoiceDialog(String title, CharSequence[] items) {
        boolean[] choicesInitial = new boolean[items.length];
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //SearchDialogAd
        dialog.setTitle(title);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setNegativeButton("CANCAl", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.create();
        dialog.show();
    }

    private void createMultiChoiceDialog(String title, CharSequence[] items) {
        boolean[] choicesInitial = new boolean[items.length];
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setNegativeButton("CANCAl", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setMultiChoiceItems(items, choicesInitial, (dialogInterface, position, isChecked) -> {

        });
        dialog.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void responseData(List<Merchant> merchants, List<Price> prices, List<PaymentType> paymentTypes, List<PhysicalWareHouse> wareHouses) {
        this.merchants = merchants;
        this.prices = prices;
        this.paymentTypes = paymentTypes;
        this.wareHouses = wareHouses;
        merchant_items = new CharSequence[merchants.size()];
        price_items = new CharSequence[prices.size()];
        payment_items = new CharSequence[paymentTypes.size()];
        warehouse_items = new CharSequence[wareHouses.size()];
        for (int i = 0, j = 0, k = 0, l = 0; i < merchants.size() || j < prices.size() || k < paymentTypes.size() || l < wareHouses.size(); i++, j++, k++, l++) {
            if (i < merchants.size()) {
                merchant_items[i] = merchants.get(i).getLabel();
            }
            if (j < prices.size()) {
                payment_items[i] = prices.get(i).getLabel();
            }
            if (k < paymentTypes.size()) {
                payment_items[i] = paymentTypes.get(i).getLabel();
            }
            if (l < wareHouses.size()) {
                warehouse_items[l] = wareHouses.get(l).getLabel();
            }
        }
    }

    private void openCalendar() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date;
                if (dayOfMonth < 10) {
                    date = year + "-0" + (month + 1) + "-0" + dayOfMonth;
                } else {
                    date = year + "-0" + (month + 1) + "-" + dayOfMonth;
                }
                if (which == 1) {
                    start_period.setText(date);
                } else end_period.setText(date);

            }
        }, mYear, mMonth, mDay);
    }


}
