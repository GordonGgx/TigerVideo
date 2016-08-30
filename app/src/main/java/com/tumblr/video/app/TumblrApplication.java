package com.tumblr.video.app;

import cn.ittiger.database.SQLiteDBConfig;

import com.tumblr.video.util.ApplicationHelper;
import com.tumblr.video.util.CrashHandler;

import android.app.Application;

/**
 * @author laohu
 */
public class TumblrApplication extends Application implements IDbApplication {
    /**
     * 本地数据库配置
     */
    private SQLiteDBConfig mDBConfig;

    @Override
    public void onCreate() {

        super.onCreate();

        CrashHandler.getInstance().init(getApplicationContext());
        ApplicationHelper.getInstance().init(this);
    }

    @Override
    public SQLiteDBConfig getGlobalDbConfig() {

        if(mDBConfig == null) {
            synchronized (TumblrApplication.class) {
                if(mDBConfig == null) {
                    mDBConfig = new SQLiteDBConfig(getApplicationContext());
                    //本地数据库文件保存在应用文件目录
                    mDBConfig.setDbDirectoryPath(getApplicationContext().getCacheDir().getAbsolutePath());
                }
            }
        }
        return mDBConfig;
    }

    public void onDestory() {

        ApplicationHelper.getInstance().onDestory();
        this.mDBConfig = null;
    }
}
