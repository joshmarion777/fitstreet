package affle.com.fitstreet.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * This class is a utility class that has method for taking images from camera and gallery.
 *
 * @author Affle AppStudioz
 */
public class CameraAndGalleryUtils {

    /**
     * This method is used to open the camera
     *
     * @param intent
     * @param activity
     * @return void
     */
    public static void getImageFromCamera(Intent intent, Activity activity) {
        File file = new File(AppConstants.FITSTREET_DIRECTORY_PATH);
        if (!file.isDirectory())
            file.mkdir();
        Uri mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                ".fitstreet" + AppConstants.FITSTREET_IMAGE_PATH));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

        try {
            intent.putExtra("return-data", true);
            activity.startActivityForResult(intent, AppConstants.RC_CAMERA_INTENT);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to open the gallery
     *
     * @param intent
     * @param activity
     * @return void
     */
    public static void getImageFromGallery(Intent intent, Activity activity) {
        activity.startActivityForResult(intent, AppConstants.RC_GALLERY_INTENT);
    }


    /**
     * This method is used to return the bitmap chosen by user
     *
     * @param path
     * @param mat
     * @param width
     * @param height
     * @return Bitmap
     */
    public static Bitmap getImageBitmap(String path, Matrix mat, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = Math.min(options.outWidth / width, options.outHeight / height);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        bitmap = getBitmap(bitmap, width, height, mat);
        return bitmap;
    }

    /**
     * This method is used to get bitmap using new height and width, and the
     * matrix
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @param mat
     * @return Bitmap
     */
    private static Bitmap getBitmap(Bitmap bm, int newWidth, int newHeight, Matrix mat) {
        if (newHeight >= bm.getHeight()) {
            newHeight = bm.getHeight();
        }
        if (newWidth >= bm.getWidth()) {
            newWidth = bm.getWidth();
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
        resizedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);
        return resizedBitmap;
    }

    /**
     * This method is used to get the rotaion matrix
     *
     * @param orientation
     * @return Matrix
     */
    public static Matrix getRotationMatrix(int orientation) {
        Matrix mat = new Matrix();
        if (orientation == 1) {
            mat.postRotate(0);
        } else if (orientation == 6) {
            mat.postRotate(90);
        } else if (orientation == 8) {
            mat.postRotate(270);
        } else if (orientation == 3) {
            mat.postRotate(180);
        }
        return mat;
    }

    /**
     * This method is used to get the orientation of captured image
     *
     * @param path
     * @return int
     */
    public static int getOrientation(String path) {
        int orientation = 0;
        try {
            ExifInterface exif = new ExifInterface(path);
            String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            orientation = Integer.parseInt(exifOrientation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orientation;
    }

    /**
     * This method is used to save image to file
     *
     * @param bitmap
     * @param path
     * @return void
     */
    public static void saveImageToFile(Bitmap bitmap, String path) {
        File file = new File(AppConstants.FITSTREET_DIRECTORY_PATH);
        if (!file.isDirectory())
            file.mkdir();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (path.contains(".png")) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        }
        try {
            File f = new File(path);
            if (f.isFile()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}