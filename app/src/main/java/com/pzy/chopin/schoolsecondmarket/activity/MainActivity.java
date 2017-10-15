package com.pzy.chopin.schoolsecondmarket.activity;

import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.adpter.ViewPagerAdpter;
import com.pzy.chopin.schoolsecondmarket.fragment.ProductList;
import com.pzy.chopin.schoolsecondmarket.fragment.Upload;
import com.pzy.chopin.schoolsecondmarket.fragment.UserCenterFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivity;

    ViewPager viewPager;
    TabLayout tabLayout;

    ViewPagerAdpter adapter;

    List<Fragment> fragmentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onstartsssss","destroy");
        setContentView(R.layout.activity_main);
        mainActivity=this;
        init();
    }


    private void init() {

        viewPager= (ViewPager) findViewById(R.id.main_viewpager);
        tabLayout= (TabLayout) findViewById(R.id.main_tablayout);

        Upload upload=new Upload();

        fragmentList.add(new ProductList());
        fragmentList.add(upload);
        fragmentList.add(new UserCenterFragment());


        adapter=new ViewPagerAdpter(getSupportFragmentManager(),fragmentList,MainActivity.this);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
                if (tab.getCustomView() != null) {
                    View tabView = (View) tab.getCustomView().getParent();
                    tabView.setTag(i);
                }
            }
        }
    }

}
