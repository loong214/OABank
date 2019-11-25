package com.findingdata.oabank.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import androidx.annotation.Nullable;


/**
 * Created by Loong on 2019/11/25.
 * Version: 1.0
 * Describe: 自定义NavigationView
 */
@ContentView(R.layout.navigation_view)
public class NavigationViewFragment extends BaseFragment {
    @ViewInject(R.id.nav_tv_text)
    private TextView nav_tv_text;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
