package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 22/7/16.
 */
public class ResGetContestDetails extends ResBase {
    @SerializedName("contestData")
    @Expose
    private ContestData contestData;
    @SerializedName("leaderBoard")
    @Expose
    private List<LeaderBoard> leaderBoard = new ArrayList<LeaderBoard>();

    /**
     * @return The contestData
     */
    public ContestData getContestData() {
        return contestData;
    }

    /**
     * @param contestData The contestData
     */
    public void setContestData(ContestData contestData) {
        this.contestData = contestData;
    }

    /**
     * @return The leaderBoard
     */
    public List<LeaderBoard> getLeaderBoard() {
        return leaderBoard;
    }

    /**
     * @param leaderBoard The leaderBoard
     */
    public void setLeaderBoard(List<LeaderBoard> leaderBoard) {
        this.leaderBoard = leaderBoard;
    }


    public class ContestData {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("contestID")
        @Expose
        private String contestID;
        @SerializedName("endDate")
        @Expose
        private String endDate;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("particpants")
        @Expose
        private String particpants;
        @SerializedName("rank")
        @Expose
        private int rank;
        @SerializedName("distance")
        @Expose
        private float distance;
        @SerializedName("calories")
        @Expose
        private int calories;
        @SerializedName("totalDistance")
        @Expose
        private float totalDistance;
        @SerializedName("tagText")
        @Expose
        private String tagText;
        @SerializedName("addInfo")
        @Expose
        private String addInfo;


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
         * @return The endDate
         */
        public String getEndDate() {
            return endDate;
        }

        /**
         * @param endDate The endDate
         */
        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        /**
         * @return The description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description The description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return The particpants
         */
        public String getParticpants() {
            return particpants;
        }

        /**
         * @param particpants The particpants
         */
        public void setParticpants(String particpants) {
            this.particpants = particpants;
        }

        /**
         * @return The rank
         */
        public int getRank() {
            return rank;
        }

        /**
         * @param rank The rank
         */
        public void setRank(int rank) {
            this.rank = rank;
        }


        /**
         * @return The calories
         */
        public int getCalories() {
            return calories;
        }

        /**
         * @param calories The calories
         */
        public void setCalories(int calories) {
            this.calories = calories;
        }




        public String getTagText() {
            return tagText;
        }

        public void setTagText(String tagText) {
            this.tagText = tagText;
        }

        public String getAddInfo() {
            return addInfo;
        }

        public void setAddInfo(String addInfo) {
            this.addInfo = addInfo;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public float getTotalDistance() {
            return totalDistance;
        }

        public void setTotalDistance(float totalDistance) {
            this.totalDistance = totalDistance;
        }
    }

    public class LeaderBoard {

        @SerializedName("rank")
        @Expose
        private int rank;
        @SerializedName("distance")
        @Expose
        private float distance;
        @SerializedName("name")
        @Expose
        private String name;

        /**
         * @return The rank
         */
        public int getRank() {
            return rank;
        }

        /**
         * @param rank The rank
         */
        public void setRank(int rank) {
            this.rank = rank;
        }

        /**
         * @return The distance
         */
        public float getDistance() {
            return distance;
        }

        /**
         * @param distance The distance
         */
        public void setDistance(float distance) {
            this.distance = distance;
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

    }
}
