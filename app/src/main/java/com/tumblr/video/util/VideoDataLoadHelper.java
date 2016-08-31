package com.tumblr.video.util;

import com.tumblr.video.bean.VideoData;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author: laohu on 2016/8/24
 * @site: http://ittiger.cn
 */
public class VideoDataLoadHelper {
    private static final String TAG = "VideoDataLoadHelper";
    private static final String VIDEO_FILE_NAME = "tumblr.txt";

    public static void loadAssertVideoData(final Callback callback) {

        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {

                try {
                    InputStream is = ApplicationHelper.getInstance().getApplication().getAssets().open(VIDEO_FILE_NAME);
                    loadVideoData(is);
                    return true;
                } catch (IOException e) {
                    Log.d(TAG, "load video data failure", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean isSuccess) {

                super.onPostExecute(isSuccess);
                if(callback != null) {
                    callback.callback(isSuccess);
                }
            }
        };
        task.execute();
    }

    private static void loadVideoData(InputStream is) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String url = br.readLine();
        while (!TextUtils.isEmpty(url)) {
            VideoData videoData = new VideoData(url);
            DBManager.getInstance().getSQLiteDB().save(videoData);
            url = br.readLine();
        }
    }
}
