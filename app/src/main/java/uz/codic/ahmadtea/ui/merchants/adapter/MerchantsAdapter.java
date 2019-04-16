package uz.codic.ahmadtea.ui.merchants.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.ui.merchants.MerchantListWorspaces;
import uz.codic.ahmadtea.ui.visit.MerchantActivity;
import uz.codic.ahmadtea.utils.CommonUtils;

public class MerchantsAdapter extends RecyclerView.Adapter<MerchantsAdapter.ViewHolder> {

    List<MerchantListWorspaces> merchants;
    int bookmark = -1;
    CallBackMerchants callback;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_merchants, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Merchant item = merchants.get(i).getMerchant();
        viewHolder.tvMerchantName.setText(item.getLabel());
        viewHolder.tvMerchantAddress.setText(item.getAddress());
        viewHolder.tvSum.setText("sum");
        if (item.getCurrent_balance() > 0) {
            viewHolder.tvMerchantCurrentBalance.setText(CommonUtils.getFormattedNumber(item.getCurrent_balance()));
            viewHolder.tvMerchantCurrentBalance.setTextColor(Color.parseColor("#A5D6A7"));
            viewHolder.tvSum.setTextColor(Color.parseColor("#A5D6A7"));
        } else if (item.getCurrent_balance() < 0) {
            viewHolder.tvMerchantCurrentBalance.setText("-" + CommonUtils.getFormattedNumber(item.getCurrent_balance() * (-1)));
            viewHolder.tvMerchantCurrentBalance.setTextColor(Color.parseColor("#EF9A9A"));
            viewHolder.tvSum.setTextColor(Color.parseColor("#EF9A9A"));
        } else {
            viewHolder.tvMerchantCurrentBalance.setText(String.valueOf(item.getCurrent_balance()));
            viewHolder.tvMerchantCurrentBalance.setTextColor(Color.parseColor("#737373"));
            viewHolder.tvSum.setTextColor(Color.parseColor("#737373"));
        }

        if (merchants.get(i).getSizeWorkspaces() == 1) {
            viewHolder.tvSizeWorkspaces.setText("");
        } else {
            viewHolder.tvSizeWorkspaces.setText(merchants.get(i).getSizeWorkspaces() + "");
        }

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), MerchantActivity.class);
                intent.putExtra("click", "onClick");
                if (merchants.get(i).getSizeWorkspaces() > 1) {

                    intent.putExtra("name", item.getLabel());
                    intent.putExtra("id", item.getPid());

                    AlertDialog.Builder builder = new AlertDialog.Builder(callback.getThisActivity());
                    builder.setTitle("Selected Workspace").setItems(getWorkspaceNames(i), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intent.putExtra("id_workspace", merchants.get(i).getWorkspaces().get(which).getId());
                            viewHolder.itemView.getContext().startActivity(intent);
                        }
                    });
                    builder.show();

                } else {
                    intent.putExtra("name", item.getLabel());
                    intent.putExtra("id", item.getPid());
                    intent.putExtra("id_workspace", merchants.get(i).getWorkspaces().get(0).getId());
                    viewHolder.itemView.getContext().startActivity(intent);
                }
            }
        });

        viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), MerchantActivity.class);
                intent.putExtra("click", "longClick");
                intent.putExtra("name", item.getLabel());
                intent.putExtra("id", item.getPid());
                intent.putExtra("id_workspace", merchants.get(i).getWorkspaces().get(0).getId());
                viewHolder.itemView.getContext().startActivity(intent);
                return true;
            }
        });

        if (!merchants.get(i).getInfos().isEmpty()){
            Log.d("baxtiyor", "ifff: " + merchants.get(i).getInfos());
            boolean error = false;
            boolean send = false;
            boolean send_draft = false;
            boolean save = false;
            boolean save_pending = false;



            for (InfoAction action :merchants.get(i).getInfos()) {
                Log.d("baxtiyor", "info: " + action);
                if (action.isError())error = true;
                if (action.isSave()) save = true;
                if (action.isSave_pending()) save_pending = true;
                if (action.isSend()) send = true;
                if (action.isSend_draft()) send_draft = true;
            }

            if (error) viewHolder.info_error.setVisibility(View.VISIBLE);
            else viewHolder.info_error.setVisibility(View.GONE);

            if (send) viewHolder.info_send.setVisibility(View.VISIBLE);
            else viewHolder.info_send.setVisibility(View.GONE);

            if (send_draft) viewHolder.info_send_draft.setVisibility(View.VISIBLE);
            else viewHolder.info_send_draft.setVisibility(View.GONE);

            if (save) viewHolder.info_save.setVisibility(View.VISIBLE);
            else viewHolder.info_save.setVisibility(View.GONE);

            if (save_pending) viewHolder.info_save_pending.setVisibility(View.VISIBLE);
            else viewHolder.info_save_pending.setVisibility(View.GONE);

        }else{
            viewHolder.info_send.setVisibility(View.GONE);
            viewHolder.info_send_draft.setVisibility(View.GONE);
            viewHolder.info_save_pending.setVisibility(View.GONE);
            viewHolder.info_save.setVisibility(View.GONE);
            viewHolder.info_error.setVisibility(View.GONE);
        }

        //Log.d("baxtiyor", "onBindViewHolder: " + merchants.get(i).getInfos());
        callback.changedPosition(i);

    }

    private CharSequence[] getWorkspaceNames(int pisition) {


        List<String> names = new ArrayList<>();
        for (int i = 0; i < merchants.get(pisition).getSizeWorkspaces(); i++) {
            names.add(merchants.get(pisition).getWorkspaces().get(i).getName());
        }
        CharSequence[] items = names.toArray(new CharSequence[0]);
        return items;
    }

    @Override
    public int getItemCount() {
        if (merchants != null)
            return merchants.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMerchantName;
        TextView tvMerchantAddress;
        TextView tvMerchantCurrentBalance;
        TextView tvSum;
        TextView tvSizeWorkspaces;
        TextView info_send, info_send_draft, info_save, info_save_pending, info_error;
        ConstraintLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMerchantName = itemView.findViewById(R.id.id_merchant_name);
            tvMerchantAddress = itemView.findViewById(R.id.id_merchant_address);
            tvMerchantCurrentBalance = itemView.findViewById(R.id.id_merchant_current_balance);
            tvSum = itemView.findViewById(R.id.sum);
            tvSizeWorkspaces = itemView.findViewById(R.id.size_workspaces);
            linearLayout = itemView.findViewById(R.id.id_linear_merchant);
            info_send = itemView.findViewById(R.id.info_send);
            info_send_draft = itemView.findViewById(R.id.info_send_draft);
            info_save = itemView.findViewById(R.id.info_save);
            info_save_pending = itemView.findViewById(R.id.info_save_pending);
            info_error = itemView.findViewById(R.id.info_error);


        }
    }


    public void setCallback(CallBackMerchants callback) {
        this.callback = callback;
    }


    public void setMerchants(List<MerchantListWorspaces> merchants) {
        this.merchants = merchants;
        notifyDataSetChanged();
    }
}
