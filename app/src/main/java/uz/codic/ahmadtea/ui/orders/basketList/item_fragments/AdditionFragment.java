package uz.codic.ahmadtea.ui.orders.basketList.item_fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.BasketProduct;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.CommentsAdapter;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.NotesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdditionFragment extends Fragment {
private Context context;
private RecyclerView recyclerComments, recyclerNotes, recyclerPhotos;
private List<Comment> comments;
private String visitNote, orderNote;
private LinearLayoutManager linearLayoutManager;

    public AdditionFragment() {
        // Required empty public constructor
    }
    public static AdditionFragment getInstance(List<BasketProduct> list, Order order, List<Comment> comments, String visitNote){
        Bundle args = new Bundle();
        args.putParcelableArrayList("comments",(ArrayList)comments);
        args.putString("visitNote", visitNote);
        args.putString("orderNote", order.getNotes());
        AdditionFragment fragment = new AdditionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        comments = getArguments().getParcelableArrayList("comments");
        visitNote = getArguments().getString("visitNote");
        orderNote = getArguments().getString("orderNote");
        return inflater.inflate(R.layout.fragment_comments_and_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerComments = view.findViewById(R.id.recycelrComments);
        recyclerNotes = view.findViewById(R.id.recyclerNotes);
        recyclerPhotos = view.findViewById(R.id.recyclerPhoto);
        linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerComments.setLayoutManager(linearLayoutManager);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(context){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        CommentsAdapter commentsAdapter = new CommentsAdapter(comments);
        recyclerComments.setAdapter(commentsAdapter);

        List<String> notes = new ArrayList<>();
        notes.add(orderNote);
        notes.add(visitNote);
        NotesAdapter notesAdapter = new NotesAdapter(notes);
        recyclerNotes.setAdapter(notesAdapter);

    }
}
