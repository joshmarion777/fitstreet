package affle.com.fitstreet.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SocialModel implements Parcelable {
    private String mId;
    private String nName;
    private int mGender;
    private String mDob;
    private String mEmail;
    private String mPicture;

    public SocialModel(Parcel source) {
        readParcel(source);
    }

    public SocialModel() {

    }

    /**
     * @return the id
     */
    public String getId() {
        return mId;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.mId = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return nName;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.nName = name;
    }

    /**
     * @return the mGender
     */
    public int getGender() {
        return mGender;
    }

    /**
     * @param gender the mGender to set
     */
    public void setGender(int gender) {
        this.mGender = gender;
    }

    /**
     * @return the dob
     */
    public String getDob() {
        return mDob;
    }

    /**
     * @param dob the lastName to set
     */
    public void setDob(String dob) {
        this.mDob = dob;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.mEmail = email;
    }

    /**
     * @return the picture
     */
    public String getPicture() {
        return mPicture;
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(String picture) {
        this.mPicture = picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SocialModel> CREATOR = new Creator<SocialModel>() {
        @Override
        public SocialModel createFromParcel(Parcel source) {
            return new SocialModel(source);
        }

        @Override
        public SocialModel[] newArray(int size) {
            return new SocialModel[size];
        }

    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(nName);
        dest.writeInt(mGender);
        dest.writeString(mDob);
        dest.writeString(mEmail);
        dest.writeString(mPicture);
    }

    private void readParcel(Parcel in) {
        mId = in.readString();
        nName = in.readString();
        mGender = in.readInt();
        mDob = in.readString();
        mEmail = in.readString();
        mPicture = in.readString();
    }
}
