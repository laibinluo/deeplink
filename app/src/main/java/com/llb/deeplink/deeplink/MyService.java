package com.llb.deeplink.deeplink;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import static com.llb.deeplink.deeplink.MainActivity.title;

public class MyService extends Service {
    AudioManager mAudioManager;
    int index;

    private int i = 0;

    Handler handler = new Handler();
    Runnable runnable=new Runnable(){
        @Override
        public void run() {

            if (i >= MainActivity.dp.length){
                try {
                    Thread.sleep(60 * 1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, index);
                startActivity(new Intent(MyService.this, MainActivity.class));
                return;
            }

            String Strtitle = MainActivity.title[i];
            Log.d(MainActivity.TAG, "i : " + i);
            Log.d(MainActivity.TAG, "title : " + MainActivity.title[i]);
            Log.d(MainActivity.TAG, "deeplink : " + MainActivity.dp[i]);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.dp[i]));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);

                if (Strtitle.contains("视频") || Strtitle.contains("TV")
                        || Strtitle.contains("tv")){
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                }
//                else{
//                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
//                    Log.d(MainActivity.TAG, "setStreamVolume : " + index);
//                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "没有安装 " + title[i], Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            // TODO Auto-generated method stub
            //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
            i++;
            handler.postDelayed(runnable, 60 * 1000);

        }
    };

    @Override
    public void onStart(Intent intent, int startId) {
        i = 0;
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 2000);
        index = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.d(MainActivity.TAG, "on start index : " + index);
        super.onStart(intent, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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
