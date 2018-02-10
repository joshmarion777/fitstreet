package affle.com.fitstreet.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.ui.fragments.FavouriteFragment;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppUtilMethods;
import butterknife.BindView;

public class FavouritesActivity extends BaseActivity {

    @BindView(R.id.iv_toolbar_favourite)
    ImageView ivNavigationToolbarFavourite;
    @BindView(R.id.rl_toolbar_favourite)
    RelativeLayout rlNavigationToolbarFavourite;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.container_body)
    LinearLayout containerBody;
    private int mFromWhere;
    private boolean mWhereFlag = true;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        super.initData();
    }

    @Override
    protected void initViews() {
        if (mWhereFlag) {
            mWhereFlag = false;
            mFromWhere = getIntent().getIntExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_PRODUCTS);
        }
        tvTitle.setText(R.string.txt_favourite);
        rlNavigationToolbarFavourite.setVisibility(View.VISIBLE);
        //set listeners
        ivBack.setOnClickListener(this);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        FavouriteFragment favouriteFragment = new FavouriteFragment();
        mFragmentTransaction.replace(R.id.container_body, favouriteFragment);
        mFragmentTransaction.commit();
    }

    @Override
    protected void initVariables() {
    }

    public int getFromWhere() {
        return mFromWhere;
    }

    public void setFromWhere(int fromWhere) {
        mFromWhere = fromWhere;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AppUtilMethods.hideKeyBoard(this);
        this.setResult(RESULT_OK);
        super.onBackPressed();
    }
}
