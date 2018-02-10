package affle.com.fitstreet.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.wv_url)
    WebView wvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        super.initData();
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        tvTitle.setText(intent.getIntExtra(AppConstants.TAG_TITLE_RESOURCE_ID, 0));
        wvUrl.getSettings().setJavaScriptEnabled(true);
        String url = intent.getStringExtra(AppConstants.TAG_URL);
        if (url.endsWith(".pdf")) {
//          wvUrl.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
            wvUrl.loadUrl("https://docs.google.com/viewer?url=" + url);
        } else
            wvUrl.loadUrl(url);

        //set listeners
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
