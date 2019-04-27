package uz.codic.ahmadtea.ui.mainpage.leftusers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.ui.mainpage.MainActivityView;

public class LeftUsersAdapter extends RecyclerView.Adapter<LeftUserViewHolder> {

    List<User> users;
    MainActivityView baseView;

    public LeftUsersAdapter(MainActivityView baseView) {
        users = new ArrayList<>();
        this.baseView = baseView;
    }

    @NonNull
    @Override
    public LeftUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LeftUserViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itm_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LeftUserViewHolder leftUserViewHolder, int i) {
        String startLetter = Character.toString(users.get(i).getName().charAt(0));
        String endLetter = Character.toString(users.get(i).getName().charAt(users.get(i).getName().length() - 1));
        leftUserViewHolder.userName.setText(startLetter.toUpperCase() + endLetter.toLowerCase());
        leftUserViewHolder.left_user_layout.setOnClickListener(action -> {
            baseView.initializeUserForButton(users.get(i));
        });
    }

    public void setData(List<User> all) {
        this.users = all;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
