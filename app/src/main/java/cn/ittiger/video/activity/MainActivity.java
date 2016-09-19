package cn.ittiger.video.activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ittiger.video.R;
import cn.ittiger.video.app.TumblrApplication;
import cn.ittiger.video.base.BaseFragment;
import cn.ittiger.video.fragment.AboutFragment;
import cn.ittiger.video.fragment.Wu5LiVideoFragment;
import cn.ittiger.video.fragment.NetEasyVideoFragment;
import cn.ittiger.video.fragment.TtKbVideoFragment;
import cn.ittiger.video.player.VideoPlayerHelper;
import cn.ittiger.video.util.ShareHelper;
import cn.ittiger.video.util.UIUtil;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    SparseArray<BaseFragment> mFragmentSparseArray = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        init();
    }

    private void init() {

        NetEasyVideoFragment fragment = new NetEasyVideoFragment();
        switchFragment(fragment);
        mNavigationView.setCheckedItem(R.id.nav_net_easy);
        mFragmentSparseArray.put(R.id.nav_net_easy, fragment);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(item.isChecked()) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        int id = item.getItemId();
        BaseFragment fragment = mFragmentSparseArray.get(id);
        if(fragment == null) {
            switch (id) {
                case R.id.nav_net_easy:
                    if(fragment == null) {
                        fragment = new NetEasyVideoFragment();
                    }
                    break;
                case R.id.nav_ttkb:
                    fragment = new TtKbVideoFragment();
                    break;
                case R.id.nav_wu5li:
                    fragment = new Wu5LiVideoFragment();
                    break;
                case R.id.nav_share://分享
                    ShareHelper.shareApp(this);
                    break;
                case R.id.nav_about:
                    fragment = new AboutFragment();
                    break;
            }
        }
        if(fragment != null) {
            mFragmentSparseArray.put(id, fragment);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if(fragment != null) {
            switchFragment(fragment);
        }

        return true;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        ((TumblrApplication)getApplication()).onDestroy();
        VideoPlayerHelper.getInstance().stop();
    }

    @Override
    protected void onPause() {

        super.onPause();
        VideoPlayerHelper.getInstance().pause();
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {

        if(System.currentTimeMillis() - exitTime > 2000) {
            UIUtil.showToast(this, R.string.two_click_exit_app);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 切换界面
     * @param fragment
     */
    private void switchFragment(BaseFragment fragment) {

        setTitle(fragment.getName());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.content_main, fragment);

        fragmentTransaction.commit();
    }
}
