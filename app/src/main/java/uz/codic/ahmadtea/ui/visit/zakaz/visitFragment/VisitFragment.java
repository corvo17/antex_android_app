package uz.codic.ahmadtea.ui.visit.zakaz.visitFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.FileReportType;
import uz.codic.ahmadtea.data.db.entities.PhotoLabel;
import uz.codic.ahmadtea.di.visitFragment.ActivityComponent;
import uz.codic.ahmadtea.di.visitFragment.CameraClass;
import uz.codic.ahmadtea.di.visitFragment.DaggerActivityComponent;
import uz.codic.ahmadtea.di.visitFragment.VisitModule;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.visit.comments.CommentListAdapter;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.camera.CameraFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.prices.PricesFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.visit_photo.PhotoFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.warehouse.WareHouseFragment;

import static uz.codic.ahmadtea.utils.Consts.pricesTag;
import static uz.codic.ahmadtea.utils.Consts.warehouseTag;

public class VisitFragment extends BaseFragment implements VisitFragmentView {

    private OnFragmentInteractionListener mListener;

    LinearLayout lnr_order;
    ImageView ic_cart;
    EditText et_notes_viseit;
    private static final int IMAGE_REQ_CODE = 107;
    private Integer file_report_type_id = 0;
    LinearLayout btnphoto;


    @Inject
    CameraClass myCamera;

    VisitFragmentPresenter<VisitFragmentView> presenter;
    CommentListAdapter adapter;
    ExpandableListView listView;
    List<String> title;
    HashMap<String, List<String>> titleChildren;

    ActivityComponent visitComponent;


    private AlertDialog commentDialog, photoDialog;
    private View view;

    private ArrayList<Integer> choosenComments = new ArrayList<>();
    private String[] comment_ids;

    @Override
    public void onResume() {
        if (mListener.getCompleteApi().getOrderObject() != null) {
            if (mListener.getCompleteApi().getOrderObject().isOrderComplete()) {
                ic_cart.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_shopping_cart));
            }
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visit, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        presenter = new VisitFragmentPresenter<>(this.getContext());
        presenter.onAttach(this);
        CameraFragment mycam = CameraFragment.newInstance();
        visitComponent = DaggerActivityComponent.builder().visitModule(new VisitModule(this.getContext(), this)).build();
        visitComponent.inject(this);
        visitComponent.inject(mycam);
        lnr_order = view.findViewById(R.id.lnr_order);
        ic_cart = view.findViewById(R.id.ic_order);
        //  listView = view.findViewById(R.id.commentListView);
        et_notes_viseit = view.findViewById(R.id.et_notes_visit);
        btnphoto = view.findViewById(R.id.lnr_foto);
        //btn back
        title = new ArrayList<>();
        titleChildren = new HashMap<>();
        title.add("Comments");
        presenter.initialize();

