package com.findingdata.oabank.ui;

import android.content.Context;
import android.content.Intent;
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
import com.findingdata.oabank.adapter.ProjectListAdapter;
import com.findingdata.oabank.base.BaseFragment;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.EventBusMessage;
import com.findingdata.oabank.entity.FilterValueEntity;
import com.findingdata.oabank.entity.NotifyEntity;
import com.findingdata.oabank.entity.NotifyListEntity;
import com.findingdata.oabank.entity.ProjectCenterListType;
import com.findingdata.oabank.entity.ProjectEntity;
import com.findingdata.oabank.entity.ProjectList;
import com.findingdata.oabank.entity.ProjectListEntity;
import com.findingdata.oabank.entity.UserInfo;
import com.findingdata.oabank.utils.Config;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.SharedPreferencesManage;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.XHttp;
import com.findingdata.oabank.weidgt.RecyclerViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: 项目列表分类Fragment
 */
@ContentView(R.layout.fragment_project_center_list)
public class ProjectCenterListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String ARG = "listType";

    public ProjectCenterListFragment() {
    }

    public static Fragment newInstance(ProjectCenterListType type) {
        ProjectCenterListFragment fragment = new ProjectCenterListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG, type.getType());
        fragment.setArguments(bundle);
        return fragment;
    }


    @ViewInject(R.id.project_center_srl_fresh)
    private SwipeRefreshLayout msf;
    @ViewInject(R.id.project_center_rl_list)
    private RecyclerView mrv;

    private int pageIndex = 1;
    private int pageSize = 10;
    private List<ProjectEntity> dataList=new ArrayList<>();
    private ProjectListAdapter adapter;
    private int loadStatus=2;//1为刷新，2为加载更多

    private Context context;
    private String listType;

    private FilterValueEntity filterValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        listType = getArguments().getString(ARG);
        //初始化项目过滤的值
        filterValue=SharedPreferencesManage.getFilterValueEntity();
        LogUtils.d(filterValue);
        //注册监听
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //注册下拉刷新
        msf.setOnRefreshListener(this);
        //设置圈圈的颜色
        msf.setColorSchemeColors(Color.rgb(47, 223, 189));

        mrv.setLayoutManager(new LinearLayoutManager(context));
        mrv.addItemDecoration(new RecyclerViewDivider(context, LinearLayoutManager.HORIZONTAL));
        //注册RecyclerView点击
        mrv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("project_id",dataList.get(position).getP_id());
                startActivity(ProjectDetailActivity.class,bundle);
            }
        });

        //初始化adapter
        adapter = new ProjectListAdapter(R.layout.item_notify_list, dataList);
        adapter.setOnLoadMoreListener(this,mrv);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setLoadMoreView(new SimpleLoadMoreView());

        mrv.setAdapter(adapter);
        getData();
    }

    /**
     * 获取项目列表
     */
    private void getData(){
        startProgressDialog();
        Map<String,Object> param=new HashMap<>();
        param.put("customer_id",SharedPreferencesManage.getUserInfo().getCustomerId());
        param.put("is_all_custmer",true);
        param.put("query_item_list",new ArrayList<>());
        param.put("page_num",pageIndex);
        param.put("page_size",pageSize);
        param.put("order_by","");

        XHttp.Post(Config.BASE_URL + "/api/Project/ProjectList", param, new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<ProjectList> entity= JsonParse.parse(result,ProjectList.class);
                if(entity.isStatus()){
                    LogUtils.d("result",entity.getResult().getData().size()+"_");
                }else{
                    Toast.makeText(context,entity.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                Toast.makeText(context,ex.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });
    }

    /**
     * 获取后台数据
     */
    private void getDataBak(){
        Map<String,String> param=new HashMap<>();
        param.put("status",listType);
        param.put("pageIndex",pageIndex+"");
        param.put("pageSize",pageSize+"");
        XHttp.Get(Config.BASE_URL + "api/v1/project/getProjectList", param, new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<ProjectListEntity> entity= JsonParse.parse(result,ProjectListEntity.class);
                if(entity.isStatus()){
                    if(loadStatus==1){
                        dataList.clear();
                    }
                    ProjectListEntity data=entity.getResult();
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
                    Toast.makeText(context,entity.getMessage(),Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context,ex.getMessage(),Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("ProjectFilter".equals(message.getMessage())){
            filterValue = SharedPreferencesManage.getFilterValueEntity();
            LogUtils.d(filterValue);
        }
    }

}
