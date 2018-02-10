package affle.com.fitstreet.models.response;

/**
 * Created by akash on 27/6/16.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ResGetCouponData extends ResBase {


    @SerializedName("coupanCatData")
    @Expose
    private List<ResCouponData> coupanCatData = new ArrayList<>();



    /**
     *
     * @return
     * The coupanCatData
     */
    public List<ResCouponData> getCoupanCatData() {
        return coupanCatData;
    }

    /**
     *
     * @param coupanCatData
     * The coupanCatData
     */
    public void setCoupanCatData(List<ResCouponData> coupanCatData) {
        this.coupanCatData = coupanCatData;
    }

}
