package uz.codic.ahmadtea.ui.visit.zakaz.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.di.visitFragment.CameraClass;
import uz.codic.ahmadtea.di.visitFragment.DaggerActivityComponent;
import uz.codic.ahmadtea.ui.base.BaseFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.camera.imageAdapter.CameraAdapter;
import uz.codic.ahmadtea.ui.visit.zakaz.camera.imageAdapter.CameraModule;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;
import uz.codic.ahmadtea.utils.Consts;

import static uz.codic.ahmadtea.utils.Consts.pricesTag;

public class CameraFragment extends BaseFragment implements CameraFragmentView {

    private static final int IMAGE_REQ_CODE = 107;
    private OnFragmentInteractionListener mListener;
    CameraFragmentPresenter<CameraFragmentView> presenter;
    RecyclerView imageHolder;
    CameraAdapter holderAdapter;
    File OldimgFile;
    String CompressionPath;

    DaggerActivityComponent daggerActivityComponent;
    @Inject
    CameraClass myCamera;

    public CameraFragment() {
        // Required empty public constructor
    }


    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        presenter = new CameraFragmentPresenter<>(this.getContext());
        presenter.onAttach(this);
        imageHolder = view.findViewById(R.id.allImages);
        //holderAdapter = new CameraAdapter(presenter.AllImages(), this);
        holderAdapter = new CameraAdapter(null, this);
        presenter.LoadImages();
        imageHolder.setAdapter(holderAdapter);
        getActivity().findViewById(R.id.cameraBtn).setVisibility(View.VISIBLE);//cameraBtn
        getActivity().findViewById(R.id.cameraBtn).setOnClickListener(action -> {
            PopupMenu btnMn = new PopupMenu(this.getContext(), action);
            btnMn.getMenuInflater().inflate(R.menu.popup_camera_menu, btnMn.getMenu());
            btnMn.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //region Opening Camera
                    myCamera.capture(item.getTitle().toString());
                    return true;
                }
            });
            btnMn.show();
            //endregion
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQ_CODE) {
            myCamera.onResultActivity();
            mListener.transactionFragments(VisitFragment.newInstance(), Consts.visitTag);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().findViewById(R.id.cameraBtn).setVisibility(View.GONE);
    }

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

    @Override
    public void loadDataImage(List<CameraModule> alldata) {
        holderAdapter.loadImages(alldata);
    }

    @Override
    public void openImage(File file) {
        mListener.openGallery(file);
    }

}
