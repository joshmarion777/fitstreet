package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 27/9/16.
 */
public class ResContactUs extends ResBase {
    @SerializedName("contactData")
    @Expose
    private ContactData contactData;

    /**
     * @return The contactData
     */
    public ContactData getContactData() {
        return contactData;
    }

    /**
     * @param contactData The contactData
     */
    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }


    public class ContactData {
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("startTime")
        @Expose
        private String startTime;
        @SerializedName("endTime")
        @Expose
        private String endTime;
        @SerializedName("timeStsing")
        @Expose
        private String timeStsing;
        @SerializedName("csEmail")
        @Expose
        private String csEmail;
        @SerializedName("bsEmail")
        @Expose
        private String bsEmail;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("pincode")
        @Expose
        private String pincode;

        /**
         * @return The phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         * @param phone The phone
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * @return The startTime
         */
        public String getStartTime() {
            return startTime;
        }

        /**
         * @param startTime The startTime
         */
        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        /**
         * @return The endTime
         */
        public String getEndTime() {
            return endTime;
        }

        /**
         * @param endTime The endTime
         */
        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        /**
         * @return The timeStsing
         */
        public String getTimeStsing() {
            return timeStsing;
        }

        /**
         * @param timeStsing The timeStsing
         */
        public void setTimeStsing(String timeStsing) {
            this.timeStsing = timeStsing;
        }

        /**
         * @return The csEmail
         */
        public String getCsEmail() {
            return csEmail;
        }

        /**
         * @param csEmail The csEmail
         */
        public void setCsEmail(String csEmail) {
            this.csEmail = csEmail;
        }

        /**
         * @return The bsEmail
         */
        public String getBsEmail() {
            return bsEmail;
        }

        /**
         * @param bsEmail The bsEmail
         */
        public void setBsEmail(String bsEmail) {
            this.bsEmail = bsEmail;
        }

        /**
         * @return The address
         */
        public String getAddress() {
            return address;
        }

        /**
         * @param address The address
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * @return The city
         */
        public String getCity() {
            return city;
        }

        /**
         * @param city The city
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         * @return The pincode
         */
        public String getPincode() {
            return pincode;
        }

        /**
         * @param pincode The pincode
         */
        public void setPincode(String pincode) {
            this.pincode = pincode;
        }
    }
}