        view.findViewById(R.id.lnr_comments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentDialog();
            }
        });

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (mListener.getCompleteApi().getVisitObject().getNotes() != null) {
            et_notes_viseit.setText(mListener.getCompleteApi().getVisitObject().getNotes());
        }

        et_notes_viseit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mListener.getCompleteApi().getVisitObject().setNotes(et_notes_viseit.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_notes_viseit.setOnTouchListener((v, event) -> {
            //mListener.getLinearButtons().setVisibility(View.GONE);
            return false;
        });

        et_notes_viseit.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                //mListener.getLinearButtons().setVisibility(View.VISIBLE);
                imm = null;
                return true;
            }
            return false;
        });


        if (getActivity() != null) {
            getActivity().findViewById(R.id.btn_back)
                    .setOnClickListener(v -> mListener.onBackPressed());
        }

        lnr_order.setOnClickListener(v -> {
            getActivity().findViewById(R.id.lnr_buttons).setVisibility(View.GONE);
            getActivity().findViewById(R.id.progress_state).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.btn_forward).setVisibility(View.VISIBLE);
            mListener.transactionFragments(WareHouseFragment.newInstance(), warehouseTag);
        });

        presenter.getFileReportTypes();
        btnphoto.setOnClickListener(action -> {

            //But method comment

            showPhotoDialog();

//            if (myCamera.isEmptyy()) {
//                //region No Image yet
//                PopupMenu btnMn = new PopupMenu(this.getContext(), action);
//                btnMn.getMenuInflater().inflate(R.menu.popup_camera_menu, btnMn.getMenu());
//                btnMn.setOnMenuItemClickListener(item -> {
//                    //region Opening Camera
//                    myCamera.capture(item.getTitle().toString());
//                    return true;
//                });
//                btnMn.show();
////                endregion
//
//
//            } else {
//                mListener.transactionFragments(mycam, Consts.cameraTag);
//            }
        });
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 1);
    }

    //region Old Staffs

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public VisitFragment() {
        // Required empty public constructor
    }

    public static VisitFragment newInstance() {
        VisitFragment fragment = new VisitFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    //endregion

    @Override
    public void loadComments(List<Comment> alltitles) {

        createCommentDialog(view, (ArrayList<Comment>) alltitles);
    }

    @Override
    public void loadFileReportType(List<FileReportType> fileReportTypes) {
        createPhotoDialog(view, fileReportTypes);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQ_CODE) {
            myCamera.onResultActivity();
        }
    }

    private void createCommentDialog(View view, ArrayList<Comment> items) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        //LayoutInflater inflater = this.getLayoutInflater();
        //View dialogView = inflater.inflate(R.layout.comment_dialog, null);
        //dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Choosen Comment");
        CharSequence[] choices = new CharSequence[items.size()];
        boolean[] choicesInitial = new boolean[items.size()];

        for (int i = 0; i < items.size(); i++) {
            choices[i] = items.get(i).getLabel();
            choicesInitial[i] = false;
        }

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (choosenComments.size() > 0) {
                    mListener.getCompleteApi().getVisitObject().setId_comment(choosenComments.toString());
                } else mListener.getCompleteApi().getVisitObject().setId_comment(null);
            }
        });

        dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                commentDialog.dismiss();
            }
        });

        dialogBuilder.setMultiChoiceItems(choices, choicesInitial, (dialogInterface, position, isChecked) -> {
            if (isChecked)
                choosenComments.add(items.get(position).getId());
            else {
                Integer item = items.get(position).getId();
                choosenComments.remove(item);
            }
        });
        commentDialog = dialogBuilder.create();
    }


    private void createPhotoDialog(View view, List<FileReportType> items) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        //LayoutInflater inflater = this.getLayoutInflater();
        //View dialogView = inflater.inflate(R.layout.comment_dialog, null);
        //dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Choosen Photo");
        CharSequence[] choices = new CharSequence[items.size()];
        boolean[] choicesInitial = new boolean[items.size()];

        for (int i = 0; i < items.size(); i++) {
            choices[i] = items.get(i).getLabel();
            choicesInitial[i] = false;
        }

        dialogBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
            if (choosenComments.size() > 0) {
                //mListener.getCompleteApi().getVisitObject().setId_comment(choosenComments.toString());
            } else {

            }
            //mListener.getCompleteApi().getVisitObject().setId_comment(null);
            mListener.transactionFragments(PhotoFragment.newInstance(file_report_type_id,items.get(file_report_type_id).getLabel() ), items.get(file_report_type_id).getLabel());
            getActivity().findViewById(R.id.lnr_buttons).setVisibility(View.GONE);

        });

        dialogBuilder.setNegativeButton("CANCEL", (dialogInterface, i) -> commentDialog.dismiss());

        dialogBuilder.setSingleChoiceItems(choices, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                Log.d("baxtiyor", "createPhotoDialog: " + items.get(position));
                file_report_type_id = position;
            }
        });
        photoDialog = dialogBuilder.create();
    }


    private void showCommentDialog() {
        commentDialog.show();
    }


    private void showPhotoDialog() {
        photoDialog.show();
    }
}
