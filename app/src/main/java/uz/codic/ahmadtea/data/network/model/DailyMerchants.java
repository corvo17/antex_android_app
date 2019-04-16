package uz.codic.ahmadtea.data.network.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyMerchants {

    List<Casual> casual;
    List<Casual> plan;

    public DailyMerchants() {
    }

    public class Casual{
        String id_merchant;
        String id_workspace;

        public Casual() {
        }

        public String getId_merchant() {
            return id_merchant;
        }

        public void setId_merchant(String id_merchant) {
            this.id_merchant = id_merchant;
        }

        public String getId_workspace() {
            return id_workspace;
        }

        public void setId_workspace(String id_workspace) {
            this.id_workspace = id_workspace;
        }
    }


    public List<Casual> getCasual() {
        return casual;
    }

    public void setCasual(List<Casual> casual) {
        this.casual = casual;
    }

    public List<Casual> getPlan() {
        return plan;
    }

    public void setPlan(List<Casual> plan) {
        this.plan = plan;
    }
}


