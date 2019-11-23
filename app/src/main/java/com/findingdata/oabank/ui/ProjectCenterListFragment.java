package com.findingdata.oabank.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseFragment;
import com.findingdata.oabank.entity.ProjectCenterListType;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: 项目列表分类Fragment
 */
@ContentView(R.layout.fragment_project_center_list)
public class ProjectCenterListFragment extends BaseFragment {
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


    @ViewInject(R.id.project_center_tv_type)
    private TextView project_center_tv_type;

    private Context context;
    private String listType;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        listType = getArguments().getString(ARG);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        project_center_tv_type.setText(listType);
    }
}
