package com.pzy.chopin.schoolsecondmarket.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pzy.chopin.schoolsecondmarket.Bean.Bean;
import com.pzy.chopin.schoolsecondmarket.Bean.SearchBean;
import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.activity.Detail;
import com.pzy.chopin.schoolsecondmarket.activity.Search;

import java.util.List;
import java.util.zip.Inflater;

import static android.R.attr.path;

/**
 * Created by Chopin on 2017/9/11.
 */

public class SearchListAdapter extends BaseAdapter {
    private static final String mpath="http://schoolc2c.applinzi.com/shopImooc/admin/uploads/";
    Context context;
    List<SearchBean.CateBean.GoodsBean> goodsBeanList;


    public SearchListAdapter(Context context, List<SearchBean.CateBean.GoodsBean> goodsBeanList) {
        this.context=context;
        this.goodsBeanList=goodsBeanList;
    }

    @Override
    public int getCount() {
        return goodsBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return goodsBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewholer=null;
        if(view!=null){
            viewholer=(ViewHolder) view.getTag();
        }
        else{
            viewholer=new ViewHolder();
            view= View.inflate(context, R.layout.item_search,null);
            viewholer.tv_name = (TextView) view.findViewById(R.id.tv_search);
            view.setTag(viewholer);
        }
        viewholer.tv_name.setText(goodsBeanList.get(i).getPName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Search.msearch, Detail.class);
                intent.putExtra("name",goodsBeanList.get(i).getPName());
                intent.putExtra("goodsid",goodsBeanList.get(i).getId());
                intent.putExtra("price",goodsBeanList.get(i).getIPrice());
                intent.putExtra("belongto_man",goodsBeanList.get(i).getUId());
                intent.putExtra("descrip",goodsBeanList.get(i).getPDesc());
                intent.putExtra("email",goodsBeanList.get(i).getCName());
                intent.putExtra("goodsname",goodsBeanList.get(i).getId());
                SearchBean.CateBean.GoodsBean.ProImgBean picimgbean=goodsBeanList.get(i).getProImg().get(0);
                intent.putExtra("picpath",mpath+picimgbean.getAlbumPath());
                Search.msearch.startActivity(intent);
            }
        });
        return view;
    }

    static class ViewHolder {
        private TextView tv_name;
    }
}
