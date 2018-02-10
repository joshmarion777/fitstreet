package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 7/10/16.
 */
public class ReqSendToBank extends ReqBase{
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("accountHolderName")
    @Expose
    private String accountHolderName;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("ifscCode")
    @Expose
    private String ifscCode;
    @SerializedName("amount")
    @Expose
    private String amount;
    /**
     *
     * @return
     * The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     * The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     *
     * @return
     * The accountHolderName
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }

    /**
     *
     * @param accountHolderName
     * The accountHolderName
     */
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    /**
     *
     * @return
     * The accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     *
     * @param accountNumber
     * The accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     *
     * @return
     * The bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     *
     * @param bankName
     * The bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     *
     * @return
     * The ifscCode
     */
    public String getIfscCode() {
        return ifscCode;
    }

    /**
     *
     * @param ifscCode
     * The ifscCode
     */
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    /**
     *
     * @return
     * The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

}
