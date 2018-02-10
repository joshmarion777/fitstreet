package affle.com.fitstreet.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 7/27/16.
 */
public class PlaceModel implements Parcelable {

    private String mName;
    private String mDescription;
    private String mCity;
    private String mState;
    private String mCountry;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public String getState() {
        return mState;
    }

    public void setState(String mState) {
        this.mState = mState;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mDescription);
        dest.writeString(this.mCity);
        dest.writeString(this.mState);
        dest.writeString(this.mCountry);
    }

    public PlaceModel() {
    }

    protected PlaceModel(Parcel in) {
        this.mName = in.readString();
        this.mDescription = in.readString();
        this.mCity = in.readString();
        this.mState = in.readString();
        this.mCountry = in.readString();
    }

    public static final Parcelable.Creator<PlaceModel> CREATOR = new Parcelable.Creator<PlaceModel>() {
        @Override
        public PlaceModel createFromParcel(Parcel source) {
            return new PlaceModel(source);
        }

        @Override
        public PlaceModel[] newArray(int size) {
            return new PlaceModel[size];
        }
    };
}
