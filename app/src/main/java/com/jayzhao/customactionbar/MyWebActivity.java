package com.jayzhao.customactionbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.JetPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;

/**
 * Created by Jay on 16-4-8.
 * 通用的展示Web页面的Activity
 */
public class MyWebActivity extends MyBaseTitleActivity {
    private static final String TAG = "MyWebActivity";

    private TextView mTips = null;
    private WebView mWebView = null;
    private EditText mEditText = null;
    private TextView mTitleText = null;
    private Button mSearchButton = null;
    private ProgressBar mProgressBar = null;
    private PullToRefreshWebView mRefreshWebView = null;

    private String mUrl;
    private WebSettings mWebSettings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_layout);
        setStyle(STYLE.BACK_AND_EDIT);
        mUrl = getIntent().getStringExtra("URL");
        setTitle(TAG);

        mRefreshWebView = (PullToRefreshWebView) findViewById(R.id.refresh_holder);
        mWebView = mRefreshWebView.getRefreshableView();

        //设置下拉刷新的响应事件
        mRefreshWebView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {
            @Override
            public void onRefresh(PullToRefreshBase<WebView> refreshView) {
                mWebView.reload();
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.web_view_progress);
        mProgressBar.setVisibility(View.VISIBLE);
        mTips = (TextView) findViewById(R.id.tips);
        mTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });

        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);
        mWebView.loadUrl(mUrl);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //在WebView中打开url
                view.loadUrl(url);
                return true;

                //调用外部浏览器打开Url
                /*Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;*/
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i(TAG, "onPageStarted");
                mProgressBar.setVisibility(View.VISIBLE);
                mTips.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "onPageFinished");
                mProgressBar.setProgress(5);
                mProgressBar.setVisibility(View.GONE);
                mRefreshWebView.onRefreshComplete();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
                    mTips.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * 利用WebView.setWebChromeClient();方法可以响应加载进度变化
         */
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress + 5);
            }
        });

        mEditText = getEditText();

        //设置SearchButton按下监听事件
        setSearchListener(new ISearch() {
            @Override
            public void onSearchClicked() {
                Log.i(TAG, "Search Button Clicked!");
                /**
                 * 原来需要加上"http://"
                 * 真是太蠢了
                 */
                mWebView.loadUrl("http://" + getEditText().getText());
                //标题栏返回普通样式
                returnToNormal();
                //隐藏系统软键盘
                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if(mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
        }
        return super.onKeyDown(keyCode, event);
    }
}
