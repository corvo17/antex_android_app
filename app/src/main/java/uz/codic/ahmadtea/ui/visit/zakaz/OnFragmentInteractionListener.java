package uz.codic.ahmadtea.ui.visit.zakaz;

import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import java.io.File;
import java.util.List;

import uz.codic.ahmadtea.data.db.entities.PhysicalWareHouse;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteObject;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteApi;

public interface OnFragmentInteractionListener {

    void transactionFragments(Fragment fragment, String tag);

    void onBackClick(String title);

    void onBackPressed();

    void openMainActivity();

    CompleteApi getCompleteApi();

    CompleteObject getCompleteObject();

    void openGallery(File file);

    void getLocation();

    LinearLayout getLinearButtons();

    List<PhysicalWareHouse> getPhysicalWareHouse();

}
