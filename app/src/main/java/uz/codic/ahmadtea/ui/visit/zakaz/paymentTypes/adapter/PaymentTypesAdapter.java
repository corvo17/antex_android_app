package uz.codic.ahmadtea.ui.visit.zakaz.paymentTypes.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.PaymentType;

public class PaymentTypesAdapter extends RecyclerView.Adapter<PaymentTypesAdapter.ViewHolder> {

    List<PaymentType> paymentTypes;

    Callback callback;

    private static int lastCheckedPos = -2;
    public void updateList(List<PaymentType> paymentTypes){
        this.paymentTypes = paymentTypes;
        notifyDataSetChanged();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_choice, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (paymentTypes.get(viewHolder.getAdapterPosition()).isChecked())
            viewHolder.checkBox.setChecked(true);
        else
            viewHolder.checkBox.setChecked(false);
        viewHolder.checkBox.setText(paymentTypes.get(viewHolder.getAdapterPosition()).getLabel());
        int position = viewHolder.getAdapterPosition();
        viewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                if(lastCheckedPos >= 0){
                    paymentTypes.get(lastCheckedPos).setChecked(false);
                    notifyDataSetChanged();
                }
                paymentTypes.get(position).setChecked(true);
                lastCheckedPos = position;
                callback.onItemClick(paymentTypes.get(position).getId());
            }
        });
        if (paymentTypes.size()==1){
            paymentTypes.get(position).setChecked(true);
            lastCheckedPos = position;
            callback.onItemClick(paymentTypes.get(position).getId());
        }
    }

    @Override
    public int getItemCount() {
        if (paymentTypes != null)
            return paymentTypes.size();
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
