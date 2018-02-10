package affle.com.fitstreet.imagechooser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.ui.activities.EditProfileActivity;
import affle.com.fitstreet.ui.fragments.HomeFragment;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppUtilMethods;

/**
 * This class is used to create an image chooser.
 *
 * @author Affle AppStudioz
 */
public class ImageChooser {
    private Fragment mFragment;
    private Activity mActivity;

    public ImageChooser(Activity activity, Fragment fragment)

    {

        this.mActivity = activity;
        this.mFragment = fragment;
    }

    /**
     * Detect the available intent and open a new dialog.
     *
     * @return void
     */
    public void openMediaSelector() {
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");

        // look for available intents
        PackageManager packageManager = mActivity.getPackageManager();

        List<ResolveInfo> cameraResolveInfoList = packageManager.queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
        List<ImageChooseOption> imageChooseOptionList = new ArrayList<ImageChooseOption>();
        if (cameraResolveInfoList.size() > 0) {
            ResolveInfo res = cameraResolveInfoList.get(0);
            final Intent intent = new Intent(cameraIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            ImageChooseOption imageChooseOption = new ImageChooseOption();
            imageChooseOption.setAppIntent(intent);
            imageChooseOption.setTitle(res.loadLabel(mActivity.getPackageManager()).toString());
            imageChooseOption.setIcon(res.loadIcon(mActivity.getPackageManager()));
            imageChooseOption.setIntentType(AppConstants.RC_CAMERA_INTENT);
            imageChooseOptionList.add(imageChooseOption);
        }

        List<ResolveInfo> galleryResolveInfoList = packageManager.queryIntentActivities(galleryIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (galleryResolveInfoList.size() > 0) {
            ResolveInfo res = galleryResolveInfoList.get(0);
            final Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            ImageChooseOption imageChooseOption = new ImageChooseOption();
            imageChooseOption.setAppIntent(intent);
            imageChooseOption.setTitle(res.loadLabel(mActivity.getPackageManager()).toString());
            imageChooseOption.setIcon(res.loadIcon(mActivity.getPackageManager()));
            imageChooseOption.setIntentType(AppConstants.RC_GALLERY_INTENT);
            imageChooseOptionList.add(imageChooseOption);
        }

        if (mFragment instanceof HomeFragment) {
            ImageChooseOption imageChooseOption = new ImageChooseOption();
            imageChooseOption.setAppIntent(null);
            imageChooseOption.setTitle(mActivity.getResources().getString(R.string.txt_reset));
            imageChooseOption.setIcon(ContextCompat.getDrawable(mActivity, R.drawable.reset_icon_popup));
            imageChooseOption.setIntentType(AppConstants.FROM_RESET_IMAGE);
            imageChooseOptionList.add(imageChooseOption);
        }

        // show available intents
        openDialog(imageChooseOptionList);
    }

    /**
     * Open a new dialog with the detected items.
     *
     * @param imageChooseOptionList
     * @return void
     */
    private void openDialog(final List<ImageChooseOption> imageChooseOptionList) {
        ImageChooseOptionAdapter imageChooseOptionAdapter = new ImageChooseOptionAdapter(mActivity, imageChooseOptionList);
        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
        dialog.setTitle(mActivity.getResources().getString(R.string.txt_select_image_from));
        dialog.setAdapter(imageChooseOptionAdapter, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                ImageChooseOption imageChooseOption = imageChooseOptionList.get(position);
                if (imageChooseOption.getIntentType() == AppConstants.RC_GALLERY_INTENT) {
                    if (mActivity instanceof EditProfileActivity) {
                        ((EditProfileActivity) mActivity).getImageFromGallery(imageChooseOption.getAppIntent());
                    } else if (mFragment instanceof HomeFragment) {
                        ((HomeFragment) mFragment).getImageFromGallery(imageChooseOption.getAppIntent());
                    }
                } else if (imageChooseOption.getIntentType() == AppConstants.RC_CAMERA_INTENT) {
                    if (mActivity instanceof EditProfileActivity) {
                        ((EditProfileActivity) mActivity).getImageFromCamera(imageChooseOption.getAppIntent());
                    } else if (mFragment instanceof HomeFragment) {
                        ((HomeFragment) mFragment).getImageFromCamera(imageChooseOption.getAppIntent());
                    }
                } else if (imageChooseOption.getIntentType() == AppConstants.FROM_RESET_IMAGE) {
                    if (mFragment instanceof HomeFragment) {
                        ((HomeFragment) mFragment).resetImage();
                    }
                }
            }
        });

        dialog.setPositiveButton(mActivity.getResources().getString(R.string.txt_cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        AppUtilMethods.overrideDialogFonts(mActivity, alertDialog);
    }
}