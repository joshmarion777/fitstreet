package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/14/16.
 */
public class ReqBase {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("service_key")
    @Expose
    private String serviceKey;

    /**
     * @return The method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method The method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return The serviceKey
     */
    public String getServiceKey() {
        return serviceKey;
    }

    /**
     * @param serviceKey The service_key
     */
    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

}
