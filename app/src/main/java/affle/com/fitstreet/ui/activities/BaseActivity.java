package affle.com.fitstreet.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import affle.com.fitstreet.preference.AppSharedPreference;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected AppSharedPreference mAppSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * This method is used to initialize the data of the class
     *
     * @return void
     */
    protected void initData() {
        mAppSharedPreference = AppSharedPreference.getInstance(getApplicationContext());
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
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
