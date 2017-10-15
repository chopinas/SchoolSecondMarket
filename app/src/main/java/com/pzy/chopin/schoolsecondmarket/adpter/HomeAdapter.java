package com.pzy.chopin.schoolsecondmarket.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pzy.chopin.schoolsecondmarket.Bean.Bean;
import com.pzy.chopin.schoolsecondmarket.Bean.CateBean;
import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.view.GridViewForScrollView;

import java.util.List;

/**
 * Created by Chopin on 2017/9/6.
 */

public class HomeAdapter extends BaseAdapter {
    private Context context;
    private List<Bean.CateGoodBean> foodDatas;

    public HomeAdapter(Context context, List<Bean.CateGoodBean> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }

    @Override
    public int getCount() {
        if (foodDatas != null) {
            return foodDatas.size();
        } else {
            return 4;
        }
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bean.CateGoodBean cateGoodBean = foodDatas.get(position);
        List<Bean.CateGoodBean.GoodsBean> goodsBeanList = cateGoodBean.getData();
        String goodsname=cateGoodBean.getType();
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home, null);
            viewHold = new ViewHold();
            viewHold.gridView = (GridViewForScrollView) convertView.findViewById(R.id.gridView);
            viewHold.blank = (TextView) convertView.findViewById(R.id.blank);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        HomeItemAdapter adapter = new HomeItemAdapter(context,goodsBeanList);

        CateBean catebean=new CateBean();
        List<String> menulist=catebean.getGoodcatelist();

        viewHold.blank.setText(menulist.get(position));
        viewHold.gridView.setAdapter(adapter);
        return convertView;
    }

    private static class ViewHold {
        private GridViewForScrollView gridView;
        private TextView blank;
    }
}
