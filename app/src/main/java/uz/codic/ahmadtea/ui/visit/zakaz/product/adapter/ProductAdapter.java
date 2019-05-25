package uz.codic.ahmadtea.ui.visit.zakaz.product.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.utils.CommonUtils;
import uz.codic.ahmadtea.utils.Consts;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<ProductAndProductPrice> products;
    Callback callback;


    public void updateList(List<ProductAndProductPrice> list){
        this.products = list;
        notifyDataSetChanged();
    }

    public void updateItem(int quantity, int box, int count, int position) {
        products.get(position).setQuantity(quantity);
        products.get(position).setCount(count);
        products.get(position).setCount_boxes(box);
        notifyDataSetChanged();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder viewHolder, int i) {
        try {
            ProductAndProductPrice item = products.get(viewHolder.getAdapterPosition());

            int price = item.getProductPrices().getValue();
            viewHolder.price.setText(CommonUtils.getFormattedNumber(price / 100));

            viewHolder.name.setText(item.getProduct().getLabel());

            viewHolder.remaining.setText(CommonUtils.getFormattedNumber(item.getActiveStock().getTotal_active_count()));
//        viewHolder.remaining.setText(CommonUtils.getFormattedNumber(callback.getRemaing_amount(item.getProduct().getId(), item.getProduct().getCount_in_box())));

            viewHolder.pulus_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //try {
                        if (item.getActiveStock().getTotal_active_count() >= (item.getCount_boxes() + 1) * products.get(viewHolder.getAdapterPosition()).getProduct().getCount_in_box() + item.getCount()) {
                            callback.changedBoxOrCount(1, viewHolder.getAdapterPosition(), item.getProduct().getId());
                            viewHolder.order_amount.setText(String.valueOf(item.getCount_boxes()+ 1));


                        }
                   //
                }
            });

            viewHolder.minus_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getCount_boxes() > 0) {
                        callback.changedBoxOrCount((-1), viewHolder.getAdapterPosition(), item.getProduct().getId());
                        viewHolder.order_amount.setText(String.valueOf(item.getCount_boxes() - 1));
                    }
                }
            });

            viewHolder.pulus_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getActiveStock().getTotal_active_count() >= (item.getCount_boxes() * products.get(viewHolder.getAdapterPosition()).getProduct().getCount_in_box() + item.getCount() + 1)) {
                        callback.changedBoxOrCount(2, viewHolder.getAdapterPosition(), item.getProduct().getId());
                        viewHolder.order_amount_count.setText(String.valueOf(item.getCount() + 1));
                    }
                }
            });

            viewHolder.minus_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getCount() > 0) {
                        callback.changedBoxOrCount((-2), viewHolder.getAdapterPosition(), item.getProduct().getId());
                        viewHolder.order_amount_count.setText(String.valueOf(item.getCount() - 1));
                    }
                }
            });

            viewHolder.itemView.setOnClickListener(v -> {
                callback.onProductClick(products.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
            });


//        String box = String.valueOf(item.getQuantity() / item.getProduct().getCount_in_box());
//        String pieces = String.valueOf(item.getQuantity() % item.getProduct().getCount_in_box());
//        if (item.getQuantity() >= item.getProduct().getCount_in_box()) {
//            viewHolder.order_amount.setText(box + "кор, " + pieces + " штук");
//        } else {
//            viewHolder.order_amount.setText(pieces + " штук");
//        }

            String box = String.valueOf(item.getCount_boxes());
            String pieces = String.valueOf(item.getCount());

            viewHolder.order_amount.setText(box);
            viewHolder.order_amount_count.setText(pieces);

            callback.changedPosition(viewHolder.getAdapterPosition());
        }catch (Exception e){
            ErrorClass.log(e);
        }

    }

    @Override
    public int getItemCount() {
        if (products != null)
            return products.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, remaining, order_amount, order_amount_count;
        TextView pulus_box, minus_box , minus_count, pulus_count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            remaining = itemView.findViewById(R.id.product_remaining);
            order_amount = itemView.findViewById(R.id.product_order_amount_box);
            order_amount_count = itemView.findViewById(R.id.product_order_amount_count);
            pulus_box = itemView.findViewById(R.id.pulus_box);
            minus_box = itemView.findViewById(R.id.minus_box);
            minus_count = itemView.findViewById(R.id.minus_count);
            pulus_count = itemView.findViewById(R.id.pulus_count);
        }

    }
}
