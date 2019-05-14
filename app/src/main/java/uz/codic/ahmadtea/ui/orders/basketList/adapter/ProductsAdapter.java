package uz.codic.ahmadtea.ui.orders.basketList.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uz.codic.ahmadtea.R;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>{

    List<BasketProduct> list;
    public void updateList(List<BasketProduct> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_saved_product, viewGroup, false);
        return new ProductsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder viewHolder, int i) {
        BasketProduct item = list.get(viewHolder.getAdapterPosition());

        viewHolder.name.setText(item.getProduct().getLabel());
        viewHolder.price.setText(Integer.toString(item.getProductPrice().getValue()));
        viewHolder.count.setText(Integer.toString(item.getOrderBasket().getCount()));

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView number, name, price,count, total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            name= itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            count = itemView.findViewById(R.id.count);
            total = itemView.findViewById(R.id.total);
        }
    }
}