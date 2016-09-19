package cn.ittiger.video.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author: laohu on 2016/9/19
 * @site: http://ittiger.cn
 */
public class DataKeeper {
    private static final String KEY_WU5LI_CURSOR = "cursor";

    public static void saveWu5LiCursor(String cursor) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ApplicationHelper.getInstance().getApplication());
        preferences.edit().putString(KEY_WU5LI_CURSOR, cursor).commit();
    }

    public static String getWu5LiCursor() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ApplicationHelper.getInstance().getApplication());
        return preferences.getString(KEY_WU5LI_CURSOR, "-1");
    }
}
