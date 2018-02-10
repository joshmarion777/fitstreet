package affle.com.fitstreet.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.TutorialPageAdapter;
import affle.com.fitstreet.ui.fragments.TutorialFragment;
import affle.com.fitstreet.utils.AppConstants;
import butterknife.BindView;

public class TutorialActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.vp_tutorial)
    ViewPager vpTutorial;
    @BindView(R.id.ll_pager_indicator)
    LinearLayout llPagerIndicator;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private List<Fragment> mTutorialFragmentList;
    private List<ImageView> mImageViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        super.initData();
    }

    @Override
    protected void initViews() {
        //set listeners
        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        vpTutorial.addOnPageChangeListener(this);
    }

    @Override
    protected void initVariables() {
        vpTutorial.setAdapter(new TutorialPageAdapter(getSupportFragmentManager(), getTutorialFragments()));
        mImageViewList = new ArrayList<ImageView>();
        for (int i = 0; i < mTutorialFragmentList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setTag(R.id.tag_position, i);
            imageView.setImageResource(R.drawable.x_sd_page_indicator);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = (int) getResources().getDimension(R.dimen.d_pad_pager_indicator);
            params.rightMargin = (int) getResources().getDimension(R.dimen.d_pad_pager_indicator);
            imageView.setLayoutParams(params);
            imageView.setOnClickListener(this);
            llPagerIndicator.addView(imageView);
            mImageViewList.add(imageView);
        }
        mImageViewList.get(0).setSelected(true);
    }

    /**
     * This method returns a list of tutorial fragments
     *
     * @return List<Fragment>
     */
    private List<Fragment> getTutorialFragments() {
        mTutorialFragmentList = new ArrayList<>();
        mTutorialFragmentList.add(TutorialFragment.newInstance(R.drawable.background_one, R.string.txt_tutorial_first, R.string.txt_title_tutorial_first));
        mTutorialFragmentList.add(TutorialFragment.newInstance(R.drawable.background_two, R.string.txt_tutorial_second, R.string.txt_title_tutorial_second));
        mTutorialFragmentList.add(TutorialFragment.newInstance(R.drawable.background_three, R.string.txt_tutorial_third, R.string.txt_title_tutorial_third));
        mTutorialFragmentList.add(TutorialFragment.newInstance(R.drawable.background_four, R.string.txt_tutorial_fourth, R.string.txt_title_tutorial_fourth));
        return mTutorialFragmentList;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_sign_up:
                intent = new Intent(TutorialActivity.this, SignUpActivity.class);
                startActivityForResult(intent, AppConstants.RC_LOGIN_SIGN_UP);
                break;
            case R.id.btn_login:
                intent = new Intent(TutorialActivity.this, LoginOptionsActivity.class);
                startActivityForResult(intent, AppConstants.RC_LOGIN_SIGN_UP);
                break;
            default:
                if (view instanceof ImageView) {
                    int position = (int) view.getTag(R.id.tag_position);
                    vpTutorial.setCurrentItem(position);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for (ImageView imageView : mImageViewList) {
            imageView.setSelected(false);
        }
        mImageViewList.get(position).setSelected(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_LOGIN_SIGN_UP && resultCode == RESULT_OK) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
