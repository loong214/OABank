package com.findingdata.oabank.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.NotifyListAdapter;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.NotifyEntity;
import com.findingdata.oabank.entity.NotifyListEntity;
import com.findingdata.oabank.entity.Transition;
import com.findingdata.oabank.utils.Config;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.XHttp;
import com.findingdata.oabank.weidgt.RecyclerViewDivider;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Loong on 2019/11/21.
 * Version: 1.0
 * Describe: 消息列表Activity
 */
@ContentView(R.layout.activity_notify)
public class NotifyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_title;
    @ViewInject(R.id.notify_srl_fresh)
    private SwipeRefreshLayout msf;
    @ViewInject(R.id.notify_rl_list)
    private RecyclerView mrv;

    private int pageIndex = 1;
    private int pageSize = 10;
    private List<NotifyEntity> dataList=new ArrayList<>();
    private NotifyListAdapter adapter;
    private int loadStatus=2;//1为刷新，2为加载更多
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText("消息中心");

        //注册下拉刷新
        msf.setOnRefreshListener(this);
        //设置圈圈的颜色
        msf.setColorSchemeColors(Color.rgb(47, 223, 189));

        mrv.setLayoutManager(new LinearLayoutManager(this));
        mrv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
        //注册RecyclerView点击
        mrv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                //startActivity(new Intent(NotifyActivity.this, ProjectDetailsActivity.class).putExtra("project_id", dataList.get(position).getProject_id()));
                Toast.makeText(NotifyActivity.this,dataList.get(position).getF_name(),Toast.LENGTH_SHORT).show();
            }
        });

        //初始化adapter
        adapter = new NotifyListAdapter(R.layout.item_notify_list, dataList);
        adapter.setOnLoadMoreListener(NotifyActivity.this,mrv);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setLoadMoreView(new SimpleLoadMoreView());

        mrv.setAdapter(adapter);
        getData();
    }

    /**
     * 获取后台数据
     */
    private void getData(){
        Map<String,String> param=new HashMap<>();
        param.put("pageIndex",pageIndex+"");
        param.put("pageSize",pageSize+"");
        XHttp.Get(Config.BASE_URL + "api/v1/base/getAllFile", param, new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<NotifyListEntity> entity= JsonParse.parse(result,NotifyListEntity.class);
                if(entity.getCode()==200){
                    if(loadStatus==1){
                        dataList.clear();
                    }
                    NotifyListEntity data=entity.getData();
                    dataList.addAll(data.getList());
                    adapter.notifyDataSetChanged();
                    if(loadStatus==1){
                        msf.setRefreshing(false);
                        adapter.setEnableLoadMore(true);
                    }else{
                        if(pageIndex*pageSize>=data.getTotalCount()){
                            adapter.loadMoreEnd();
                        }else{
                            adapter.loadMoreComplete();
                        }
                    }
                }else{
                    if(loadStatus==1){
                        msf.setRefreshing(false);
                        adapter.setEnableLoadMore(true);
                    }else{
                        pageIndex--;
                        adapter.loadMoreFail();
                    }
                    Toast.makeText(NotifyActivity.this,entity.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                if(loadStatus==1){
                    msf.setRefreshing(false);
                    adapter.setEnableLoadMore(true);
                }else{
                    pageIndex--;
                    adapter.loadMoreFail();
                }
                Toast.makeText(NotifyActivity.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }

    @Override
    public void onRefresh() {
        loadStatus=1;
        pageIndex = 1;
        adapter.setEnableLoadMore(false);
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        loadStatus=2;
        pageIndex++;
        getData();
    }

    @Event({R.id.toolbar_btn_back})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finishWithTransition(Transition.RightOut);
                break;
        }
    }
}
