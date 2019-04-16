package uz.codic.ahmadtea.ui.visit.zakaz.product.dialog;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.utils.CommonUtils;

public class ProductCalcDialog extends DialogFragment implements View.OnClickListener {

    public TextView name, remaining, cart, price, box, pieces, total, tv_count_in_box;
    TextView doubleZero, point;
    TextView plusOne, minusOne, btnOk;
    ImageView backspace;

    ProductDialogListener listener;


    int count_in_box;
    int p_price;
    int p_remaining;
    int total_count_dialog = 0;
    String p_name;
    int list_position;
    boolean isOrderBasket;
    boolean isBox = false;
    String id_product;
    OrderBasket orderBasket;
    LinearLayout btnBoxes;
    LinearLayout btnPieces;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);

        //getArguments from bundle
        id_product = getArguments().getString("id_product");
        p_name = getArguments().getString("name");
        p_price = getArguments().getInt("price");
        p_remaining = getArguments().getInt("remaining");
        count_in_box = getArguments().getInt("count_in_box");
        list_position = getArguments().getInt("position");
        isOrderBasket = getArguments().getBoolean("isOrderBasket");
        name.setText(p_name);
        btnPieces.setBackgroundColor(Color.parseColor("#EEEEEE"));
        price.setText(CommonUtils.getFormattedNumber(p_price / 100));
        tv_count_in_box.setText("Кор (" + count_in_box + ")");

        if (isOrderBasket) {
            setItems();
            pieces.setText(String.valueOf(getArguments().getInt("count")));
            box.setText(String.valueOf(getArguments().getInt("count_box")));
            cart.setText(orderBasket.getTotal_count() + "");
        }

        if (Integer.parseInt(cart.getText().toString())==0){
            remaining.setText(CommonUtils.getFormattedNumber(p_remaining));
        }else remaining.setText(CommonUtils.getFormattedNumber(p_remaining)+"\n" + CommonUtils.getFormattedNumber(p_remaining-Integer.parseInt(cart.getText().toString())));
    }

    //Click events by Id
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.df_0:
            case R.id.df_1:
            case R.id.df_2:
            case R.id.df_3:
            case R.id.df_4:
            case R.id.df_5:
            case R.id.df_6:
            case R.id.df_7:
            case R.id.df_8:
            case R.id.df_9:
                if (isBox) {
                    // box
                    if (Integer.parseInt(box.getText().toString() + Integer.toString(ButtonsMapping.DIGITS.get(v.getId()))) * count_in_box + Integer.parseInt(pieces.getText().toString()) <= p_remaining) {
                        if (cart.getText().toString().equals("0"))
                            cart.setText("");
                        cart.setText(String.valueOf(Integer.parseInt(box.getText().toString() + Integer.toString(ButtonsMapping.DIGITS.get(v.getId()))) * count_in_box + Integer.parseInt(pieces.getText().toString())));
                        //cart.append(Integer.toString(ButtonsMapping.DIGITS.get(v.getId())));
                    } else
                        Toast.makeText(getContext(), "Over limit", Toast.LENGTH_LONG).show();
                } else {
                    //count
                    if (Integer.parseInt(pieces.getText().toString() + Integer.toString(ButtonsMapping.DIGITS.get(v.getId()))) + Integer.parseInt(box.getText().toString()) * count_in_box <= p_remaining) {
                        if (cart.getText().toString().equals("0"))
                            cart.setText("");
                        cart.setText(String.valueOf(Integer.parseInt(pieces.getText().toString() + Integer.toString(ButtonsMapping.DIGITS.get(v.getId()))) + Integer.parseInt(box.getText().toString()) * count_in_box));
                        //cart.append(Integer.toString(ButtonsMapping.DIGITS.get(v.getId())));
                    } else
                        Toast.makeText(getContext(), "Over limit", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.df_00:
                if (isBox) {
                    if (Integer.parseInt(box.getText().toString() + "00") * count_in_box + Integer.parseInt(pieces.getText().toString()) <= p_remaining) {
                        if (!cart.getText().toString().equals("0"))
                            //cart.append("00");
                            cart.setText(String.valueOf(Integer.parseInt(box.getText().toString() + "00") * count_in_box + Integer.parseInt(pieces.getText().toString())));
                    } else
                        Toast.makeText(getContext(), "Over limit", Toast.LENGTH_LONG).show();
                } else {
                    if (Integer.parseInt(pieces.getText().toString() + "00") + Integer.parseInt(box.getText().toString()) * count_in_box <= p_remaining) {

                        if (!cart.getText().toString().equals("0"))
                            cart.setText(String.valueOf(Integer.parseInt(pieces.getText().toString() + "00") + Integer.parseInt(box.getText().toString()) * count_in_box));
                        //cart.append("00");
                    } else
                        Toast.makeText(getContext(), "Over limit", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.df_point:
                cart.append(".");
                break;
            case R.id.df_plus_1:
                if (isBox) {
                    if ((Integer.parseInt(box.getText().toString()) + 1) * count_in_box + Integer.parseInt(pieces.getText().toString()) <= p_remaining) {
                        cart.setText(String.valueOf((Integer.parseInt(box.getText().toString()) + 1) * count_in_box + Integer.parseInt(pieces.getText().toString())));
                    } else Toast.makeText(getContext(), "Over limit", Toast.LENGTH_LONG).show();
                } else {
                    if (Integer.parseInt(pieces.getText().toString()) + 1 + Integer.parseInt(box.getText().toString()) * count_in_box <= p_remaining) {
                        cart.setText(String.valueOf(Integer.parseInt(pieces.getText().toString()) + 1 + Integer.parseInt(box.getText().toString()) * count_in_box));
                    } else {
                        Toast.makeText(getContext(), "Over limit", Toast.LENGTH_LONG).show();
                    }

                }

                break;
            case R.id.df_minus_1:
                if (isBox) {
                    cart.setText(String.valueOf((Integer.parseInt(box.getText().toString()) - 1) * count_in_box + Integer.parseInt(pieces.getText().toString())));
                } else {
                    if (Integer.parseInt(pieces.getText().toString()) > 0) {
                        cart.setText(String.valueOf(Integer.parseInt(pieces.getText().toString()) - 1 + Integer.parseInt(box.getText().toString()) * count_in_box));
                    }else {

                    }
                }
                break;
            case R.id.df_backspace:
                String number = cart.getText().toString();
                if (isBox) {
                    String box_string = box.getText().toString();
                    Log.d("baxtiyor", "onClick: " + box_string + " lenghth " + box_string.length());
                    if (box_string.length() > 1) {
                        box.setText(box_string.substring(0, box_string.length() - 1));
                        Log.d("baxtiyor", "onClick: " + box_string);
                        Log.d("baxtiyor", "s: " + Integer.parseInt(box.getText().toString()) * count_in_box + Integer.parseInt(pieces.getText().toString()));
                        cart.setText(String.valueOf((Integer.parseInt(box.getText().toString())) * count_in_box + Integer.parseInt(pieces.getText().toString())));
                    } else {
                        Log.d("baxtiyor", "onClick: " + box_string);
                        box.setText("0");
                        cart.setText(pieces.getText().toString());
                    }
                } else {
                    Log.d("baxtiyor", "onClick: " + number);
                    String count_string = pieces.getText().toString();
                    if (count_string.length() > 1) {
                        pieces.setText(count_string.substring(0, count_string.length() - 1));
                        cart.setText(String.valueOf((Integer.parseInt(box.getText().toString())) * count_in_box + Integer.parseInt(pieces.getText().toString())));
                    } else {
                        pieces.setText("0");
                        cart.setText(String.valueOf((Integer.parseInt(box.getText().toString())) * count_in_box + Integer.parseInt(pieces.getText().toString())));
                    }
                }
                break;
            case R.id.df_btn_ok:
//                if(!cart.getText().toString().equals("0"))
                listener.onFinishedDialog(Integer.parseInt(cart.getText().toString()),Integer.parseInt(box.getText().toString()), Integer.parseInt(pieces.getText().toString()), list_position, isOrderBasket);
                dismiss();
                break;
            case R.id.btn_boxes:
                btnBoxes.setBackgroundColor(Color.parseColor("#EEEEEE"));
                btnPieces.setBackgroundColor(Color.parseColor("#ffffff"));
                isBox = true;
                Log.d("baxtiyor", "box: ");
                break;


            case R.id.btn_pieces:
                Log.d("baxtiyor", "count: ");
                isBox = false;
                btnBoxes.setBackgroundColor(Color.parseColor("#ffffff"));
                btnPieces.setBackgroundColor(Color.parseColor("#EEEEEE"));
                break;

        }

        //each click will update some of the view
        updateUi();

    }

    private void updateUi() {
        int cart_number = Integer.parseInt(cart.getText().toString());


        //box
        if (isBox) {
            int count = Integer.parseInt(pieces.getText().toString());
            box.setText(String.valueOf((cart_number - count) / count_in_box));
        } else {
            int count_boxes = Integer.parseInt(box.getText().toString());
            pieces.setText(String.valueOf(cart_number - (count_boxes * count_in_box)));
        }

//        if (cart_number >= count_in_box) {
//            int box_amount = cart_number / count_in_box;
//            box.setText(CommonUtils.getFormattedNumber(box_amount));
//        } else {
//            box.setText("0");
//        }
//
//        //pieces
//        int piece_amount = cart_number % count_in_box;
//        pieces.setText(String.valueOf(piece_amount));

        //total
        total.setText(CommonUtils.getFormattedNumber((1l*cart_number * p_price) / 100));
        if (Integer.parseInt(cart.getText().toString())==0){
            remaining.setText(CommonUtils.getFormattedNumber(p_remaining));
        }else remaining.setText(CommonUtils.getFormattedNumber(p_remaining)+"\n" + CommonUtils.getFormattedNumber(p_remaining-Integer.parseInt(cart.getText().toString())));


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public ProductCalcDialog() {

    }


    public static ProductCalcDialog newInstance(String id_product, String productName, int remaining_amount, int price, int count_in_box, int count_box, int count, int list_position, boolean isOrderBasket) {
        ProductCalcDialog productCalcDialog = new ProductCalcDialog();
        Bundle bundle = new Bundle();
        bundle.putString("id_product", id_product);
        bundle.putString("name", productName);
        bundle.putInt("remaining", remaining_amount);
        bundle.putInt("price", price);
        bundle.putInt("count_in_box", count_in_box);
        bundle.putInt("count_box", count_box);
        bundle.putInt("count", count);
        bundle.putInt("position", list_position);
        bundle.putBoolean("isOrderBasket", isOrderBasket);
        productCalcDialog.setArguments(bundle);
        return productCalcDialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getDialog().getWindow()
//                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_calc, container);
    }


    private void bindViews(View view) {
        name = view.findViewById(R.id.df_product_name);
        remaining = view.findViewById(R.id.df_remaining);
        price = view.findViewById(R.id.df_price);
        cart = view.findViewById(R.id.df_cart_amount);
        box = view.findViewById(R.id.df_box_amount);
        tv_count_in_box = view.findViewById(R.id.id_count_in_box);
        pieces = view.findViewById(R.id.df_piece_amount);
        total = view.findViewById(R.id.df_total_sum);
        doubleZero = view.findViewById(R.id.df_00);
        point = view.findViewById(R.id.df_point);
        plusOne = view.findViewById(R.id.df_plus_1);
        minusOne = view.findViewById(R.id.df_minus_1);
        btnOk = view.findViewById(R.id.df_btn_ok);
        backspace = view.findViewById(R.id.df_backspace);
        btnBoxes = view.findViewById(R.id.btn_boxes);
        btnPieces = view.findViewById(R.id.btn_pieces);

        doubleZero.setOnClickListener(this);
        plusOne.setOnClickListener(this);
        minusOne.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        backspace.setOnClickListener(this);
        btnPieces.setOnClickListener(this);
        btnBoxes.setOnClickListener(this);
        for (int id : ButtonsMapping.DIGITS.keySet()) {
            view.findViewById(id).setOnClickListener(this);
        }
    }

    public void setItems() {
        orderBasket = listener.getOrderBasketByProductId(id_product);
        Log.d("stocks", "setItems: " + orderBasket.getPrice());

    }

    public void setProductListener(ProductDialogListener listener) {
        this.listener = listener;
    }


}
