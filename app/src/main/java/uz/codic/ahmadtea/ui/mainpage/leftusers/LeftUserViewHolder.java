package uz.codic.ahmadtea.ui.mainpage.leftusers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uz.codic.ahmadtea.R;

public class LeftUserViewHolder extends RecyclerView.ViewHolder {

    public TextView userName;
    View view;
    public LeftUserViewHolder(@NonNull View itemView) {
        super(itemView);
        view=itemView;
        userName = itemView.findViewById(R.id.userText);
    }

    public View getView() {
        return view;
    }
}
