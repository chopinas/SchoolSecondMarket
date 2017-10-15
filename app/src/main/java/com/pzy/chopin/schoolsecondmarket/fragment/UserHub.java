package com.pzy.chopin.schoolsecondmarket.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pzy.chopin.schoolsecondmarket.Bean.CollectionBean;
import com.pzy.chopin.schoolsecondmarket.activity.MainActivity;
import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.activity.Detail;
import com.pzy.chopin.schoolsecondmarket.adpter.HubAdapter;
import com.pzy.chopin.schoolsecondmarket.view.MyDecoration;

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

public class UserHub extends Fragment {

    private static final String hub_url="http://schoolc2c.applinzi.com/shopImooc/returnmypro.php";
    private static  final String delete_url="http://schoolc2c.applinzi.com/shopImooc/admin/doAdminAction.php?act=delPro&id=";

    SwipeRefreshLayout sw_fresh;

    SharedPreferences sp;

    RecyclerView view_hub;
    HubAdapter adapter;

    Gson gson;
    CollectionBean bean;

    List<CollectionBean.GoodsBean> goodsBeanList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_hub,container,false);
        init(view);
        getdata();

        return view;
    }

    private void init(View view) {

        goodsBeanList.clear();

        sp=getContext().getSharedPreferences("login", MODE_PRIVATE);

        view_hub=(RecyclerView) view.findViewById(R.id.lv_hub);
        adapter=new HubAdapter(getContext(),goodsBeanList);

        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        view_hub.setLayoutManager(manager);

        view_hub.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        //这句就是添加我们自定义的分隔线
        view_hub.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST));

        /**
         * 长按删除
         */
        adapter.setOnItemLongClickListener(new HubAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.mainActivity);
                builder.setMessage("是否要删除改商品");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CollectionBean.GoodsBean goodBean=goodsBeanList.get(position);
                        RequestParams params=new RequestParams(delete_url+goodBean.getId());
                        x.http().get(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.d("delete_result",result);
                                goodsBeanList.clear();
                                getdata();
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
                });
                builder.show();
            }
        });

        /**
         * 点击跳转到详情页面
         */
        adapter.setOnItemClickListener(new HubAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CollectionBean.GoodsBean goodBean=goodsBeanList.get(position);
                Intent i=new Intent(getContext(), Detail.class);
                i.putExtra("name",goodBean.getPName());
                i.putExtra("price",goodBean.getIPrice());
                i.putExtra("belongto_man",goodBean.getUsername());
                i.putExtra("descrip",goodBean.getPDesc());
                i.putExtra("picpath",HubAdapter.pic_path+goodBean.getProImg().get(0).getAlbumPath());
                i.putExtra("email",goodBean.getEmail());
                i.putExtra("goodsid",goodBean.getId());
                startActivity(i);
            }
        });

        sw_fresh=(SwipeRefreshLayout) view.findViewById(R.id.sw_refresh_hub);
        // 设置下拉进度的背景颜色，默认就是白色的
        sw_fresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        sw_fresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        sw_fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 获取数据
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goodsBeanList.clear();
                        getdata();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "刷新了数据", Toast.LENGTH_SHORT).show();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        sw_fresh.setRefreshing(false);
                    }
                }, 1200);

            }

        });
    }

    public void getdata() {
        int userid=sp.getInt("uid",00000);
        RequestParams params=new RequestParams(hub_url);
        params.addParameter("uId",userid);

        gson=new Gson();
        bean=new CollectionBean();

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                bean=gson.fromJson(result,CollectionBean.class);

                for(int i=0;i<bean.getGoodsbean().size();i++){
                    goodsBeanList.add(bean.getGoodsbean().get(i));
                }
                Log.d("getdata",result);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getContext(), "出了一点错误", Toast.LENGTH_SHORT).show();
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
