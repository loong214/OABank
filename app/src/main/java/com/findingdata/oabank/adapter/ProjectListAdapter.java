package com.findingdata.oabank.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.NotifyEntity;
import com.findingdata.oabank.entity.ProjectEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Loong on 2019/11/22.
 * Version: 1.0
 * Describe: 消息列表适配器
 */
public class ProjectListAdapter extends BaseQuickAdapter<ProjectEntity, BaseViewHolder> {
    public ProjectListAdapter(int layoutResId, @Nullable List<ProjectEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProjectEntity item) {
        helper.setText(R.id.notify_list_name,item.getP_name());
        helper.setText(R.id.notify_list_time,item.getP_create_time());
        helper.setText(R.id.notify_list_content,item.getP_client()+" "+item.getP_price()+"万");
    }
}
