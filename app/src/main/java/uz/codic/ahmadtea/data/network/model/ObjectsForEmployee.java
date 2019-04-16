package uz.codic.ahmadtea.data.network.model;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.MyWorkspace;
import uz.codic.ahmadtea.data.db.entities.PaymentType;
import uz.codic.ahmadtea.data.db.entities.Price;
import uz.codic.ahmadtea.data.db.entities.Stocks;
import uz.codic.ahmadtea.data.db.entities.Mmd;


public class ObjectsForEmployee {

    List<MyWorkspace> my_workspaces;

    public ObjectsForEmployee(){

    }

    public List<MyWorkspace> getMy_workspaces() {
        return my_workspaces;
    }

    public void setMy_workspaces(List<MyWorkspace> my_workspaces) {
        this.my_workspaces = my_workspaces;
    }
}
