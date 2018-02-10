package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 3/10/16.
 */
public class ResMyWallet extends ResBase{
    @SerializedName("totalAmt")
    @Expose
    private String totalAmt;
    @SerializedName("cashBackAmt")
    @Expose
    private String cashBackAmt;
    @SerializedName("refundAmt")
    @Expose
    private String refundAmt;
    @SerializedName("prizeAmt")
    @Expose
    private String prizeAmt;

    /**
     *
     * @return
     * The totalAmt
     */
    public String getTotalAmt() {
        return totalAmt;
    }

    /**
     *
     * @param totalAmt
     * The totalAmt
     */
    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    /**
     *
     * @return
     * The cashBackAmt
     */
    public String getCashBackAmt() {
        return cashBackAmt;
    }

    /**
     *
     * @param cashBackAmt
     * The cashBackAmt
     */
    public void setCashBackAmt(String cashBackAmt) {
        this.cashBackAmt = cashBackAmt;
    }

    /**
     *
     * @return
     * The refundAmt
     */
    public String getRefundAmt() {
        return refundAmt;
    }

    /**
     *
     * @param refundAmt
     * The refundAmt
     */
    public void setRefundAmt(String refundAmt) {
        this.refundAmt = refundAmt;
    }

    /**
     *
     * @return
     * The prizeAmt
     */
    public String getPrizeAmt() {
        return prizeAmt;
    }

    /**
     *
     * @param prizeAmt
     * The prizeAmt
     */
    public void setPrizeAmt(String prizeAmt) {
        this.prizeAmt = prizeAmt;
    }

}
