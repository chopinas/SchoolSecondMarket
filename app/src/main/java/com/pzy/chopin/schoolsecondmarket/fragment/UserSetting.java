package com.pzy.chopin.schoolsecondmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.activity.About;
import com.pzy.chopin.schoolsecondmarket.adpter.UserSettingAdapter;

/**
 * Created by Chopin on 2017/9/15.
 */

public class UserSetting extends Fragment {

    ListView lv_usersetting;

    UserSettingAdapter adapter;

    String titles[]={
            "尚未开发",
            "尚未开发",
            "尚未开发",
            "尚未开发",
            "尚未开发",
            "尚未开发",
            "关于软件"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_setting,container,false);
        init(view);
        return view;
    }

    private void init(View view) {
        lv_usersetting=(ListView) view.findViewById(R.id.lv_usersetting);
        adapter=new UserSettingAdapter(titles,getContext());
        lv_usersetting.setAdapter(adapter);

        lv_usersetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i<6){
                    Toast.makeText(getContext(), "此功能尚未开发", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(getContext(), About.class));
                }
            }
        });
    }
}
