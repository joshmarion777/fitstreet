package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 13/10/16.
 */
public class ResPrizeMoneyDetails extends ResBase {
    @SerializedName("prizeData")
    @Expose
    private List<PrizeDatum> prizeData = new ArrayList<PrizeDatum>();

    /**
     * @return The prizeData
     */
    public List<PrizeDatum> getPrizeData() {
        return prizeData;
    }

    /**
     * @param prizeData The prizeData
     */
    public void setPrizeData(List<PrizeDatum> prizeData) {
        this.prizeData = prizeData;
    }

    public class PrizeDatum {

        @SerializedName("contestID")
        @Expose
        private String contestID;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("createdTime")
        @Expose
        private String createdTime;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;

        /**
         * @return The contestID
         */
        public String getContestID() {
            return contestID;
        }

        /**
         * @param contestID The contestID
         */
        public void setContestID(String contestID) {
            this.contestID = contestID;
        }

        /**
         * @return The distance
         */
        public String getDistance() {
            return distance;
        }

        /**
         * @param distance The distance
         */
        public void setDistance(String distance) {
            this.distance = distance;
        }

        /**
         * @return The rank
         */
        public String getRank() {
            return rank;
        }

        /**
         * @param rank The rank
         */
        public void setRank(String rank) {
            this.rank = rank;
        }

        /**
         * @return The amount
         */
        public String getAmount() {
            return amount;
        }

        /**
         * @param amount The amount
         */
        public void setAmount(String amount) {
            this.amount = amount;
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

    }
}
