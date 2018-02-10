package affle.com.fitstreet.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.utils.AppConstants;
import butterknife.BindView;

/**
 * This class is a fragment that shows the tutorial.
 */
public class TutorialFragment extends BaseFragment {
    @BindView(R.id.iv_tutorial)
    ImageView ivTutorial;
    @BindView(R.id.tv_text_tutorial)
    TextView tvTextTutorial;
    @BindView(R.id.tv_title_tutorial)
    TextView tvTitleTutorial;

    public static final TutorialFragment newInstance(int imageResourceId, int textResourceId, int titleResourceId) {
        TutorialFragment tutorialFragment = new TutorialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.TAG_IMG_RESOURCE_ID, imageResourceId);
        bundle.putInt(AppConstants.TAG_TEXT_RESOURCE_ID, textResourceId);
        bundle.putInt(AppConstants.TAG_TITLE_RESOURCE_ID, titleResourceId);
        tutorialFragment.setArguments(bundle);
        return tutorialFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorial, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initViews() {
        Bundle bundle = getArguments();
        int imageResourceId = bundle.getInt(AppConstants.TAG_IMG_RESOURCE_ID, 0);
        int textResourceId = bundle.getInt(AppConstants.TAG_TEXT_RESOURCE_ID, 0);
        int titleResourceId = bundle.getInt(AppConstants.TAG_TITLE_RESOURCE_ID, 0);

        ivTutorial.setImageResource(imageResourceId);
        tvTitleTutorial.setText(titleResourceId);
        tvTextTutorial.setText(textResourceId);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onClick(View view) {
    }
}