package affle.com.fitstreet.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.ui.activities.ContactUsActivity;
import affle.com.fitstreet.ui.activities.FAQActivity;
import affle.com.fitstreet.ui.activities.HomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by akash on 7/9/16.
 */
public class HelpAndSupportFragment extends BaseFragment {
    @BindView(R.id.iv_help_support)
    ImageView ivHelpSupport;
    @BindView(R.id.tv_contact_us)
    CustomTypefaceTextView tvContactUs;
    @BindView(R.id.tv_faq)
    CustomTypefaceTextView tvFaq;

    @Override
    protected void initViews() {
        ((HomeActivity) mActivity).setActionBarTitle(R.string.title_help_and_support);
    }

    @Override
    protected void initVariables() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_and_support, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.tv_contact_us, R.id.tv_faq})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_contact_us:
                intent = new Intent(mActivity, ContactUsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_faq:
                intent = new Intent(mActivity, FAQActivity.class);
                startActivity(intent);
                break;
        }
    }
}
