package uz.codic.ahmadtea.ui.visit.zakaz.product;


import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.paymentTypes.PaymentFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.prices.PricesFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.product.adapter.Callback;
import uz.codic.ahmadtea.ui.visit.zakaz.product.adapter.ProductAdapter;
import uz.codic.ahmadtea.ui.visit.zakaz.product.dialog.ProductCalcDialog;
import uz.codic.ahmadtea.ui.visit.zakaz.product.dialog.ProductDialogListener;
import uz.codic.ahmadtea.utils.CommonUtils;
import uz.codic.ahmadtea.utils.CyrillicLatin;

import static uz.codic.ahmadtea.utils.Consts.paymentTag;
import static uz.codic.ahmadtea.utils.Consts.pricesTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFragment implements ProductMvpView, Callback, ProductDialogListener {

    private OnFragmentInteractionListener listener;

    ProductMvpPresenter<ProductMvpView> presenter;

    ProductAdapter adapter;

    RecyclerView recyclerView;

    TextView total_price;

    TextView total_quantity;

    TextView productQuantity;

    EditText editTextSearch;

    public static int oldPriceId;

    //Pop up menu
    PopupWindow popup;
    SwitchCompat switchRemaining;
    SwitchCompat switchSelected;

    Order orderObject;
    Visit visitObject;
    List<OrderBasket> orderBaskets;
    List<ProductAndProductPrice> productList;
    private ImageButton go_to_top;

    @Override
    public void onProductClick(ProductAndProductPrice products, int position) {
        int price = products.getProductPrices().getValue();

        ProductCalcDialog productCalcDialog = new ProductCalcDialog().newInstance(
                products.getProduct().getId(),
                products.getProduct().getLabel(),
                products.getStocks().getTotal_count(),
                price,
                products.getProduct().getCount_in_box(),
                products.getCount_boxes(),
                products.getCount(),
                position,
                isOrderBasket(products.getProduct().getId()));
        productCalcDialog.setProductListener(this);
        productCalcDialog.show(getFragmentManager(), "calculator");
    }

    public OrderBasket getOrderBasketByProductId(String id) {
        OrderBasket orderBasket = null;
        for (int i = 0; i < orderBaskets.size(); i++) {
            if (orderBaskets.get(i).getId_product().equals(id)) {
                orderBasket = orderBaskets.get(i);
                return orderBasket;
            }
        }
        return orderBasket;
    }

    @Override
    public void onFinishedDialog(int quantity, int box, int count, int listPosition, boolean isOrderBasket) {
        OrderBasket orderBasket;
        if (isOrderBasket) {
            orderBasket = getOrderBasketByProductId(listener.getCompleteObject().getProductList().get(listPosition).getProduct().getId());
        } else orderBasket = new OrderBasket();
        orderBasket.setOrderId(orderObject.getId()); //order_id
        orderBasket.setId_product(listener.getCompleteObject().getProductList().get(listPosition).getProduct().getId()); //id_product
        orderBasket.setCount(quantity % listener.getCompleteObject().getProductList().get(listPosition).getProduct().getCount_in_box()); // count
        orderBasket.setCount_boxes(quantity / listener.getCompleteObject().getProductList().get(listPosition).getProduct().getCount_in_box()); //count_boxes
        orderBasket.setTotal_count(quantity);// total_count
        orderBasket.setPrice(listener.getCompleteObject().getProductList().get(listPosition).getProductPrices().getValue());//to calculate total price
        orderBasket.setPrice_value(listener.getCompleteObject().getProductList().get(listPosition).getProductPrices().getValue());

        if (!isOrderBasket && quantity != 0) orderBaskets.add(orderBasket);
        else if (quantity == 0 && isOrderBasket) {
            orderBaskets.remove(orderBasket);
        }

        updateTotalCalculationUi();
        adapter.updateItem(quantity, box, count, listPosition);
    }


    private void updateTotalCalculationUi() {
        long total = 0;
        int total_q = 0;
        for (OrderBasket busket : orderBaskets) {
            total += 1l * busket.getPrice() * busket.getTotal_count();
            total_q += busket.getTotal_count();
        }
        orderObject.setTotal_cost(total);
        total_price.setText(CommonUtils.getFormattedNumber(total / 100));
        total_quantity.setText(CommonUtils.getFormattedNumber(total_q));
        productQuantity.setText(Integer.toString(orderBaskets.size()));
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        try {

            StateProgressBar progressBar = getActivity().findViewById(R.id.progress_state);
            progressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);

            recyclerView = view.findViewById(R.id.order_recycler_view);
            total_price = view.findViewById(R.id.product_total_price);
            total_quantity = view.findViewById(R.id.product_total_quantity);
            productQuantity = view.findViewById(R.id.product_quantity);
            go_to_top = view.findViewById(R.id.id_product_go_top);

            getActivity().findViewById(R.id.btn_forward).setOnClickListener(v -> {
                if (!orderBaskets.isEmpty()) {
                    getActivity().findViewById(R.id.btn_filter).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.btn_search).setVisibility(View.GONE);
                    listener.transactionFragments(PaymentFragment.newInstance(), paymentTag);
                } else {
                    showMessage("Choose products");
                }
            });
            if (getActivity() != null) {
                getActivity().findViewById(R.id.btn_back).setOnClickListener(v -> {
                    getActivity().findViewById(R.id.btn_filter).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.btn_search).setVisibility(View.GONE);
                    listener.transactionFragments(PricesFragment.newInstance(), pricesTag);
                    //listener.onBackClick(pricesTag);
                });
            }

            getActivity().findViewById(R.id.btn_filter).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.btn_search).setVisibility(View.VISIBLE);

            presenter = new ProductPresenter<>(getActivity());
            presenter.onAttach(this);
            adapter = new ProductAdapter();
            adapter.setCallback(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            if (listener.getCompleteApi().getOrderObject().getId_price() != null) {
                if (listener.getCompleteObject().getProductList() == null || (listener.getCompleteApi().getOrderObject().getId_price() != oldPriceId)) {
                    listener.getCompleteApi().getOrderObject().setTotal_cost(null);
                    listener.getCompleteApi().getOrderBasketList().clear();
                    presenter.reqeustProductList(listener.getCompleteApi().getOrderObject().getId_price(), listener.getCompleteObject().getWorkspace().getId());
                } else {
                    adapter.updateList(listener.getCompleteObject().getProductList());
                }
            } else {
                presenter.reqeustProductList(listener.getCompleteApi().getOrderObject().getId_price(), listener.getCompleteObject().getWorkspace().getId());
            }

            oldPriceId = listener.getCompleteApi().getOrderObject().getId_price();

//     listener.getCompleteApi().getOrderObject().setId_price(getArguments().getInt("priceId"));
            //CompleteApi Object
            orderBaskets = listener.getCompleteApi().getOrderBasketList();
            orderObject = listener.getCompleteApi().getOrderObject();
            orderObject.setId(UUID.randomUUID().toString());//id
            //Filter logic
            filterLogic();

            //Search logic
            searchLogic(view);

            go_to_top.animate().translationY(500).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(0);
            go_to_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerView.scrollToPosition(0);
                }
            });

        }catch (Exception e){
            ErrorClass.log(e);
        }

    }


    private void filterLogic() {
        popup = new PopupWindow(getActivity());
        View layout = getLayoutInflater().inflate(R.layout.menu_filter_pop_up, null);
        popup.setContentView(layout);
        getActivity().findViewById(R.id.btn_filter).setOnClickListener(this::displayPopupWindow);
    }

    private void displayPopupWindow(View anchorView) {

        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_filter));
        popup.showAsDropDown(anchorView);

        switchRemaining = popup.getContentView().findViewById(R.id.filter_switch_remaining);
        switchRemaining.setOnCheckedChangeListener((buttonView, isChecked) -> {
            List<ProductAndProductPrice> list = new ArrayList<>();
            if (isChecked) {
                for (ProductAndProductPrice item : productList) {
                    int remaining = item.getStocks().getTotal_count();
                    if (remaining > 0) {
                        list.add(item);
                    }
                }
                switchRemaining.setChecked(true);
                showMessage("Filtered");
                adapter.updateList(list);
            } else {
                showMessage("Unfiltered");
                switchRemaining.setChecked(false);
                adapter.updateList(productList);
            }
        });
        switchSelected = popup.getContentView().findViewById(R.id.filter_switch_selected);
        switchSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
            List<ProductAndProductPrice> list = new ArrayList<>();
            if (isChecked) {
                for (ProductAndProductPrice item : productList) {
                    if (item.getQuantity() > 0) {
                        list.add(item);
                    }
                }
                showMessage("Filtered");
                adapter.updateList(list);
            } else {
                showMessage("Unfiltered");
                adapter.updateList(productList);
            }
        });
    }

    private void searchLogic(View view) {
        editTextSearch = getActivity().findViewById(R.id.edtxt_search);

        getActivity().findViewById(R.id.btn_search).setOnClickListener(v -> {
            editTextSearch.requestFocus();
            editTextSearch.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
            editTextSearch.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            getActivity().findViewById(R.id.visit_toolbar_search).setVisibility(View.VISIBLE);
        });

        getActivity().findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            if (editTextSearch.getText().toString().trim().isEmpty()) {
                adapter.updateList(productList);
                CommonUtils.hideKeyboardFrom(getActivity().getApplicationContext(), view);
                editTextSearch.setText("");
                getActivity().findViewById(R.id.visit_toolbar_search).setVisibility(View.GONE);
            } else {
                editTextSearch.setText("");
            }

        });
        getActivity().findViewById(R.id.btn_back_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.updateList(productList);
                CommonUtils.hideKeyboardFrom(getActivity().getApplicationContext(), view);
                editTextSearch.setText("");
                getActivity().findViewById(R.id.visit_toolbar_search).setVisibility(View.GONE);
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterNames(s.toString());
            }
        });
    }

    private void filterNames(String text) {
        Log.d("baxtiyor", "filterNames: " + text);
        List<ProductAndProductPrice> searchedList = new ArrayList<>();
        String otherVersion = CyrillicLatin.getOtherVersion(text);
        if (listener != null && text != null) {
            for (ProductAndProductPrice product : productList) {
                if (product.getProduct().getLabel().toLowerCase().contains(text.toLowerCase())
                        || product.getProduct().getLabel().toLowerCase().contains(otherVersion)) {
                    searchedList.add(product);
                }
            }
            adapter.updateList(searchedList);
        } else {
            Log.d("Lifec", "filterNames: null");
        }

    }

    @Override
    public void onProductListReady(List<ProductAndProductPrice> products) {
        this.productList = products;
        listener.getCompleteObject().setProductList(productList);
        adapter.updateList(products);
    }

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onAttach(Context context) {
        Log.d("Lifec", "onAttach: ");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public boolean isOrderBasket(String id_product) {
        for (int i = 0; i < orderBaskets.size(); i++) {
            if (orderBaskets.get(i).getId_product().equals(id_product)) return true;
        }
        return false;
    }


    @Override
    public void changedPosition(int position) {
        if (position > 20) {
            go_to_top.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(600);
        } else if (position < 20) {
            go_to_top.animate().translationY(500).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(600);
        }
    }

    @Override
    public void changedBoxOrCount(int type, int position, String id_product) {
        // types = 1 plus box
        // types = -1 minus box
        // types = 2 plus count
        // types = -2 minus count
        boolean isOrderBasket = isOrderBasket(id_product);
        if (type == 1) {
            // box_count +1


            OrderBasket orderBasket;
            if (isOrderBasket) {
                orderBasket = getOrderBasketByProductId(id_product);
                orderBasket.setCount_boxes(orderBasket.getCount_boxes() + 1);
                orderBasket.setTotal_count(orderBasket.getTotal_count() +1 * listener.getCompleteObject().getProductList().get(position).getProduct().getCount_in_box());// total_count
            } else {
                orderBasket = new OrderBasket();
                orderBasket.setOrderId(orderObject.getId()); //order_id
                orderBasket.setId_product(listener.getCompleteObject().getProductList().get(position).getProduct().getId()); //id_product
                orderBasket.setCount(0); // count
                orderBasket.setCount_boxes(1); //count_boxes
                orderBasket.setTotal_count(1 * listener.getCompleteObject().getProductList().get(position).getProduct().getCount_in_box());// total_count
                orderBasket.setPrice(listener.getCompleteObject().getProductList().get(position).getProductPrices().getValue());//to calculate total price
                orderBasket.setPrice_value(listener.getCompleteObject().getProductList().get(position).getProductPrices().getValue());
                orderBaskets.add(orderBasket);
            }

            updateTotalCalculationUi();
            adapter.updateItem(orderBasket.getTotal_count(), orderBasket.getCount_boxes(), orderBasket.getCount(), position);


        } else if (type == -1) {
            // box_count -1

            OrderBasket orderBasket;
            if (isOrderBasket) {
                orderBasket = getOrderBasketByProductId(id_product);
                if (orderBasket.getCount_boxes() > 1) {
                    orderBasket.setCount_boxes(orderBasket.getCount_boxes() - 1);
                    orderBasket.setTotal_count(orderBasket.getTotal_count() - (1 * listener.getCompleteObject().getProductList().get(position).getProduct().getCount_in_box()));
                    adapter.updateItem(orderBasket.getTotal_count(), orderBasket.getCount_boxes(), orderBasket.getCount(), position);
                } else if (orderBasket.getCount() > 0 && orderBasket.getCount_boxes() == 1) {
                    orderBasket.setCount_boxes(0);
                    orderBasket.setTotal_count(orderBasket.getTotal_count() - (1 * listener.getCompleteObject().getProductList().get(position).getProduct().getCount_in_box()));
                    adapter.updateItem(orderBasket.getTotal_count(), orderBasket.getCount_boxes(), orderBasket.getCount(), position);
                } else if (orderBasket.getCount() == 0 && orderBasket.getCount_boxes() == 1) {
                    orderBaskets.remove(orderBasket);
                    adapter.updateItem(0, 0, 0, position);
                }
            }
            updateTotalCalculationUi();

        } else if (type == 2) {
            //count +1

            OrderBasket orderBasket;
            if (isOrderBasket) {
                orderBasket = getOrderBasketByProductId(id_product);
                orderBasket.setCount(orderBasket.getCount() + 1);
                orderBasket.setTotal_count(orderBasket.getTotal_count() + 1);
            } else {
                orderBasket = new OrderBasket();
                orderBasket.setOrderId(orderObject.getId()); //order_id
                orderBasket.setId_product(listener.getCompleteObject().getProductList().get(position).getProduct().getId()); //id_product
                orderBasket.setCount(1); // count
                orderBasket.setCount_boxes(0); //count_boxes
                orderBasket.setTotal_count(1);// total_count
                orderBasket.setPrice(listener.getCompleteObject().getProductList().get(position).getProductPrices().getValue());//to calculate total price
                orderBasket.setPrice_value(listener.getCompleteObject().getProductList().get(position).getProductPrices().getValue());
                orderBaskets.add(orderBasket);
            }

            updateTotalCalculationUi();
            adapter.updateItem(orderBasket.getTotal_count(), orderBasket.getCount_boxes(), orderBasket.getCount(), position);

        } else if (type == -2) {
            // count -1
            OrderBasket orderBasket;
            if (isOrderBasket) {
                orderBasket = getOrderBasketByProductId(id_product);
                if (orderBasket.getCount() > 1) {
                    orderBasket.setCount(orderBasket.getCount() - 1);
                    orderBasket.setTotal_count(orderBasket.getTotal_count() - 1);
                    adapter.updateItem(orderBasket.getTotal_count(), orderBasket.getCount_boxes(), orderBasket.getCount(), position);
                } else if (orderBasket.getCount_boxes() > 0 && orderBasket.getCount() == 1) {
                    orderBasket.setCount(0);
                    orderBasket.setTotal_count(orderBasket.getTotal_count() - 1);
                    adapter.updateItem(orderBasket.getTotal_count(), orderBasket.getCount_boxes(), orderBasket.getCount(), position);
                } else if (orderBasket.getCount_boxes() == 0 && orderBasket.getCount() == 1) {
                    orderBaskets.remove(orderBasket);
                    adapter.updateItem(0, 0, 0, position);
                }
            }
            updateTotalCalculationUi();

        }

    }
}
