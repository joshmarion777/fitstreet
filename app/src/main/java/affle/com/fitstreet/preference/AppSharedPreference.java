package affle.com.fitstreet.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This class is the preferences class of the application.
 *
 * @author Affle Appstudioz
 */
public class AppSharedPreference {
    private SharedPreferences mSharedPreferences;
    private Editor mEditor;
    private static AppSharedPreference mAppSharedPreference;

    /**
     * This method is used to create single instance of app preference class.
     *
     * @param context
     * @return
     */
    public static AppSharedPreference getInstance(Context context) {
        if (mAppSharedPreference == null) {
            mAppSharedPreference = new AppSharedPreference(context);
        }
        return mAppSharedPreference;
    }

    /**
     * This method is the constructor of the class
     *
     * @param context the context to get the shared preference object
     */
    private AppSharedPreference(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(PreferenceKeys.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mSharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    /**
     * This method returns the shared preference editor
     *
     * @return the shared preference editor
     */
    public Editor getEditor() {
        return mEditor;
    }


    /**
     * This method is used to commit the shared preference editor
     */
    public void commitEditor() {
        mEditor.commit();
    }

    /**
     * This method is used to clear the shared preference editor and remove all the stored shared preference values
     */
    public void clearEditor() {
        mEditor.clear();
        mEditor.commit();
    }

    /**
     * This method is used to store a string value in the shared preference
     *
     * @param key   the key on which the string value will be stored
     * @param value the string value to store
     */
    public void setString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * This method returns the value corresponding to the key, or the default value if the key value pair does not exist
     *
     * @param key        the key corresponding which the string value is to be retrieved
     * @param defaultVal the default value to return if the key does not exist
     * @return the string value corresponding to the key if the key exists, else returns the default value
     */
    public String getString(String key, String defaultVal) {
        return mSharedPreferences.getString(key, defaultVal);
    }

    /**
     * This method is used to store a int value in the shared preference
     *
     * @param key   the key on which the int value will be stored
     * @param value the int value to store
     */
    public void setInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    /**
     * This method returns the value corresponding to the key, or the default value if the key value pair does not exist
     *
     * @param key        the key corresponding which the int value is to be retrieved
     * @param defaultVal the default value to return if the key does not exist
     * @return the int value corresponding to the key if the key exists, else returns the default value
     */
    public int getInt(String key, int defaultVal) {
        return mSharedPreferences.getInt(key, defaultVal);
    }

    /**
     * This method is used to store a long value in the shared preference
     *
     * @param key   the key on which the long value will be stored
     * @param value the long value to store
     */
    public void setLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    /**
     * This method returns the value corresponding to the key, or the default value if the key value pair does not exist
     *
     * @param key        the key corresponding which the long value is to be retrieved
     * @param defaultVal the default value to return if the key does not exist
     * @return the long value corresponding to the key if the key exists, else returns the default value
     */
    public Long getLong(String key, long defaultVal) {
        return mSharedPreferences.getLong(key, defaultVal);
    }

    /**
     * This method is used to store a boolean value in the shared preference
     *
     * @param key   the key on which the boolean value will be stored
     * @param value the boolean value to store
     */
    public void setBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    /**
     * This method returns the value corresponding to the key, or the default value if the key value pair does not exist
     *
     * @param key        the key corresponding which the boolean value is to be retrieved
     * @param defaultVal the default value to return if the key does not exist
     * @return the boolean value corresponding to the key if the key exists, else returns the default value
     */
    public boolean getBoolean(String key, boolean defaultVal) {
        return mSharedPreferences.getBoolean(key, defaultVal);
    }
}
