package uz.codic.ahmadtea.ui.sittings;

import java.util.HashMap;

import uz.codic.ahmadtea.ui.base.MvpView;

public interface VersionInfoMvpView extends MvpView {

    void onResponseCurrentVersion(HashMap<String, HashMap<String, String>> stringHashMapHashMap);

}
