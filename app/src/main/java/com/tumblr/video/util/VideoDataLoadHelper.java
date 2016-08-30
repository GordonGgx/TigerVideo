package com.tumblr.video.util;

import com.tumblr.video.bean.VideoData;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author: laohu on 2016/8/24
 * @site: http://ittiger.cn
 */
public class VideoDataLoadHelper {
    private static final String VIDEO_FILE_NAME = "tumblr.txt";

    public static void loadAssertVideoData(final Runnable runnable) {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    InputStream is = ApplicationHelper.getInstance().getApplication().getAssets().open(VIDEO_FILE_NAME);
                    loadVideoData(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                super.onPostExecute(aVoid);
                if(runnable != null) {
                    runnable.run();
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
