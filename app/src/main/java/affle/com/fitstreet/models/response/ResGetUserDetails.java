package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/14/16.
 */
public class ResGetUserDetails extends ResBase {
    @SerializedName("userData")
    @Expose
    private ResUserData userData;

    /**
     * @return The userData
     */
    public ResUserData getUserData() {
        return userData;
    }

    /**
     * @param userData The userData
     */
    public void setUserData(ResUserData userData) {
        this.userData = userData;
    }

    public class ResUserData {
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("emailID")
        @Expose
        private String emailID;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("registrationType")
        @Expose
        private String registrationType;
        @SerializedName("locationName")
        @Expose
        private String locationName;
        @SerializedName("height")
        @Expose
        private String height;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("isEmailVerified")
        @Expose
        private String isEmailVerified;
        @SerializedName("homeScreen")
        @Expose
        private String homeScreen;
        @SerializedName("notification")
        @Expose
        private String notification;
        @SerializedName("unit")
        @Expose
        private String unit;
        @SerializedName("totalPoints")
        @Expose
        private String totalPoints;

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
         * @return The status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return The image
         */
        public String getImage() {
            return image;
        }

        /**
         * @param image The image
         */
        public void setImage(String image) {
            this.image = image;
        }

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The dob
         */
        public String getDob() {
            return dob;
        }

        /**
         * @param dob The dob
         */
        public void setDob(String dob) {
            this.dob = dob;
        }

        /**
         * @return The mGender
         */
        public String getGender() {
            return gender;
        }

        /**
         * @param gender The mGender
         */
        public void setGender(String gender) {
            this.gender = gender;
        }

        /**
         * @return The emailID
         */
        public String getEmailID() {
            return emailID;
        }

        /**
         * @param emailID The emailID
         */
        public void setEmailID(String emailID) {
            this.emailID = emailID;
        }

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
         * @return The registrationType
         */
        public String getRegistrationType() {
            return registrationType;
        }

        /**
         * @param registrationType The registrationType
         */
        public void setRegistrationType(String registrationType) {
            this.registrationType = registrationType;
        }

        /**
         * @return The locationName
         */
        public String getLocationName() {
            return locationName;
        }

        /**
         * @param locationName The locationName
         */
        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        /**
         * @return The height
         */
        public String getHeight() {
            return height;
        }

        /**
         * @param height The height
         */
        public void setHeight(String height) {
            this.height = height;
        }

        /**
         * @return The weight
         */
        public String getWeight() {
            return weight;
        }

        /**
         * @param weight The weight
         */
        public void setWeight(String weight) {
            this.weight = weight;
        }

        /**
         * @return The isEmailVerified
         */
        public String getIsEmailVerified() {
            return isEmailVerified;
        }

        /**
         * @param isEmailVerified The isEmailVerified
         */
        public void setIsEmailVerified(String isEmailVerified) {
            this.isEmailVerified = isEmailVerified;
        }

        /**
         * @return The homeScreen
         */
        public String getHomeScreen() {
            return homeScreen;
        }

        /**
         * @param homeScreen The homeScreen
         */
        public void setHomeScreen(String homeScreen) {
            this.homeScreen = homeScreen;
        }

        /**
         * @return The notification
         */
        public String getNotification() {
            return notification;
        }

        /**
         * @param notification The notification
         */
        public void setNotification(String notification) {
            this.notification = notification;
        }

        /**
         * @return The unit
         */
        public String getUnit() {
            return unit;
        }

        /**
         * @param unit The unit
         */
        public void setUnit(String unit) {
            this.unit = unit;
        }

        /**
         * @return The totalPoints
         */
        public String getTotalPoints() {
            return totalPoints;
        }

        /**
         * @param totalPoints The totalPoints
         */
        public void setTotalPoints(String totalPoints) {
            this.totalPoints = totalPoints;
        }
    }
}
