package uz.codic.ahmadtea.ui.mainpage;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;

public interface FragmentsListener {
    void setToolbarTitle(String title);

    List<WorkspaceAndMerchant> getmMerchants();
}
