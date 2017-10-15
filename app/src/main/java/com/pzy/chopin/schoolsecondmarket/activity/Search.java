package com.pzy.chopin.schoolsecondmarket.activity;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pzy.chopin.schoolsecondmarket.Bean.SearchBean;
import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.adpter.SearchListAdapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    public static Search msearch;
    Toolbar toolbar;
    SearchView mSearchView;
    SearchView.SearchAutoComplete mSearchAutoComplete;


    private static final String url_search="http://schoolc2c.applinzi.com/shopImooc/admin/search.php";
    List<SearchBean.CateBean.GoodsBean> goodsBeanList=new ArrayList<>();
    private SearchListAdapter searchListAdapter;
    private ListView listView_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        msearch=this;
        toolbar= (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchAutoComplete.isShown()) {
                    try {
                        mSearchAutoComplete.setText("");
                        Method method = mSearchView.getClass().getDeclaredMethod("onCloseClicked");
                        method.setAccessible(true);
                        method.invoke(mSearchView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    finish();
                }
            }
        });

        listView_search = (ListView) findViewById(R.id.lv_search);
        searchListAdapter=new SearchListAdapter(this,goodsBeanList);
        listView_search.setAdapter(searchListAdapter);
//        searchListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);

        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        mSearchView.setQueryHint("请输入商品名称");

        //设置输入框提示文字样式
        mSearchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.background_light));
        mSearchAutoComplete.setTextColor(getResources().getColor(android.R.color.background_light));
        mSearchAutoComplete.setTextSize(14);
        //设置触发查询的最少字符数（默认2个字符才会触发查询）
//        mSearchAutoComplete.setThreshold(1);

        //设置搜索框有字时显示叉叉，无字时隐藏叉叉
        mSearchView.onActionViewExpanded();
        mSearchView.setIconified(true);

        //修改搜索框控件间的间隔（这样只是为了更加接近网易云音乐的搜索框）
        LinearLayout search_edit_frame = (LinearLayout) mSearchView.findViewById(R.id.search_edit_frame);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) search_edit_frame.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        search_edit_frame.setLayoutParams(params);

        //监听SearchView的内容
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                goodsBeanList.clear();
                getdata(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 获取json并存入数据库
     */
    public void getdata(String query) {
        RequestParams params=new RequestParams(url_search);
        params.addBodyParameter("keywords",query);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //TODO:进行json数据解析
                Log.d("queryresult",result);
                String is_ok = null;
                try {
                    JSONObject object=new JSONObject(result);
                    is_ok = object.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(is_ok.equals("get pros success")){
                    SearchBean bean=new SearchBean();
                    Gson gson=new Gson();
                    bean=new GsonBuilder().create().fromJson(result,SearchBean.class);
                    if(bean.getMessage().equals("get pros success")){
                        for(int i=0;i<bean.getData().size();i++){
                            SearchBean.CateBean.GoodsBean goods=bean.getData().get(i).getData();
                            goodsBeanList.add(goods);
                        }
                        searchListAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    searchListAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"未匹配到数据",Toast.LENGTH_SHORT).show();
                }

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
