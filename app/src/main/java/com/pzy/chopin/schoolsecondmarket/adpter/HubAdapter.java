package com.pzy.chopin.schoolsecondmarket.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pzy.chopin.schoolsecondmarket.Bean.CollectionBean;
import com.pzy.chopin.schoolsecondmarket.R;

import java.util.List;

/**
 * Created by Chopin on 2017/9/21.
 */

public class HubAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener onItemLongClickListener=null;
    public static final String pic_path="http://schoolc2c.applinzi.com/shopImooc/admin/uploads/";

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    List<CollectionBean.GoodsBean> goodsBeanList;
    Context context;

    public HubAdapter(Context context, List<CollectionBean.GoodsBean> goodsBeanList){
        this.context=context;
        this.goodsBeanList=goodsBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hub,parent,false);
        MyViewHolder vh=new MyViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if(onItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemLongClickListener.onItemLongClick(holder.itemView,position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }
            //将position保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(position);
            holder.tv_name.setText("商品名称:  "+goodsBeanList.get(position).getPName());
            holder.tv_price.setText("商品价格:  "+goodsBeanList.get(position).getIPrice());
            Log.d("hubname",goodsBeanList.get(position).getProImg().get(0).getAlbumPath());
            Glide.with(context).load(pic_path+goodsBeanList.get(position)
                                .getProImg().get(0).getAlbumPath())
                                .override(60, 60) // resizes the image to these dimensions (in pixel)
                                .centerCrop() // this cropping technique scales the image so that it fills the requested bounds and
                                .into(holder.iv_icon);
    }


    @Override
    public int getItemCount() {
        return goodsBeanList.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.onItemLongClickListener=listener;
    }

}
class  MyViewHolder extends RecyclerView.ViewHolder {
    TextView tv_name,tv_price;
    ImageView iv_icon;

    public MyViewHolder(View itemView) {
        super(itemView);
        tv_name=(TextView) itemView.findViewById(R.id.tv_hub_name);
        tv_price=(TextView) itemView.findViewById(R.id.tv_hub_price);
        iv_icon=(ImageView) itemView.findViewById(R.id.iv_hub_icon);
    }
}

