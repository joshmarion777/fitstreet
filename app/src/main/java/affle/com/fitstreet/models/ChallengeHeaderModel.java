package affle.com.fitstreet.models;

import affle.com.fitstreet.interfaces.IViewType;

/**
 * Created by root on 7/22/16.
 */
public class ChallengeHeaderModel implements IViewType {
    private String mChallengeTitle;

    public ChallengeHeaderModel(String challengeTitle) {
        this.mChallengeTitle = challengeTitle;
    }

    public String getChallengeTitle() {
        return this.mChallengeTitle;
    }

    public void setChallengeTitle(String challengeTitle) {
        this.mChallengeTitle = challengeTitle;
    }

    @Override
    public int getViewType() {
        return IViewType.VIEW_TYPE_TITLE_CONTEST;
    }
}
