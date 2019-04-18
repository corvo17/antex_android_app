package uz.codic.ahmadtea.ui.sittings;

import java.io.File;

public interface CallBack {

    void onSuccess(File file);

    void onError(Exception e);

}
