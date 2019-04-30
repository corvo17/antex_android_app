package uz.codic.ahmadtea.data.network.model;

import java.util.List;

public class DailyMerchants {

    List<Casual> casual;
    List<Casual> plan;

    public DailyMerchants() {
    }

    public class Casual{
        String merchant_id;
        String workspace_id;

        public Casual() {
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getWorkspace_id() {
            return workspace_id;
        }

        public void setWorkspace_id(String workspace_id) {
            this.workspace_id = workspace_id;
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


