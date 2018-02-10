
package affle.com.fitstreet.models.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResCouponsDetail extends ResBase {


    @SerializedName("couponData")
    @Expose
    private CouponData couponData;

    /**
     * 
     * @return
     *     The couponData
     */
    public CouponData getCouponData() {
        return couponData;
    }

    /**
     * 
     * @param couponData
     *     The couponData
     */
    public void setCouponData(CouponData couponData) {
        this.couponData = couponData;
    }

}
