package uz.codic.ahmadtea.ui.report.basketList.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import uz.codic.ahmadtea.R;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.ViewHolder>{

    private ArrayList<BasketProduct> items;
    public AllProductsAdapter(ArrayList<BasketProduct> list) {
        this.items = list;
    }

    @Override
    public AllProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_product, parent, false);
        return new AllProductsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AllProductsAdapter.ViewHolder holder, int position) {
        holder.number.setText((1+position)+".");
        holder.name.setText(items.get(position).getProduct().getLabel());
        holder.price.setText(items.get(position).getProductPrice().getValue()/100 + "");
        holder.count.setText(items.get(position).getOrderBasket().getTotal_count()+ "");
        holder.total.setText((items.get(position).getOrderBasket().getTotal_count() *items.get(position).getProductPrice().getValue())/100 + "");

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView number, name,price, count, total;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            count = itemView.findViewById(R.id.count);
            total = itemView.findViewById(R.id.total);
        }


    }
}
