package uz.codic.ahmadtea.ui.synchronisation.setOrder.dialog;

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
import uz.codic.ahmadtea.ui.visit.zakaz.product.dialog.ButtonsMapping;
import uz.codic.ahmadtea.utils.CommonUtils;

public class SetOrderCalcDialog extends DialogFragment implements View.OnClickListener {

    public TextView name, remaining, cart, price, box, pieces, total;
    TextView doubleZero, point;
    TextView plusOne, minusOne, btnOk;
    ImageView backspace;

    SetOrderDialogListener listener;


    int count_in_box;
    int p_price;
    int p_remaining;
    String p_name;
    int list_position;
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
        count_in_box = getArguments().getInt("count_box");
        list_position = getArguments().getInt("position");
        name.setText(p_name);
        price.setText(CommonUtils.getFormattedNumber(p_price/100));
        remaining.setText(CommonUtils.getFormattedNumber(p_remaining));

            setItems();
            pieces.setText(String.format("%d", orderBasket.getCount()));
            box.setText(String.format("%d", orderBasket.getCount_boxes()));
            cart.setText(orderBasket.getTotal_count() + "");

    }

    //Click events by Id
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
                if (Integer.parseInt(cart.getText().toString() + Integer.toString(ButtonsMapping.DIGITS.get(v.getId())))  <= p_remaining) {
                    if (cart.getText().toString().equals("0"))
                        cart.setText("");
                    cart.append(Integer.toString(ButtonsMapping.DIGITS.get(v.getId())));
                } else
                    Toast.makeText(getContext(), "Over limit", Toast.LENGTH_LONG).show();
                break;
            case R.id.df_00:
                if (Integer.parseInt(cart.getText().toString() + "00") <= p_remaining) {
                    if (!cart.getText().toString().equals("0"))
                        cart.append("00");
                } else
                    Toast.makeText(getContext(), "Over limit", Toast.LENGTH_LONG).show();
                break;
            case R.id.df_point:
                cart.append(".");
                break;
            case R.id.df_plus_1:
                int num = Integer.parseInt(cart.getText().toString());
                cart.setText(String.valueOf(num + 1));
                break;
            case R.id.df_minus_1:
                int num1 = Integer.parseInt(cart.getText().toString());
                if (num1 > 0)
                    cart.setText(String.valueOf(num1 - 1));
                break;
            case R.id.df_backspace:
                String number = cart.getText().toString();
                if (number.length() > 1)
                    cart.setText(number.substring(0, number.length() - 1));
                else
                    cart.setText("0");
                break;
            case R.id.df_btn_ok:
//                if(!cart.getText().toString().equals("0"))
                listener.onFinishedDialog(Integer.parseInt(cart.getText().toString()), list_position);
                dismiss();
                break;

        }

        //each click will update some of the view
        updateUi();

    }

    private void updateUi() {
        int cart_number = Integer.parseInt(cart.getText().toString());

        //box
        if (cart_number >= count_in_box) {
            int box_amount = cart_number / count_in_box;
            box.setText(CommonUtils.getFormattedNumber(box_amount));
        } else {
            box.setText("0");
        }

        //pieces
        int piece_amount = cart_number % count_in_box;
        pieces.setText(String.valueOf(piece_amount));

        //total
        total.setText(CommonUtils.getFormattedNumber((cart_number * p_price)/100));


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public SetOrderCalcDialog() {

    }


    public static SetOrderCalcDialog newInstance(String id_product, String productName, int remaining_amount, int price, int count_in_box, int list_position) {
        SetOrderCalcDialog calcDialog = new SetOrderCalcDialog();
        Bundle bundle = new Bundle();
        bundle.putString("id_product", id_product);
        bundle.putString("name", productName);
        bundle.putInt("remaining", remaining_amount);
        bundle.putInt("price", price);
        bundle.putInt("count_box", count_in_box);
        bundle.putInt("position", list_position);
        calcDialog.setArguments(bundle);
        return calcDialog;
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

    public void setItems(){
        try {
            orderBasket = listener.getOrderBasketByProductId(id_product);
            Log.d("stocks", "setItems: " + orderBasket.getPrice());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setOrderDialogListener(SetOrderDialogListener listener){
        this.listener = listener;
    }


}
