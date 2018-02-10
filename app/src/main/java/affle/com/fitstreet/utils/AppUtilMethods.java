package affle.com.fitstreet.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.RotateDrawable;
import android.net.Uri;
import android.os.Handler;
import android.util.AndroidException;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import affle.com.fitstreet.R;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.ChallengeDetailsActivity;
import affle.com.fitstreet.ui.activities.HomeActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by root on 3/4/16.
 */
public class AppUtilMethods {

    /**
     * This method is used to show the keyboard
     *
     * @return void
     */
    public static void showKeyBoard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
            inputMethodManager.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to hide the keyboard
     *
     * @param activity
     * @return boolean
     */
    public static boolean hideKeyBoard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
            return inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method is used to hide the keyboard
     *
     * @param activity
     * @param editText
     * @return boolean
     */
    public static boolean hideKeyBoard(Activity activity, EditText editText) {
        try {
            InputMethodManager inputMethodManager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
            return inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method is used to hide the keyboard
     *
     * @param activity
     * @param view
     * @return boolean
     */
    public static boolean hideKeyBoard(Activity activity, View view) {
        try {
            InputMethodManager inputMethodManager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
            return inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void generateFBKeyHash(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo("com.fitstreet", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Logger.e("key hash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String getGender(Context context, int gender) {
        switch (gender) {
            case ServiceConstants.GENDER_MALE:
                return context.getString(R.string.txt_male);
            case ServiceConstants.GENDER_FEMALE:
                return context.getString(R.string.txt_female);
            case ServiceConstants.GENDER_OTHERS:
                return context.getString(R.string.txt_others);
        }
        return "";
    }

    public static String getProfileDate(String serverDate) {
        try {
            if (serverDate.equals("0000-00-00"))
                return "";
            SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return format.format(serverFormat.parse(serverDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getServerDate(String date) {
        try {
            SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return serverFormat.format(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDistanceUnit(Context context, int unit) {
        List<String> distanceUnitList = Arrays.asList(context.getResources().getStringArray(R.array.arr_distance));
        return distanceUnitList.size() > unit ? distanceUnitList.get(unit) : "miles";
    }

    public static void overrideFonts(final Context context, final View v) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_roboto_regular));
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(typeface);
            }
        } catch (Exception e) {
        }
    }

    public static void overrideDialogFonts(final Context context, final AlertDialog dialog) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_roboto_regular));
        try {
            Button btnPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button btnNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            TextView tvMessage = (TextView) dialog.findViewById(android.R.id.message);
            TextView tvTitle = null;
            int titleId = context.getResources().getIdentifier("alertTitle", "id", "android");
            if (titleId > 0) {
                tvTitle = (TextView) dialog.findViewById(titleId);
            }
            if (btnPositive != null)
                btnPositive.setTypeface(typeface);
            if (btnNegative != null)
                btnNegative.setTypeface(typeface);
            if (tvMessage != null)
                tvMessage.setTypeface(typeface);
            if (tvTitle != null)
                tvTitle.setTypeface(typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to show the default share dialog for sharing plain text
     *
     * @param activity     context to create the share dialog
     * @param shareSubject subject of sharing
     * @param shareBody    content to be shared
     */
    public static void openTextShareDialog(Activity activity, String shareSubject, String shareBody) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        activity.startActivityForResult(Intent.createChooser(sharingIntent, activity.getResources().getString(R.string.txt_share_using)), AppConstants.RC_SHARE_DIALOG);
        ServiceUtils.shareAndInvite(activity, new ProgressDialog(activity), AppSharedPreference.getInstance(activity) , ServiceConstants.TYPE_SHARE);
    }

    public static void openImageShareDialog(Activity activity, String shareSubject, String shareBody, String imagePath) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("image/*");
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
        activity.startActivity(Intent.createChooser(sharingIntent, activity.getResources().getString(R.string.txt_share_using)));
//        ServiceUtils.shareAndInvite(activity, null, AppSharedPreference.getInstance(activity), ServiceConstants.TYPE_INVITE);
    }


    public static long calculateDays(String endDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String inputString2 = df.format(c.getTime());
        long diff = 0;
        try {
            Date date1 = myFormat.parse(endDate);
            Date date2 = myFormat.parse(inputString2);
            diff = date1.getTime() - date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static void setImageShowingLoader(Activity activity, final ImageView imageView, String url) {

        imageView.setBackgroundResource(R.drawable.loading_anim);
        ((AnimationDrawable) imageView.getBackground()).start();
        Glide.with(activity)
                .load(url)
                .placeholder(R.drawable.no_image_available)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imageView.clearAnimation();
                        return false;
                    }
                })
                .bitmapTransform(new CropCircleTransformation(activity))
                .into(imageView);


    }
}
