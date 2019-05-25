package uz.codic.ahmadtea.ui.visit.zakaz.prices;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.ui.visit.zakaz.paymentTypes.adapter.Callback;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {

    List<Price> priceList;

    Callback callback;
    private static int lastCheckedPos = -2;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void updateList(List<Price> priceList) {
        this.priceList = priceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_choice, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (priceList.get(viewHolder.getAdapterPosition()).isChecked())
            viewHolder.checkBox.setChecked(true);
        else
            viewHolder.checkBox.setChecked(false);

//        if (lastCheckedPos == i) {
//            viewHolder.checkBox.setChecked(true);
//        }
        viewHolder.checkBox.setText(priceList.get(viewHolder.getAdapterPosition()).getLabel());
        int position = viewHolder.getAdapterPosition();
        viewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if(lastCheckedPos >= 0 && lastCheckedPos != position){
                    priceList.get(lastCheckedPos).setChecked(false);
                    notifyItemChanged(lastCheckedPos);
                }
                priceList.get(position).setChecked(true);
                lastCheckedPos = position;
                callback.onItemClick(priceList.get(position).getId());
            }
        });

        if (priceList.size()==1){
            priceList.get(position).setChecked(true);
            lastCheckedPos = position;
            callback.onItemClick(priceList.get(position).getId());
        }

    }

    @Override
    public int getItemCount() {
        if (priceList != null)
            return priceList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_choice);
        }
    }
}
