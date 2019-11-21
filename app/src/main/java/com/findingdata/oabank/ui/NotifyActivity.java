package com.findingdata.oabank.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.utils.Config;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.XHttp;
import com.findingdata.oabank.weidgt.RecyclerViewDivider;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText("消息中心");
        msf.setOnRefreshListener(this);
        msf.setColorSchemeColors(Color.rgb(47, 223, 189));
        mrv.setLayoutManager(new LinearLayoutManager(this));
        mrv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mrv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                //startActivity(new Intent(NotifyActivity.this, ProjectDetailsActivity.class).putExtra("project_id", dataList.get(position).getProject_id()));
            }
        });
        getData();
    }

    private void getData(){
        Map<String,String> param=new HashMap<>();
        param.put("pageIndex",pageIndex+"");
        param.put("pageSize",pageSize+"");
        XHttp.Get(Config.BASE_URL + "api/v1/base/getAllFile", param, new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
