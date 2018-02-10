package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 18/10/16.
 */
public class ResRunkeeperData extends ResBase{
        @SerializedName("activityData")
        @Expose
        private ActivityData activityData;
        /**
         * @return The activityData
         */
        public ActivityData getActivityData() {
            return activityData;
        }

        /**
         * @param activityData The activityData
         */
        public void setActivityData(ActivityData activityData) {
            this.activityData = activityData;
        }

        public class ActivityData {
            @SerializedName("distance")
            @Expose
            private String distance;
            @SerializedName("calories")
            @Expose
            private String calories;

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
             * @return The calories
             */
            public String getCalories() {
                return calories;
            }

            /**
             * @param calories The calories
             */
            public void setCalories(String calories) {
                this.calories = calories;
            }
        }
}

