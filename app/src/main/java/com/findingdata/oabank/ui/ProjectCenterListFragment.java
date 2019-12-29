package com.findingdata.oabank.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.ProjectListAdapter;
import com.findingdata.oabank.base.BaseFragment;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.EventBusMessage;
import com.findingdata.oabank.entity.FilterValueEntity;
import com.findingdata.oabank.entity.ProjectCenterListType;
import com.findingdata.oabank.entity.ProjectInfo;
import com.findingdata.oabank.entity.ProjectList;
import com.findingdata.oabank.entity.QueryItem;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.SharedPreferencesManage;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.weidgt.RecyclerViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.findingdata.oabank.base.BaseHandler.HTTP_REQUEST;
import static com.findingdata.oabank.utils.Config.BASE_URL;

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
        bundle.putInt(ARG, type.getType());
        fragment.setArguments(bundle);
        return fragment;
    }


    @ViewInject(R.id.project_center_srl_fresh)
    private SwipeRefreshLayout msf;
    @ViewInject(R.id.project_center_rl_list)
    private RecyclerView mrv;

    private int pageIndex = 1;
    private int pageSize = 5;
    private List<ProjectInfo> dataList=new ArrayList<>();
    private ProjectListAdapter adapter;
    private int loadStatus=2;//1为刷新，2为加载更多

    private Context context;
    private int listType;

    private List<FilterValueEntity> filterValue;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        listType = getArguments().getInt(ARG);
        //初始化项目过滤的值
        filterValue=SharedPreferencesManage.getFilterValueEntity();
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
                bundle.putInt("project_id",dataList.get(position).getPROJECT_ID());
                startActivity(ProjectDetailActivity.class,bundle);
            }
        });

        //初始化adapter
        adapter = new ProjectListAdapter(R.layout.item_project_list, dataList);
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
        param.put("page_num",pageIndex);
        param.put("page_size",pageSize);
        List<QueryItem> queryItemList=new ArrayList<>();
        List<Integer> value=new ArrayList<>();
        value.add(listType);
        queryItemList.add(new QueryItem("1026",new ArrayList<String>()));
        queryItemList.add(new QueryItem("1012",value));
        param.put("query_item_list",queryItemList);
        Message message=new Message();
        message.what=HTTP_REQUEST;
        Bundle bundle=new Bundle();
        RequestParam requestParam=new RequestParam<>(BASE_URL+"/api/Project/ProjectList", HttpMethod.PostJson,null,param,new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<ProjectList> entity= JsonParse.parse(result,ProjectList.class);
                if(entity.isStatus()){
                    if(loadStatus==1){
                        dataList.clear();
                    }
                    dataList.addAll(entity.getResult().getData());
                    adapter.notifyDataSetChanged();
                    if(loadStatus==1){
                        msf.setRefreshing(false);
                        adapter.setEnableLoadMore(true);
                    }else{
                        if(pageIndex*pageSize>=entity.getResult().getData_count()){
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
                    showToast(entity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });
        bundle.putSerializable("request",requestParam);
        message.setData(bundle);
        handler.sendMessage(message);
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
