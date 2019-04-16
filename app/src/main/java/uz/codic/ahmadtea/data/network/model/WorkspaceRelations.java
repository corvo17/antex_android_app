package uz.codic.ahmadtea.data.network.model;

import java.util.List;

import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.db.entities.WorkspaceMerchant;
import uz.codic.ahmadtea.data.db.entities.WorkspaceMmd;
import uz.codic.ahmadtea.data.db.entities.WorkspacePaymentType;
import uz.codic.ahmadtea.data.db.entities.WorkspacePrice;

public class WorkspaceRelations {

    List<WorkspaceMmd> workspaces_mmds;
    List<Workspace> all_workspaces;
    List<WorkspaceMerchant> workspaces_merchants;
    List<WorkspacePrice> workspaces_prices;
    List<WorkspacePaymentType> workspaces_payment_types;

    public List<WorkspaceMmd> getWorkspaces_mmds() {
        return workspaces_mmds;
    }

    public void setWorkspaces_mmds(List<WorkspaceMmd> workspaces_mmds) {
        this.workspaces_mmds = workspaces_mmds;
    }

    public List<Workspace> getAll_workspaces() {
        return all_workspaces;
    }

    public void setAll_workspaces(List<Workspace> all_workspaces) {
        this.all_workspaces = all_workspaces;
    }

    public List<WorkspaceMerchant> getWorkspaces_merchants() {
        return workspaces_merchants;
    }

    public void setWorkspaces_merchants(List<WorkspaceMerchant> workspaces_merchants) {
        this.workspaces_merchants = workspaces_merchants;
    }

    public List<WorkspacePrice> getWorkspaces_prices() {
        return workspaces_prices;
    }

    public void setWorkspaces_prices(List<WorkspacePrice> workspaces_prices) {
        this.workspaces_prices = workspaces_prices;
    }

    public List<WorkspacePaymentType> getWorkspaces_payment_types() {
        return workspaces_payment_types;
    }

    public void setWorkspaces_payment_types(List<WorkspacePaymentType> workspaces_payment_types) {
        this.workspaces_payment_types = workspaces_payment_types;
    }
}
