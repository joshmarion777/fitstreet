package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 21/10/16.
 */
public class ResPushNotification extends ResBase {
    @SerializedName("notificationData")
    @Expose
    private List<NotificationDatum> notificationData = new ArrayList<NotificationDatum>();

    /**
     * @return The notificationData
     */
    public List<NotificationDatum> getNotificationData() {
        return notificationData;
    }

    /**
     * @param notificationData The notificationData
     */
    public void setNotificationData(List<NotificationDatum> notificationData) {
        this.notificationData = notificationData;
    }

    public class NotificationDatum {

        @SerializedName("notificationID")
        @Expose
        private String notificationID;
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("notifyMessage")
        @Expose
        private String notifyMessage;
        @SerializedName("createdTime")
        @Expose
        private String createdTime;
        @SerializedName("notifyType")
        @Expose
        private String notifyType;
        @SerializedName("gender")
        @Expose
        private String gender;

        /**
         * @return The notificationID
         */
        public String getNotificationID() {
            return notificationID;
        }

        /**
         * @param notificationID The notificationID
         */
        public void setNotificationID(String notificationID) {
            this.notificationID = notificationID;
        }

        /**
         * @return The userID
         */
        public String getUserID() {
            return userID;
        }

        /**
         * @param userID The userID
         */
        public void setUserID(String userID) {
            this.userID = userID;
        }

        /**
         * @return The notifyMessage
         */
        public String getNotifyMessage() {
            return notifyMessage;
        }

        /**
         * @param notifyMessage The notifyMessage
         */
        public void setNotifyMessage(String notifyMessage) {
            this.notifyMessage = notifyMessage;
        }

        /**
         * @return The createdTime
         */
        public String getCreatedTime() {
            return createdTime;
        }

        /**
         * @param createdTime The createdTime
         */
        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getNotifyType() {
            return notifyType;
        }

        public void setNotifyType(String notifyType) {
            this.notifyType = notifyType;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}