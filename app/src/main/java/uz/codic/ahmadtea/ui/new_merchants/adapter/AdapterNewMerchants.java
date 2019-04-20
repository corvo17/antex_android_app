package uz.codic.ahmadtea.ui.new_merchants.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.ui.visit.MerchantActivity;

public class AdapterNewMerchants extends RecyclerView.Adapter<AdapterNewMerchants.ViewHolder> {
    List<NewMerchant> merchants;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_merchants, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.tvMerchantName.setText(merchants.get(holder.getAdapterPosition()).getName());
        holder.tvMerchantAddress.setText(merchants.get(holder.getAdapterPosition()).getAddress());
        holder.tvSizeWorkspaces.setText("");

        holder.linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), MerchantActivity.class);
            intent.putExtra("click", "onClick");

            intent.putExtra("name", merchants.get(holder.getAdapterPosition()).getName());
            intent.putExtra("id", merchants.get(holder.getAdapterPosition()).getPid());
            intent.putExtra("id_workspace", merchants.get(holder.getAdapterPosition()).getId_workspace());
            holder.itemView.getContext().startActivity(intent);
        });
        holder.linearLayout.setOnLongClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), MerchantActivity.class);
            intent.putExtra("click", "longClick");

            intent.putExtra("name", merchants.get(holder.getAdapterPosition()).getName());
            intent.putExtra("id", merchants.get(holder.getAdapterPosition()).getPid());
            intent.putExtra("id_workspace", merchants.get(holder.getAdapterPosition()).getId_workspace());
            holder.itemView.getContext().startActivity(intent);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        if (merchants != null){
            return merchants.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMerchantName;
        TextView tvMerchantAddress;
        TextView tvSizeWorkspaces;
        RelativeLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMerchantName = itemView.findViewById(R.id.id_merchant_name);
            tvMerchantAddress = itemView.findViewById(R.id.id_merchant_address);
            tvSizeWorkspaces = itemView.findViewById(R.id.size_workspaces);
            linearLayout = itemView.findViewById(R.id.id_linear_merchant);
        }
    }

    public void setMerchants(List<NewMerchant> merchants) {
        this.merchants = merchants;
        notifyDataSetChanged();
    }
}
