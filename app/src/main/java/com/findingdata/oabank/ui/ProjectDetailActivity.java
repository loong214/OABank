package com.findingdata.oabank.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import androidx.annotation.Nullable;

/**
 * Created by Loong on 2019/11/27.
 * Version: 1.0
 * Describe: 项目详情Activity
 */
@ContentView(R.layout.activity_project_detail)
public class ProjectDetailActivity extends BaseActivity {
    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_tv_title;
    @ViewInject(R.id.project_detail_tv_id)
    private TextView project_detail_tv_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_tv_title.setText("项目详情");
        project_detail_tv_id.setText(getIntent().getExtras().getString("project_id"));
    }

    @Event({R.id.toolbar_btn_back})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
        }
    }
}
