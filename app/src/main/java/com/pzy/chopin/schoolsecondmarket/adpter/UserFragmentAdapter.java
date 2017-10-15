package com.pzy.chopin.schoolsecondmarket.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Chopin on 2017/9/15.
 */

public class UserFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    private final static String[] nametitle=new String[] { "我的收藏", "我的仓库", "个人中心" };


    public UserFragmentAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return nametitle[position];
    }
}
