package com.pzy.chopin.schoolsecondmarket.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pzy.chopin.schoolsecondmarket.R;

import java.util.List;

/**
 * Created by Chopin on 2017/9/2.
 */

public class ViewPagerAdpter extends FragmentPagerAdapter{

    List<Fragment> fragmentList=null;

    static Context context;

    int[] imageresid=new int[]{
            R.drawable.index,
            R.drawable.add,
            R.drawable.user,
    };
    String[] tabTitles=new String[]{
            "首页",
            "添加",
            "我的"
    };


    public ViewPagerAdpter(FragmentManager fm, List<Fragment> fragmentList, Context context) {
        super(fm);
        this.fragmentList=fragmentList;
        this.context=context;
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
        return null;
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.tablayout_button_style, null);
        TextView tv= (TextView) view.findViewById(R.id.textView_buttion_tablayout);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) view.findViewById(R.id.imageView_buttion_tablayout);
        img.setImageResource(imageresid[position]);
        return view;
    }
}
