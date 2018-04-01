package com.llb.deeplink.deeplink;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    WebView web_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web_main = (WebView) findViewById(R.id.web_main);

        web_main.loadUrl("file:///android_asset/h5.html");

        web_main.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {
                    //不处理http, https, ftp的请求
                    return false;
                }
                Intent intent;
                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                } catch (URISyntaxException e) {
                    // Log.e(TAG, "URISyntaxException: " + e.getLocalizedMessage());
                    return false;
                }
                intent.setComponent(null);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    //XLLog.e(TAG, "ActivityNotFoundException: " + e.getLocalizedMessage());
                    return false;
                }
                return true;
            }
            //            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                if (url.startsWith("will://")) {
//                    Uri uri = Uri.parse(url);
//                    Log.e("---------scheme: ", uri.getScheme() + "host: " + uri.getHost() + "Id: " + uri.getPathSegments().get(0));
//                    Toast.makeText(MainActivity.this, "打开新的页面", Toast.LENGTH_LONG).show();
//                    return true; //返回true，代表要拦截这个url
//                }
//                return super.shouldOverrideUrlLoading(view, url);
//            }
        });
    }
}
