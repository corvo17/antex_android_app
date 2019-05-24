package uz.codic.ahmadtea.ui.report.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.report.basketList.BasketActivity;
import uz.codic.ahmadtea.utils.CommonUtils;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private List<OrderedList> items;

    public OrderAdapter() {
    }

    public void updateList(List<OrderedList> merchants){
        this.items = merchants;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_planning, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final OrderedList item = items.get(holder.getAdapterPosition());

        holder.name.setText(item.getMerchant().getLabel());
        holder.totalCost.setText(CommonUtils.getFormattedNumber(item.getOrder().getTotal_cost()/100));
        holder.itemView.setOnClickListener(v -> {
            Intent orderedActivity = new Intent(holder.itemView.getContext(), BasketActivity.class);
            orderedActivity.putExtra("orderId", item.getOrder().getId());
            orderedActivity.putExtra("priceId", item.getOrder().getId_price());
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
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.daily_planning_name);

            totalCost = itemView.findViewById(R.id.daily_planning_address);
        }


    }
}
