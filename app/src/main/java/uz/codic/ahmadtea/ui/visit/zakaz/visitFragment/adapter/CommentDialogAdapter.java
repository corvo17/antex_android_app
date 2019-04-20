package uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import java.util.ArrayList;

import uz.codic.ahmadtea.R;

public class CommentDialogAdapter extends RecyclerView.Adapter<CommentDialogAdapter.CommentDialogViewHolder> {

    private ArrayList<String> items;

    public CommentDialogAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CommentDialogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, viewGroup, false);
        return new CommentDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentDialogViewHolder commentDialogViewHolder, int position) {
        commentDialogViewHolder.bindView(items.get(commentDialogViewHolder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class CommentDialogViewHolder extends RecyclerView.ViewHolder {

        private CheckedTextView checkedTextView;

        CommentDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            checkedTextView = itemView.findViewById(R.id.item_check);

            checkedTextView.setOnClickListener(view -> {
                if (checkedTextView.isChecked())
                    checkedTextView.setChecked(false);
                else
                    checkedTextView.setChecked(true);
            });
        }

        void bindView(String title) {
            checkedTextView.setText(title);
        }
    }
}
