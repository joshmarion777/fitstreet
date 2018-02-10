package affle.com.fitstreet.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;

import affle.com.fitstreet.preference.AppSharedPreference;
import butterknife.ButterKnife;

/**
 * This class is the Base Fragment class the all other Fragment classes extend.
 * This class has 2 methods to initialize data and views that all other Fragment classes must implement
 *
 * @author Affle Appstudioz
 */
public abstract class BaseFragment extends Fragment implements OnClickListener {
    protected Activity mActivity;
    protected AppSharedPreference mAppSharedPreference;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * This method is used to initialize the data of the class
     *
     * @return void
     */
    protected void initData() {
        mActivity = getActivity();
        mAppSharedPreference = AppSharedPreference.getInstance(mActivity.getApplicationContext());
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, getView());
        initViews();
        initVariables();
    }

    /**
     * This method is used to initialize the views of the class
     *
     * @return void
     */
    protected abstract void initViews();

    /**
     * This method is used to initialize the variables of the class
     *
     * @return void
     */
    protected abstract void initVariables();
}