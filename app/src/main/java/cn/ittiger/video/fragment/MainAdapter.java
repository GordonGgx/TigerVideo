package cn.ittiger.video.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public class MainAdapter extends FragmentPagerAdapter {

    private List<? extends VideoFragment> mList;

    public MainAdapter(FragmentManager fm, List<? extends VideoFragment> list) {

        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {

        return mList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mList.get(position).getName();
    }

    @Override
    public int getCount() {

        return mList.size();
    }

    public String getItemTitle(int position) {

        return mList.get(position).getName();
    }
}
