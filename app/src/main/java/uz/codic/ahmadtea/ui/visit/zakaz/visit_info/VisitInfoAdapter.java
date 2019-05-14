package uz.codic.ahmadtea.ui.visit.zakaz.visit_info;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.utils.CommonUtils;

import static uz.codic.ahmadtea.utils.Consts.statusPending;
import static uz.codic.ahmadtea.utils.Consts.statusSaveAsDraft;
import static uz.codic.ahmadtea.utils.Consts.statusSendAsDraft;
import static uz.codic.ahmadtea.utils.Consts.statusSent;

class VisitInfoAdapter extends RecyclerView.Adapter<VisitInfoAdapter.Holder> {

    List<Order> orders;

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_visit_info, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        int position = holder.getAdapterPosition();
        if (orders.get(position).getStatus().equals(statusSent)){
            holder.info.setText("Send\n");
            holder.itemView.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else if (orders.get(position).getStatus().equals(statusSendAsDraft)){
            holder.info.setText("Send as draft\n");
            holder.itemView.setBackgroundColor(Color.parseColor("#2DF3E1"));
        } else if (orders.get(position).getStatus().equals(statusPending)){
            holder.info.setText("Save as pending\n");
            holder.itemView.setBackgroundColor(Color.parseColor("#FF9800"));
        } else if (orders.get(position).getStatus().equals(statusSaveAsDraft)){
            holder.info.setText("Save Locally\n");
            holder.itemView.setBackgroundColor(Color.parseColor("#9FA8DA"));
        }
        holder.info.append(CommonUtils.getFormattedNumber(orders.get(position).getTotal_cost()) + "");

    }

    @Override
    public int getItemCount() {
        if (orders != null) return orders.size();
        else return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView info;

        public Holder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.info);
        }
    }
}
