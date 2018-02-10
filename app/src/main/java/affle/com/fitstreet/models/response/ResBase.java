package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/14/16.
 */

public class ResBase {

    @SerializedName("errstr")
    @Expose
    private String errstr;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;

    /**
     * @return The errstr
     */
    public String getErrstr() {
        return errstr;
    }

    /**
     * @param errstr The errstr
     */
    public void setErrstr(String errstr) {
        this.errstr = errstr;
    }

    /**
     * @return The success
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    /**
     * @return The errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode The errorCode
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
