package com.example.tome.module_shop_mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.tome.component_base.base.BaseMVPActivity;
import com.example.tome.component_base.util.L;
import com.example.tome.component_data.d_arouter.IntentKV;
import com.example.tome.module_shop_mall.R;
import com.example.tome.module_shop_mall.R2;
import com.example.tome.module_shop_mall.contract.ArticleDetailContract;
import com.example.tome.module_shop_mall.presenter.ArticleDetailPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.tome.component_data.d_arouter.IntentKV.K_ARTICLE_ID;

/**
 * 文章详情页
 */
public class ArticleDetailActivity extends BaseMVPActivity<ArticleDetailPresenter> implements ArticleDetailContract.View {

    @BindView(R2.id.common_toolbar_title)
    TextView mTitle;
    @BindView(R2.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.article_detail_web_view)
    WebView mWebView;

    private Bundle bundle;
    private int articleId;
    private String articleLink;
    private String title;
    private boolean isCollect;
    private boolean isCommonSite;
    private boolean isCollectPage;

    @Override
    protected ArticleDetailPresenter getPresenter() {
        return new ArticleDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mall_activity_home_detail;
    }

    @Override
    protected void initTitle() {
        Intent intent = getIntent();
        //断言判断 assert
        assert intent != null ;

        //取代原本的ActionBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        String title = intent.getStringExtra(IntentKV.K_ARTICLE_TITLE);
        mTitle.setText(title);
        articleLink = intent.getStringExtra(IntentKV.K_ARTICLE_LINK);
        articleId = intent.getIntExtra(IntentKV.K_ARTICLE_ID , 0);
        isCommonSite = intent.getBooleanExtra(IntentKV.K_IS_COMMON_SITE, false);
        isCollect = intent.getBooleanExtra(IntentKV.K_IS_COLLECT , false);
        isCollectPage = intent.getBooleanExtra(IntentKV.K_IS_COLLECT_PAGE, false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initView() {
        mWebView.loadUrl(articleLink);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(articleLink);
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
    }

    @Override
    public void showArticleData() {
        mPresenter.getArticleData();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
