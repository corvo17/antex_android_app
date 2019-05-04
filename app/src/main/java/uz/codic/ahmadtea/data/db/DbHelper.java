package uz.codic.ahmadtea.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Currencies;
import uz.codic.ahmadtea.data.db.entities.FileReportType;
import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Measurement;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Mmd;
import uz.codic.ahmadtea.data.db.entities.MmdType;
import uz.codic.ahmadtea.data.db.entities.MyWorkspace;
import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.PaymentType;
import uz.codic.ahmadtea.data.db.entities.PhotoG;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.data.db.entities.Product;
import uz.codic.ahmadtea.data.db.entities.ProductPrice;
import uz.codic.ahmadtea.data.db.entities.Stocks;
import uz.codic.ahmadtea.data.db.entities.User;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.db.entities.WorkspaceMerchant;
import uz.codic.ahmadtea.data.db.entities.WorkspaceMmd;
import uz.codic.ahmadtea.data.db.entities.WorkspacePaymentType;
import uz.codic.ahmadtea.data.db.entities.WorkspacePrice;


//Need to have singleton instance of the DbHelper class

//This is central database





@Database(
        entities = {
                Comment.class,
                Merchant.class,
                Mmd.class,
                PaymentType.class,
                Price.class,
                Product.class,
                ProductPrice.class,
                User.class,
                Workspace.class,
                Measurement.class,
                Currencies.class,
                Visit.class,
                Order.class,
                OrderBasket.class,
                Stocks.class,
                MyWorkspace.class,
                WorkspaceMerchant.class,
                WorkspaceMmd.class,
                MmdType.class,
                WorkspacePrice.class,
                WorkspacePaymentType.class,
                PhotoG.class,
                NewMerchant.class,
                InfoAction.class,
                FileReportType.class

        },
        version = 1,
        exportSchema = false
)
public abstract class DbHelper extends RoomDatabase {

    public abstract DaoAccess daoAccess();

    private static volatile DbHelper INSTANCE;

    public static DbHelper getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DbHelper.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DbHelper.class, "AhmadTea")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
