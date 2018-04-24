package com.llb.deeplink.deeplink;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    ListView listView ;

    public Button btnStart;

    public Button btnStop;


    public static  String[] title = { "波波视频1", "汽车之家1", "爱奇艺", "手机百度",
            "天天快报", "PPTV" };

    public static  String[] dp = {"bobo://bb.web/v?vid=6329306112713555968&pg=jg&pg_position=jg3&partner_source=channel",
    "autohome://article/platform?newsid=2223180&type=1&showcommentlist=0&label=jixin&ltype=69&dt=20180312",
    "iqiyi://mobile/home?ftype=27&subtype=347",
    "baiduboxapp://utils?action=sendIntent&minver=7.4&params=%7B%22intent%22%3A%22intent%3A%23Intent%3Baction%3Dcom.baidu.searchbox.action.HOME%3BS.targetCommand%3D%257B%2522mode%2522%253A%25220%2522%252C%2522intent%2522%253A%2522intent%253A%2523Intent%253BB.bdsb_append_param%253Dtrue%253BS.bdsb_light_start_url%253Dhttp%25253A%25252F%25252Fbaijiahao.baidu.com%25252Fs%25253Fid%25253D1595361518306467257%253Bend%2522%252C%2522class%2522%253A%2522com.baidu.searchbox.xsearch.UserSubscribeCenterActivity%2522%252C%2522min_v%2522%253A%252216787968%2522%257D%3Bend%22%7D&needlog=1&logargs=%7B%22source%22%3A%221021229k%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22other%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22url%22%2C%22channel%22%3A%22%22%2C%22ext%22%3A%22%7B%5C%22sid%5C%22%3A%5C%22%7Bqueryid%7D%5C%22%7D%22%7D",
    "qnreading://article_detail?nm=20180216A0PRC900&from=aaa6",
    "pptv://page/player/halfscreen?type=vod&vid=27706889&sid=9044950&utm=194"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("snssdk143://detail?groupid=6534762826882351629&gd_label=click_schema_hxhg16"));
//        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        // startService(new Intent(this, MyService.class));
        listView = (ListView) findViewById(R.id.list_view);

        btnStart = (Button) findViewById(R.id.start);

        btnStop = (Button) findViewById(R.id.stop);

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, title);

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
                String str = dp[i];
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

//        web_main = (WebView) findViewById(R.id.web_main);
//
//        web_main.loadUrl("file:///android_asset/h5.html");
//
//        web_main.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {
//                    //不处理http, https, ftp的请求
//                    return false;
//                }
//                Intent intent;
//                try {
//                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                } catch (URISyntaxException e) {
//                    // Log.e(TAG, "URISyntaxException: " + e.getLocalizedMessage());
//                    return false;
//                }
//                intent.setComponent(null);
//                try {
//                    startActivity(intent);
//                } catch (ActivityNotFoundException e) {
//                    //XLLog.e(TAG, "ActivityNotFoundException: " + e.getLocalizedMessage());
//                    return false;
//                }
//                return true;
//            }
//            //            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////
////                if (url.startsWith("will://")) {
////                    Uri uri = Uri.parse(url);
////                    Log.e("---------scheme: ", uri.getScheme() + "host: " + uri.getHost() + "Id: " + uri.getPathSegments().get(0));
////                    Toast.makeText(MainActivity.this, "打开新的页面", Toast.LENGTH_LONG).show();
////                    return true; //返回true，代表要拦截这个url
////                }
////                return super.shouldOverrideUrlLoading(view, url);
////            }
//        });
    }
}
