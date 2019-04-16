package uz.codic.ahmadtea.data;

import uz.codic.ahmadtea.data.db.DaoAccess;
import uz.codic.ahmadtea.data.network.ApiCentral;
import uz.codic.ahmadtea.data.network.ApiService;
import uz.codic.ahmadtea.data.prefs.PrefHelper;

public interface DataManager extends ApiService, ApiCentral, DaoAccess, PrefHelper {
}
