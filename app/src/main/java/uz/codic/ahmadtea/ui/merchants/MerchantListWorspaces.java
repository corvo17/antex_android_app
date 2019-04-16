package uz.codic.ahmadtea.ui.merchants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Workspace;

public class MerchantListWorspaces implements Serializable {

    Merchant merchant;

    List<Workspace> workspaces;

    List<InfoAction> infos;

    public MerchantListWorspaces() {
        workspaces = new ArrayList<>();
        infos = new ArrayList<>();
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public int getSizeWorkspaces(){
        return workspaces.size();
    }

    public void setWorkspace(Workspace workspace){
        workspaces.add(workspace);
    }

    public void setInfos(InfoAction infoAction){
        infos.add(infoAction);
    }

    public List<InfoAction> getInfos() {
        return infos;
    }

    public void setInfos(List<InfoAction> infos) {
        this.infos = infos;
    }

    @Override
    public String toString() {
        return "MerchantListWorspaces{" +
                "merchant=" + merchant +
                ", workspaces=" + workspaces +
                ", infos=" + infos +
                '}';
    }
}
