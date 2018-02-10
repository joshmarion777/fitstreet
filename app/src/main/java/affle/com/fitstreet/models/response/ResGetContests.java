package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.interfaces.IViewType;

/**
 * Created by akash on 20/7/16.
 */
public class ResGetContests extends ResBase {
    @SerializedName("myContestData")
    @Expose
    private List<MyContestData> myContestData = new ArrayList<MyContestData>();
    @SerializedName("upContestData")
    @Expose
    private List<UpContestData> upContestData = new ArrayList<UpContestData>();


    /**
     * @return The myContestData
     */
    public List<MyContestData> getMyContestData() {
        return myContestData;
    }

    /**
     * @param myContestData The myContestData
     */
    public void setMyContestData(List<MyContestData> myContestData) {
        this.myContestData = myContestData;
    }

    /**
     * @return The upContestData
     */
    public List<UpContestData> getUpContestData() {
        return upContestData;
    }

    /**
     * @param upContestData The upContestData
     */
    public void setUpContestData(List<UpContestData> upContestData) {
        this.upContestData = upContestData;
    }


    public class MyContestData implements IViewType {

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
        private Integer rank;

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
        public Integer getRank() {
            return rank;
        }

        /**
         * @param rank The rank
         */
        public void setRank(Integer rank) {
            this.rank = rank;
        }

        @Override
        public int getViewType() {
            return IViewType.VIEW_TYPE_MY_CONTEST;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }
    }

    public class UpContestData implements IViewType {

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
        private Integer rank;
        @SerializedName("tagText")
        @Expose
        private String tagText;

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
         * @return The distance
         */
        public float getDistance() {
            return distance;
        }

        /**
         * @param distance The distance
         */
        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        /**
         * @return The rank
         */
        public Integer getRank() {
            return rank;
        }

        /**
         * @param rank The rank
         */
        public void setRank(Integer rank) {
            this.rank = rank;
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

        @Override
        public int getViewType() {
            return IViewType.VIEW_TYPE_UPCOMING_CONTEST;
        }
    }

}