package com.pzy.chopin.schoolsecondmarket.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pzy.chopin.schoolsecondmarket.Bean.CollectionBean;
import com.pzy.chopin.schoolsecondmarket.R;

import java.util.List;

/**
 * Created by Chopin on 2017/9/18.
 */

public class ColleacitonAdapter extends BaseAdapter {
    private static final  String picurl="http://schoolc2c.applinzi.com/shopImooc/admin/uploads/";
    List<CollectionBean.GoodsBean> goodlist;
    Context context;

    public ColleacitonAdapter( List<CollectionBean.GoodsBean> goodlist,Context context){
        this.goodlist=goodlist;
        this.context=context;
    }

    @Override
    public int getCount() {
        return goodlist.size();
    }

    @Override
    public Object getItem(int i) {
        return goodlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=View.inflate(context, R.layout.item_collect,null);
            viewHolder.iv_colletction=(ImageView) view.findViewById(R.id.iv_collerction_icon);
            viewHolder.tv_name=(TextView) view.findViewById(R.id.tv_collection_name);
            viewHolder.tv_price=(TextView) view.findViewById(R.id.tv_colletction_price);
            view.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        CollectionBean.GoodsBean goodbean=goodlist.get(i);

        viewHolder.tv_name.setText("商品名称： "+goodbean.getPName());
        viewHolder.tv_price.setText("商品价格:  "+goodbean.getIPrice());
        Glide.with(context)
                .load(picurl+goodbean.getProImg().get(0).getAlbumPath())
                .override(60, 60) // resizes the image to these dimensions (in pixel)
                .centerCrop() // this cropping technique scales the image so that it fills the requested bounds and
                .into(viewHolder.iv_colletction);

        return view;
    }

    static class ViewHolder{
        ImageView iv_colletction;
        TextView tv_name,tv_price;
    }
}
