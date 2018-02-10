package affle.com.fitstreet.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.ui.fragments.MyCartFragmentNew;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;

public class MyCartActivity extends BaseActivity {
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
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        super.initData();

    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_my_cart);
        ivBack.setOnClickListener(this);
        mFromWhere = (getIntent().getIntExtra(AppConstants.TAG_FROM_WHERE, 2000));
        if (mFromWhere == 1001 || mFromWhere == 1002) {
            mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            MyCartFragmentNew myCartFragmentNew = new MyCartFragmentNew();
            mFragmentTransaction.replace(R.id.container_body, myCartFragmentNew);
            mFragmentTransaction.commit();
        }
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
