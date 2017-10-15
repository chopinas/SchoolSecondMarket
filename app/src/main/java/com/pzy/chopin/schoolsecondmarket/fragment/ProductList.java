package com.pzy.chopin.schoolsecondmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pzy.chopin.schoolsecondmarket.Bean.Bean;
import com.pzy.chopin.schoolsecondmarket.activity.MainActivity;
import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.activity.Search;
import com.pzy.chopin.schoolsecondmarket.adpter.HomeAdapter;
import com.pzy.chopin.schoolsecondmarket.adpter.MenuAdapter;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Chopin on 2017/9/2.
 */

public class ProductList extends Fragment {
    private static final String url="http://schoolc2c.applinzi.com/shopImooc/returnpro.php";

    private List<String> menulist=new ArrayList<>();
    private List<Bean.CateGoodBean> homeList = new ArrayList<>();
    private List<Integer> showTitle;

    private ListView lv_menu;
    private ListView lv_home;

    private MenuAdapter menuAdapter;
    private HomeAdapter homeAdapter;
    private int currentItem;

    private TextView tv_title;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_productlist,container,false);
        Log.d("fragment","1");
        init(view);
        getJsondata();
        search(view);
        return view;
    }

    /**
     * 搜索跳转操作
     */
    private void search(View view) {

        LinearLayout linearLayout=(LinearLayout) view.findViewById(R.id.line_search);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.mainActivity, Search.class));
            }
        });
    }


    /**
     * 从后台获取json数据并解析json数据
     */
    private void getJsondata() {
        RequestParams parms=new RequestParams(url);
        x.http().get(parms, new Callback.CommonCallback<String >() {
            @Override
            public void onSuccess(String result) {
                Log.d("json",result);
                showTitle=new ArrayList<>();
                //TODO:进行json解析
                Bean bean= new Bean();
                Gson gson=new Gson();
                bean=new GsonBuilder().create().fromJson(result,Bean.class);
                for (int i = 0; i < bean.getData().size(); i++) {
                    Bean.CateGoodBean cateGoodBean=bean.getData().get(i);
                    //对meanlist里面的数据进行判断，不然会出现下标越界
                    if (menulist.size()<4)
                    {
                        menulist.add(cateGoodBean.getType());
                        homeList.add(cateGoodBean);
                        showTitle.add(i);
                    }
                }
                tv_title.setText(bean.getData().get(0).getType());
                menuAdapter.notifyDataSetChanged();
                homeAdapter.notifyDataSetChanged();
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


    private void init(View v) {

        lv_menu = (ListView)v.findViewById(R.id.lv_menu);
        tv_title = (TextView) v.findViewById(R.id.tv_titile);
        lv_home = (ListView)v.findViewById(R.id.lv_home);

        menuAdapter = new MenuAdapter(getContext(),menulist);
        lv_menu.setAdapter(menuAdapter);

        homeAdapter=new HomeAdapter(getContext(),homeList);
        lv_home.setAdapter(homeAdapter);

        swipeRefreshLayout=(SwipeRefreshLayout) v.findViewById(R.id.swlayout_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menulist.clear();
                        homeList.clear();
                        showTitle.clear();
                        getJsondata();
                        Toast.makeText(getContext(), "数据已刷新", Toast.LENGTH_SHORT).show();

                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1200);

            }
        });


        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetInvalidated();
                tv_title.setText(menulist.get(position));
                lv_home.setSelection(showTitle.get(position));
            }
        });


        lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                int current = showTitle.indexOf(firstVisibleItem);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
                    tv_title.setText(menulist.get(currentItem));
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
                }
            }
        });
    }

}
