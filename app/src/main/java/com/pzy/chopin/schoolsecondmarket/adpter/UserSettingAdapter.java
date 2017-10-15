package com.pzy.chopin.schoolsecondmarket.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pzy.chopin.schoolsecondmarket.R;

/**
 * Created by Chopin on 2017/9/29.
 */

public class UserSettingAdapter extends BaseAdapter {
    Context context;

    String titles[];

    public UserSettingAdapter(String titles[],Context context){
        this.titles=titles;
        this.context=context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return titles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            view=View.inflate(context, R.layout.item_usersetting,null);
            viewHolder= new ViewHolder();
            viewHolder.textView=(TextView) view.findViewById(R.id.tv_usersetting);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(titles[i]);

        return view;
    }

    private  static class ViewHolder{
         TextView textView;
    }
}
