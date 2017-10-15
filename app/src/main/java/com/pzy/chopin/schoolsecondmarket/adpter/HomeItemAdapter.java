package com.pzy.chopin.schoolsecondmarket.adpter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pzy.chopin.schoolsecondmarket.Bean.Bean;
import com.pzy.chopin.schoolsecondmarket.activity.MainActivity;
import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.activity.Detail;


import java.util.List;


/**
 * Created by Chopin on 2017/9/7.
 */

public class HomeItemAdapter extends BaseAdapter {
    private String path="http://schoolc2c.applinzi.com/shopImooc/admin/uploads/";
    private Context context;
    private List<Bean.CateGoodBean.GoodsBean> foodDatas;

    public HomeItemAdapter(Context context, List<Bean.CateGoodBean.GoodsBean> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }


    @Override
    public int getCount() {
        if (foodDatas != null) {
            return foodDatas.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Bean.CateGoodBean.GoodsBean goodsBean = foodDatas.get(position);
        Bean.CateGoodBean.GoodsBean.ProImgBean picimgbean=goodsBean.getProImg().get(0);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home_category, null);
            viewHold = new ViewHold();
            viewHold.tv_name = (TextView) convertView.findViewById(R.id.item_home_name);
            viewHold.iv_icon = (ImageView) convertView.findViewById(R.id.item_album);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(goodsBean.getPName());
        Uri uri = Uri.parse(path+picimgbean.getAlbumPath().trim());
        Glide.with(context).load(uri).into(viewHold.iv_icon);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"click"+goodsBean.getPName(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.mainActivity,Detail.class);
                intent.putExtra("name",goodsBean.getPName());
                intent.putExtra("goodsid",goodsBean.getId());
                intent.putExtra("price",goodsBean.getIPrice());
                intent.putExtra("belongto_man",goodsBean.getUId());
                intent.putExtra("descrip",goodsBean.getPDesc());
                intent.putExtra("email",goodsBean.getCName());
                intent.putExtra("goodsname",goodsBean.getId());
                Bean.CateGoodBean.GoodsBean.ProImgBean picimgbean=goodsBean.getProImg().get(0);
                intent.putExtra("picpath",path+picimgbean.getAlbumPath());
                MainActivity.mainActivity.startActivity(intent);
            }
        });
        return convertView;


    }

    private static class ViewHold {
        private TextView tv_name;
        private ImageView iv_icon;
    }

}
