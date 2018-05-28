package com.llb.deeplink.deeplink;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.llb.deeplink.deeplink.util.LogUtils;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    public static String TAG = "testMainDp";

    public Button btnStart;

    public Button btnStop;


    public static String[] title = {"PPTV", "腾讯视频", "腾讯新闻", "西瓜视频",
            "火山小视频", "抖音"};

    public static String[] dp = {
            "pptv://page/player/halfscreen?type=vod&vid=27757231&sid=9045060&utm=194",
            "tenvideo2://?action=1&cover_id=tr1mxts1ncwzfaq&video_id=t0026b9nxmn&from=30125|7",
            "qqnews://article_9527?nm=20180428C1ONTJ00&from=dkl2",
            "snssdk32://home/news?gd_label=click_schema_hxhg9",
            "snssdk1112://main?&push_id=80400050&gd_label=click_schema_hxhg9",
            "snssdk1128://detail?id=6549850631253789966&gd_label=click_schema_hxhg9"};

    private ScreenBroadcastReceiver mScreenReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);

        btnStart = (Button) findViewById(R.id.start);

        btnStop = (Button) findViewById(R.id.stop);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, title);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setItemsCanFocus(false);
                startService(new Intent(MainActivity.this, MyService.class));
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this, MyService.class));
            }
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                launcherDp(i);
            }
        });

        mScreenReceiver = new ScreenBroadcastReceiver();
        registerListener();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterListener();
    }

    private void launcherDp(int index) {
        String str = dp[index];
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "没有安装 " + title[index], Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 停止screen状态监听
     */
    public void unregisterListener() {
        unregisterReceiver(mScreenReceiver);
    }

    /**
     * 启动screen状态广播接收器
     */
    private void registerListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenReceiver, filter);
    }

    /**
     * screen状态广播接收者
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
                LogUtils.d(TAG, "michael ACTION_SCREEN_ON");
                Intent i = new Intent(Intent.ACTION_MAIN, null);
                i.addCategory(Intent.CATEGORY_HOME);
                startActivity(i);
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                launcherDp(1);
                LogUtils.d(TAG, "michael ACTION_SCREEN_OFF");
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
                LogUtils.d(TAG, "michael ACTION_USER_PRESENT");
            }
        }
    }
}
