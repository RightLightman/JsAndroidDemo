package com.jiangtea.jsandroiddemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.wv);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//打开js和安卓通信
        //加载本地assets目录下的网页
        mWebView.loadUrl("file:///android_asset/demo.html");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //直接使用webview加载js就可以了
                mWebView.loadUrl("javascript:wave()");
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });


        //核心方法, 用于处理js被执行后的回调
        mWebView.addJavascriptInterface(new JsCallback() {

            @JavascriptInterface//注意:此处一定要加该注解,否则在4.1+系统上运行失败
            @Override
            public void onJsCallback() {
                System.out.println("js调用Android啦");
            }
        }, "demo");//参1是回调接口的实现;参2是js回调对象的名称

    }

    //定义回调接口
    public interface JsCallback {
        public void onJsCallback();
    }

}
