package uz.codic.ahmadtea.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class RequestWithWorkspaceId {

    @SerializedName("params")
    Map<Object, Object> params;

    public RequestWithWorkspaceId(){

    }

    public void setParams(Map<Object, Object> params) {
        this.params = params;
    }

    public Map<Object, Object> getParams() {
        return params;
    }
}


