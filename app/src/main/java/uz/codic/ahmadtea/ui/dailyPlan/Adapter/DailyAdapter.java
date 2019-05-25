package uz.codic.ahmadtea.ui.dailyPlan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.visit.MerchantActivity;
import uz.codic.ahmadtea.utils.CommonUtils;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    private final Context context;
    private List<WorkspaceAndMerchant> items;
    private DailyCallBack callBack;

    public DailyAdapter(Context context) {
        this.context = context;
    }

    public void updateList(List<WorkspaceAndMerchant> merchants) {
        this.items = merchants;
        notifyDataSetChanged();
    }

    public void setCallBack(DailyCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_planning, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Merchant item = items.get(holder.getAdapterPosition()).getMerchant();
        holder.address.setText(item.getAddress());
        holder.name.setText(item.getLabel());

       // holder.tvSum.setText("sum");
        if (item.getCurrent_balance() != null) {
            if (item.getCurrent_balance() > 0) {
                holder.tvMerchantCurrentBalance.setText(CommonUtils.getFormattedNumber(item.getCurrent_balance()));
                holder.tvMerchantCurrentBalance.setTextColor(Color.parseColor("#A5D6A7"));
                holder.tvSum.setTextColor(Color.parseColor("#A5D6A7"));
            } else if (item.getCurrent_balance() < 0) {
                holder.tvMerchantCurrentBalance.setText("-" + CommonUtils.getFormattedNumber(item.getCurrent_balance() * (-1)));
                holder.tvMerchantCurrentBalance.setTextColor(Color.parseColor("#EF9A9A"));
                holder.tvSum.setTextColor(Color.parseColor("#EF9A9A"));
            } else {
                holder.tvMerchantCurrentBalance.setText(String.valueOf(item.getCurrent_balance()));
                holder.tvMerchantCurrentBalance.setTextColor(Color.parseColor("#737373"));
                holder.tvSum.setTextColor(Color.parseColor("#737373"));
            }
        } else {
            holder.tvMerchantCurrentBalance.setText(String.valueOf(0));
            holder.tvMerchantCurrentBalance.setTextColor(Color.parseColor("#737373"));
            holder.tvSum.setTextColor(Color.parseColor("#737373"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent merchant = new Intent(holder.itemView.getContext(), MerchantActivity.class);
                merchant.putExtra("click", "onClick");
                merchant.putExtra("name", item.getLabel());
                merchant.putExtra("id", item.getPid());
                merchant.putExtra("id_workspace", items.get(holder.getAdapterPosition()).getWorkspace().getId());
                holder.itemView.getContext().startActivity(merchant);
            }
        });

        if (items.get(holder.getAdapterPosition()).getInfoAction() != null) {
            boolean error = false;
            boolean send = false;
            boolean send_draft = false;
            boolean save = false;
            boolean save_pending = false;


            InfoAction action = items.get(holder.getAdapterPosition()).getInfoAction();
            if (action.isError()) error = true;
            if (action.isSave()) save = true;
            if (action.isSave_pending()) save_pending = true;
            if (action.isSend()) send = true;
            if (action.isSend_draft()) send_draft = true;

            if (error) holder.info_error.setVisibility(View.VISIBLE);
            else holder.info_error.setVisibility(View.GONE);

            if (send) holder.info_send.setVisibility(View.VISIBLE);
            else holder.info_send.setVisibility(View.GONE);

            if (send_draft) holder.info_send_draft.setVisibility(View.VISIBLE);
            else holder.info_send_draft.setVisibility(View.GONE);

            if (save) holder.info_save.setVisibility(View.VISIBLE);
            else holder.info_save.setVisibility(View.GONE);

            if (save_pending) holder.info_save_pending.setVisibility(View.VISIBLE);
            else holder.info_save_pending.setVisibility(View.GONE);

        } else {
            holder.info_send.setVisibility(View.GONE);
            holder.info_send_draft.setVisibility(View.GONE);
            holder.info_save_pending.setVisibility(View.GONE);
            holder.info_save.setVisibility(View.GONE);
            holder.info_error.setVisibility(View.GONE);
        }

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
        TextView tvMerchantCurrentBalance;
        TextView tvSum;
        TextView info_send, info_send_draft, info_save, info_save_pending, info_error;

        TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.daily_planning_name);
            address = itemView.findViewById(R.id.daily_planning_address);
            tvMerchantCurrentBalance = itemView.findViewById(R.id.id_merchant_current_balance);
            tvSum = itemView.findViewById(R.id.sum);
            info_send = itemView.findViewById(R.id.info_send);
            info_send_draft = itemView.findViewById(R.id.info_send_draft);
            info_save = itemView.findViewById(R.id.info_save);
            info_save_pending = itemView.findViewById(R.id.info_save_pending);
            info_error = itemView.findViewById(R.id.info_error);
        }

    }
}