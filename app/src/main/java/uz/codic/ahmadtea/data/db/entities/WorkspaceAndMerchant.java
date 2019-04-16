package uz.codic.ahmadtea.data.db.entities;

import android.arch.persistence.room.Embedded;

import java.io.Serializable;

public class WorkspaceAndMerchant implements Serializable {

    @Embedded(prefix = "w_")
    Workspace workspace;

    @Embedded
    Merchant merchant;

    @Embedded
    InfoAction infoAction;


    public WorkspaceAndMerchant() {
    }


    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public InfoAction getInfoAction() {
        return infoAction;
    }

    public void setInfoAction(InfoAction infoAction) {
        this.infoAction = infoAction;
    }

    @Override
    public String toString() {
        return "WorkspaceAndMerchant{" +
                "workspace=" + workspace +
                ", merchant=" + merchant +
                ", infoAction=" + infoAction +
                '}';
    }
}
