package com.findingdata.oabank.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findingdata.oabank.entity.Transition;
import com.findingdata.oabank.utils.AtyTransitionUtil;
import com.findingdata.oabank.weidgt.ProgressDialogView;

import org.xutils.x;

import androidx.fragment.app.Fragment;

import static com.findingdata.oabank.FDApplication.activityTrans;

/**
 * Created by zengx on 2019/11/17.
 * Describe: Fragment基础类
 */
public class BaseFragment extends Fragment {
    private Context context;// 上下文
    private boolean injected = false;
    private ProgressDialogView progressDialogView = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    /**
     * 启动新的Activity 默认Trans-RightIn
     * @param clazz
     */
    public void startActivity(Class clazz){
        Transition transition=Transition.RightIn;
        activityTrans.put(clazz,transition);
        startActivity(new Intent(getActivity(), clazz));
        executeTransition(transition);
    }

    /**
     * 启动新的Activity 默认Trans-RightIn
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class clazz,Bundle bundle){
        Transition transition=Transition.RightIn;
        activityTrans.put(clazz,transition);
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz
     * @param transition  转场动画
     */
    public void startActivity(Class clazz, Transition transition){
        activityTrans.put(clazz,transition);
        startActivity(new Intent(getActivity(), clazz));
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz
     * @param bundle
     * @param transition 转场动画
     */
    public void startActivity(Class clazz, Bundle bundle,Transition transition){
        activityTrans.put(clazz,transition);
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz
     * @param bundle
     * @param requestCode
     * @param transition 转场动画
     */
    public void startActivityForResult( Class clazz, Bundle bundle, int requestCode,Transition transition) {
        activityTrans.put(clazz,transition);
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        executeTransition(transition);
    }

    //执行转场
    private void executeTransition(Transition transition){
        switch (transition){
            case TopIn:
                AtyTransitionUtil.enterFromTop(getActivity());
                break;
            case TopOut:
                AtyTransitionUtil.exitToTop(getActivity());
                break;
            case LeftIn:
                AtyTransitionUtil.enterFromLeft(getActivity());
                break;
            case LeftOut:
                AtyTransitionUtil.exitToLeft(getActivity());
                break;
            case BottomIn:
                AtyTransitionUtil.enterFromBottom(getActivity());
                break;
            case BottomOut:
                AtyTransitionUtil.exitToBottom(getActivity());
                break;
            case RightIn:
                AtyTransitionUtil.enterFromRight(getActivity());
                break;
            case RightOut:
                AtyTransitionUtil.exitToRight(getActivity());
                break;
        }
    }


    //启动加载框
    protected void startProgressDialog() {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(context, "",false);
    }
    //启动加载框
    protected void startProgressDialog(String msg) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(context, msg,false);
    }
    //启动加载框
    protected void startProgressDialog(boolean cancelable) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(context, "",cancelable);
    }
    //启动加载框
    protected void startProgressDialog(String msg,boolean cancelable) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(context, msg,cancelable);
    }
    //关闭加载框
    protected void stopProgressDialog() {
        if (progressDialogView != null) {
            progressDialogView.stopLoad();
        }
    }
}