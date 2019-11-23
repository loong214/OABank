package com.findingdata.oabank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.ProjectCenterListType;
import com.findingdata.oabank.entity.Transition;
import com.findingdata.oabank.receiver.GeTuiIntentService;
import com.findingdata.oabank.utils.AtyTransitionUtil;
import com.findingdata.oabank.utils.LogUtils;
import com.igexin.sdk.PushManager;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.main_tabs_iv_todo)
    private ImageView iv_todo;
    @ViewInject(R.id.main_tabs_iv_doing)
    private ImageView iv_doing;
    @ViewInject(R.id.main_tabs_iv_pause)
    private ImageView iv_pause;
    @ViewInject(R.id.main_tabs_iv_stop)
    private ImageView iv_stop;
    @ViewInject(R.id.main_tabs_tv_todo)
    private TextView tab_todo;
    @ViewInject(R.id.main_tabs_tv_doing)
    private TextView tab_doing;
    @ViewInject(R.id.main_tabs_tv_pause)
    private TextView tab_pause;
    @ViewInject(R.id.main_tabs_tv_stop)
    private TextView tab_stop;


    private Context context;
    private TextView[] tabs = new TextView[4];
    private FragmentManager fragmentManager;
    private Fragment todo, doing, pause, stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context=this;
        fragmentManager = getSupportFragmentManager();
        initTabs();
        setTabSelection(0);
        String cid = PushManager.getInstance().getClientid(this);
        LogUtils.d("cid"+cid);
    }
    private void initTabs() {
        tabs[0] = tab_todo;
        tabs[1] = tab_doing;
        tabs[2] = tab_pause;
        tabs[3] = tab_stop;
    }

    @Event({R.id.main_tabs_ll_todo,R.id.main_tabs_ll_doing,R.id.main_tabs_ll_pause,R.id.main_tabs_ll_stop,
            R.id.main_tv_search,R.id.main_btn_filter,R.id.main_btn_person,R.id.main_btn_notify})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.main_tabs_ll_todo:
                setTabSelection(0);
                break;
            case R.id.main_tabs_ll_doing:
                setTabSelection(1);
                break;
            case R.id.main_tabs_ll_pause:
                setTabSelection(2);
                break;
            case R.id.main_tabs_ll_stop:
                setTabSelection(3);
                break;
            case R.id.main_tv_search:
                Toast.makeText(MainActivity.this,"search",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_btn_filter:
                Toast.makeText(MainActivity.this,"filter",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_btn_person:
                startActivity(PersonActivity.class, Transition.LeftIn);
//                startActivity(new Intent(MainActivity.this,PersonActivity.class));
//                AtyTransitionUtil.enterFromLeft(MainActivity.this);
                break;
            case R.id.main_btn_notify:
                startActivity(NotifyActivity.class,Transition.RightIn);
//                startActivity(new Intent(MainActivity.this,NotifyActivity.class));
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setTabSelection(int index) {

        for (int i = 0; i < tabs.length; i++) {
            if (i == index) {
                tabs[i].setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            } else {
                tabs[i].setTextColor(ContextCompat.getColor(context, R.color.text_desc));
            }
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (todo == null) {
                    todo = ProjectCenterListFragment.newInstance(ProjectCenterListType.Todo);
                    transaction.add(R.id.main_fl_content, todo);
                } else {
                    transaction.show(todo);
                }
                iv_todo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_todo_press));
                iv_doing.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_doing_normal));
                iv_pause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_pause_normal));
                iv_stop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_stop_normal));
                break;
            case 1:
                if (doing == null) {
                    doing = ProjectCenterListFragment.newInstance(ProjectCenterListType.Doing);
                    transaction.add(R.id.main_fl_content, doing);
                } else {
                    transaction.show(doing);
                }
                iv_todo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_todo_normal));
                iv_doing.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_doing_press));
                iv_pause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_pause_normal));
                iv_stop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_stop_normal));
                break;
            case 2:
                if (pause == null) {
                    pause = ProjectCenterListFragment.newInstance(ProjectCenterListType.Pause);
                    transaction.add(R.id.main_fl_content, pause);
                } else {
                    transaction.show(pause);
                }
                iv_todo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_todo_normal));
                iv_doing.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_doing_normal));
                iv_pause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_pause_press));
                iv_stop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_stop_normal));
                break;
            case 3:
                if (stop == null) {
                    stop = ProjectCenterListFragment.newInstance(ProjectCenterListType.Stop);
                    transaction.add(R.id.main_fl_content, stop);
                } else {
                    transaction.show(stop);
                }
                iv_todo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_todo_normal));
                iv_doing.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_doing_normal));
                iv_pause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_pause_normal));
                iv_stop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_stop_press));
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (todo != null) {
            transaction.hide(todo);
        }
        if (doing != null) {
            transaction.hide(doing);
        }
        if (pause != null) {
            transaction.hide(pause);
        }
        if (stop != null) {
            transaction.hide(stop);
        }
    }
}
