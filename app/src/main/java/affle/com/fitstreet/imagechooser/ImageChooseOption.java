package affle.com.fitstreet.imagechooser;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * This class stores the image choose details camera and gallery.
 *
 * @author Affle AppStudioz
 */
public class ImageChooseOption {
    private CharSequence mTitle;
    private Drawable mIcon;
    private Intent mAppIntent;
    private int mIntentType;

    public ImageChooseOption() {
        super();
    }

    public ImageChooseOption(CharSequence title, Drawable icon, Intent appIntent, int intentType) {
        super();
        this.mTitle = title;
        this.mIcon = icon;
        this.mAppIntent = appIntent;
        this.mIntentType = intentType;
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
    }

    public Intent getAppIntent() {
        return mAppIntent;
    }

    public void setAppIntent(Intent appIntent) {
        this.mAppIntent = appIntent;
    }

    public int getIntentType() {
        return mIntentType;
    }

    public void setIntentType(int intentType) {
        this.mIntentType = intentType;
    }
}