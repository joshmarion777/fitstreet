package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 22/7/16.
 */
public class ResLeaveContest extends ResBase {
    @SerializedName("upContestData")
    @Expose
    private List<UpContestDatum> upContestData = new ArrayList<UpContestDatum>();
    @SerializedName("myContestData")
    @Expose
    private List<MyContestDatum> myContestData = new ArrayList<MyContestDatum>();
    @SerializedName("contestData")
    @Expose
    private CurrentContestData currentContestData;
    @SerializedName("leaderBoard")
    @Expose
    private List<LeaderBoard> leaderBoard = new ArrayList<LeaderBoard>();

    /**
     * @return The upContestData
     */
    public List<UpContestDatum> getUpContestData() {
        return upContestData;
    }

    /**
     * @param upContestData The upContestData
     */
    public void setUpContestData(List<UpContestDatum> upContestData) {
        this.upContestData = upContestData;
    }

    /**
     * @return The myContestData
     */
    public List<MyContestDatum> getMyContestData() {
        return myContestData;
    }

    /**
     * @param myContestData The myContestData
     */
    public void setMyContestData(List<MyContestDatum> myContestData) {
        this.myContestData = myContestData;
    }


    /**
     * @param currentContestData The currentContestData
     */
    public void setCurrentContestData(CurrentContestData currentContestData) {
        this.currentContestData = currentContestData;
    }

    public CurrentContestData getCurrentContestData() {
        return currentContestData;
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

    public class LeaderBoard {

        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("rank")
        @Expose
        private int rank;

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

    }

    public class MyContestDatum {

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
        @SerializedName("particpants")
        @Expose
        private String particpants;
        @SerializedName("distance")
        @Expose
        private float distance;
        @SerializedName("rank")
        @Expose
        private int rank;

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

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }
    }

    public class UpContestDatum {

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
        @SerializedName("tagText")
        @Expose
        private String tagText;
        @SerializedName("particpants")
        @Expose
        private String particpants;

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
         * @return The tagText
         */
        public String getTagText() {
            return tagText;
        }

        /**
         * @param tagText The tagText
         */
        public void setTagText(String tagText) {
            this.tagText = tagText;
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

    }

    public class CurrentContestData {
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
        @SerializedName("tagText")
        @Expose
        private String tagText;
        @SerializedName("addInfo")
        @Expose
        private String addInfo;
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
         * @return The tagText
         */
        public String getTagText() {
            return tagText;
        }

        /**
         * @param tagText The tagText
         */
        public void setTagText(String tagText) {
            this.tagText = tagText;
        }

        /**
         * @return The addInfo
         */
        public String getAddInfo() {
            return addInfo;
        }

        /**
         * @param addInfo The addInfo
         */
        public void setAddInfo(String addInfo) {
            this.addInfo = addInfo;
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

        /**
         * @return The totalDistance
         */
        public float getTotalDistance() {
            return totalDistance;
        }

        /**
         * @param totalDistance The totalDistance
         */
        public void setTotalDistance(int totalDistance) {
            this.totalDistance = totalDistance;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }
    }
}