package uz.codic.ahmadtea.ui.synchronisation.setOrder.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;
import uz.codic.ahmadtea.utils.CommonUtils;

public class SetOrderAdapter extends RecyclerView.Adapter<SetOrderAdapter.ViewHolder> {

    List<OrderBasket> orderBaskets;
    List<ProductAndProductPrice> productItems;
    Map<Integer, Boolean> positions;
    boolean isChanged = false;
    CalbackSetOrder calback;

    public SetOrderAdapter() {
        positions = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_set_order, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (productItems != null) {
            ProductAndProductPrice item = productItems.get(viewHolder.getAdapterPosition());

            viewHolder.product_name.setText(item.getProduct().getLabel());
            if (isChanged){
                viewHolder.product_name.setTextColor(Color.parseColor("#FF0000"));
            }else {
                viewHolder.product_count.setTextColor(Color.parseColor("#333333"));
            }
            viewHolder.product_count.setText(CommonUtils.getFormattedNumber(orderBaskets.get(viewHolder.getAdapterPosition()).getTotal_count()) + "");

            if (item.getProductPrices().getValue() != orderBaskets.get(viewHolder.getAdapterPosition()).getPrice_value()){
                viewHolder.product_price.setText("old" +CommonUtils.getFormattedNumber(orderBaskets.get(viewHolder.getAdapterPosition()).getPrice_value() / 100));
                viewHolder.product_price.append("\nnew" + CommonUtils.getFormattedNumber(item.getProductPrices().getValue() / 100));
                viewHolder.product_price.setTextColor(Color.parseColor("#FF0000"));
            }else {
                viewHolder.product_price.setText(CommonUtils.getFormattedNumber(item.getProductPrices().getValue() / 100));
                viewHolder.product_price.setTextColor(Color.parseColor("#333333"));
            }
            viewHolder.product_remaining.setText(CommonUtils.getFormattedNumber(item.getStocks().getTotal_count()));
            if (positions.get(viewHolder.getAdapterPosition())){
                viewHolder.product_count.setTextColor(Color.parseColor("#FF0000"));
            }else {
                viewHolder.product_name.setTextColor(Color.parseColor("#333333"));
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calback.itemClick(item, viewHolder.getAdapterPosition());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (orderBaskets != null){
            return orderBaskets.size();
        }else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView product_name;
        TextView product_price;
        TextView product_remaining;
        TextView product_count;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.id_set_order_name);
            product_price = itemView.findViewById(R.id.id_set_order_price);
            product_remaining = itemView.findViewById(R.id.id_set_order_remaining);
            product_count = itemView.findViewById(R.id.id_set_order_order_amount);
        }
    }

    public void setOrderBaskets(List<OrderBasket> orderBaskets) {
        this.orderBaskets = orderBaskets;
        notifyDataSetChanged();
    }

    public void setProductItems(List<ProductAndProductPrice> productItems) {
        this.productItems = productItems;
        updatePositions();
        notifyDataSetChanged();
    }

    public void setPositions(int position) {
        positions.put(position, true);
    }

    public void updatePositions(){
        positions = new HashMap<>();
        for (int i = 0; i < productItems.size(); i++) {
            positions.put(i, false);
        }
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public void setCalback(CalbackSetOrder calback) {
        this.calback = calback;
    }
}
