package uz.codic.ahmadtea.ui.saved_visits;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.report.adapter.OrderedList;
import uz.codic.ahmadtea.ui.report.basketList.BasketActivity;
public class AdapterSavedVisits extends RecyclerView.Adapter<AdapterSavedVisits.ViewHolder>{

    private List<OrderedList> items;

    public AdapterSavedVisits() {
    }

    public void updateList(List<OrderedList> merchants){

        this.items = merchants;
        notifyDataSetChanged();
    }
    @Override
    public AdapterSavedVisits.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_visits_list, parent, false);
        return new AdapterSavedVisits.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterSavedVisits.ViewHolder holder, int position) {
        final OrderedList item = items.get(holder.getAdapterPosition());
        holder.name.setText(item.getMerchant().getLabel());
        holder.totalCost.setText(item.getOrder().getTotal_cost() + "");


        holder.lnr_merchants.setOnClickListener(v -> {
            Intent orderedActivity = new Intent(holder.itemView.getContext(), BasketActivity.class);
            orderedActivity.putExtra("orderId", item.getOrder().getId());
            orderedActivity.putExtra("priceId", item.getOrder().getId_price());
            orderedActivity.putExtra("order", items.get(position).getOrder());
            holder.itemView.getContext().startActivity(orderedActivity);
        });
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView distance;
        ImageView bg_circle;
        TextView totalCost;
        LinearLayout lnr_merchants;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            lnr_merchants = itemView.findViewById(R.id.merchants);
            totalCost = itemView.findViewById(R.id.address);
        }


    }
}
