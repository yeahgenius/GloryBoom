package com.yeahgenius.hy.gloryboom.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.yeahgenius.hy.gloryboom.R;
import com.yeahgenius.hy.gloryboom.bean.FavPageBean;
import com.yeahgenius.hy.gloryboom.constant.Constant;
import com.yeahgenius.hy.gloryboom.util.DbUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewctivity extends AppCompatActivity
{
    @BindView(R.id.toolbar_webview_activity) Toolbar toolbarWebviewActivity;
    @BindView(R.id.webview_webview_activity) WebView webviewWebviewActivity;
    @BindView(R.id.fab_webview_activity) FloatingActionButton fabWebviewActivity;
    private WebSettings settings;
    private String title;
    private String url;
    private String cacheDirPath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewctivity);
        ButterKnife.bind(this);
        DbUtils.createDb(this, "favpage");
        initToolbar();
        initWebView();
        initFab();
    }

    private void initToolbar()
    {
        title = getIntent().getStringExtra("title");
        toolbarWebviewActivity.setTitle(title);
        setSupportActionBar(toolbarWebviewActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarWebviewActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWebView()
    {
        url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            url = Constant.ZHIHU + getIntent().getStringExtra("href");
        }
        //获取WebSettings对象
        settings = webviewWebviewActivity.getSettings();
        //启用触控缩放
        settings.setBuiltInZoomControls(true);
        //启用支持视窗meta标记（可实现双击缩放）
        settings.setUseWideViewPort(true);
        //以缩略图模式加载页面
        settings.setLoadWithOverviewMode(true);
        //启用JavaScript支持
        settings.setJavaScriptEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        settings.setDatabaseEnabled(true);
        cacheDirPath = getFilesDir().getAbsolutePath() + Constant.APP_CACAHE_DIRNAME;
        //设置数据库缓存路径
        settings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        settings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        settings.setAppCacheEnabled(true);
        //设置将接收各种通知和请求的WebViewClient（在WebView加载所有的链接）
        webviewWebviewActivity.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url)
            {
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                webviewWebviewActivity.loadUrl(url);
                return true;
            }
        });
        webviewWebviewActivity.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result)
            {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                result.confirm();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result)
            {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result)
            {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        //设置默认加载的网页
        webviewWebviewActivity.loadUrl(url);
    }

    private void initFab()
    {
        fabWebviewActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "已添加到收藏列表", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                FavPageBean data = new FavPageBean();
                data.setTitle(title);
                data.setUrl(url);
                if (DbUtils.getQueryByWhere(FavPageBean.class, "title", new String[]{title}).isEmpty())
                {
                    DbUtils.insert(data);
                }
            }
        });

    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache()
    {
        //清理Webview缓存数据库
        try
        {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath() + Constant.APP_CACAHE_DIRNAME);
        Logger.d("appCacheDir path=" + appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath() + "/webviewCache");
        Logger.d("webviewCacheDir path=" + webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if(webviewCacheDir.exists())
        {
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists())
        {
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file)
    {
        Logger.d("delete file path=" + file.getAbsolutePath());
        if (file.exists())
        {
            if (file.isFile())
            {
                file.delete();
            }
            else if (file.isDirectory())
            {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
        else
        {
            Logger.d("delete file no exists " + file.getAbsolutePath());
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
