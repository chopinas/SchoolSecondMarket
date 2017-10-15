package com.pzy.chopin.schoolsecondmarket.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pzy.chopin.schoolsecondmarket.Bean.CollectionBean;
import com.pzy.chopin.schoolsecondmarket.activity.MainActivity;
import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.activity.Detail;
import com.pzy.chopin.schoolsecondmarket.adpter.ColleacitonAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Chopin on 2017/9/15.
 */

public class UserCollection extends Fragment {
    private static String url_collecting="http://schoolc2c.applinzi.com/shopImooc/returnco.php";
    private static final  String picurl="http://schoolc2c.applinzi.com/shopImooc/admin/uploads/";
    SharedPreferences sp;
    SwipeRefreshLayout sw_fresh;
    ColleacitonAdapter adapter;
    ListView listView;
    CollectionBean goodslist;
    List<CollectionBean.GoodsBean> goodlist=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragment","3.1");
        View view=inflater.inflate(R.layout.user_collection,container,false);
        init(view);
        getconnection();
        return view;
    }

    private void init(View view) {
        //为了解决转到其他fragment后在转回来时候出现重复的情况
        goodlist.clear();

        sp=getContext().getSharedPreferences("login", MODE_PRIVATE);

        listView=(ListView)view.findViewById(R.id.lv_collection);

        adapter=new ColleacitonAdapter(goodlist,getContext());
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CollectionBean.GoodsBean goodbean=goodlist.get(i);
                Intent intent=new Intent(getContext(), Detail.class);
                intent.putExtra("name",goodlist.get(i).getPName());
                intent.putExtra("price",goodlist.get(i).getIPrice());
                intent.putExtra("belongto_man",goodlist.get(i).getUsername());
                intent.putExtra("descrip",goodlist.get(i).getPDesc());
                intent.putExtra("picpath",picurl+goodbean.getProImg().get(0).getAlbumPath());
                intent.putExtra("email",goodlist.get(i).getEmail());
                intent.putExtra("goodsid",goodlist.get(i).getId());
                MainActivity.mainActivity.startActivity(intent);
            }
        });


        sw_fresh=(SwipeRefreshLayout) view.findViewById(R.id.sw_refresh);
        // 设置下拉进度的背景颜色，默认就是白色的
        sw_fresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        sw_fresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        sw_fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goodlist.clear();
                        getconnection();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "刷新了一条数据", Toast.LENGTH_SHORT).show();

                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        sw_fresh.setRefreshing(false);
                    }
                }, 1200);

                // System.out.println(Thread.currentThread().getName());

                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
            }

        });

    }


    /**
     * 从后台获取收藏的数据
     */

    public void getconnection() {
        goodslist=new CollectionBean();
        goodlist.clear();
        final Gson gson=new Gson();

        int id=sp.getInt("uid",0000);
        RequestParams params=new RequestParams(url_collecting);
        params.addParameter("uId",id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                goodslist=gson.fromJson(result,CollectionBean.class);
                for (int i=0;i<goodslist.getGoodsbean().size();i++){
                    goodlist.add(goodslist.getGoodsbean().get(i));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

}
