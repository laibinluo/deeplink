package com.llb.deeplink.deeplink;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyService extends Service {
    WebView mWebView ;
    View root ;

    private int i = 0;

    private void initView(){
        root = LayoutInflater.from(this).inflate(R.layout.activity_js,
                null);
        mWebView = root.findViewById(R.id.webview);
    }

    Handler handler = new Handler();

    Runnable runnable=new Runnable(){
        @Override
        public void run() {

            if (i >= MainActivity.dp.length){
                return;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.dp[i++]));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            // TODO Auto-generated method stub
            //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作

            handler.postDelayed(this, 2000);

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        i = 0;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("snssdk143://detail?groupid=6534762826882351629&gd_label=click_schema_hxhg16"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        handler.postDelayed(runnable, 2000);

//        initView();
//        WebSettings webSettings = mWebView.getSettings();
//
//        // 设置与Js交互的权限
//        webSettings.setJavaScriptEnabled(true);
//        // 设置允许JS弹窗
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//
//        // 先载入JS代码
//        // 格式规定为:file:///android_asset/文件名.html
//        mWebView.loadUrl("file:///android_asset/javascript.html");
//
//
//        mWebView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                // 注意调用的JS方法名要对应上
//                // 调用javascript的callJS()方法
//                mWebView.loadUrl("javascript:callJS()");
//            }
//        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(runnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
