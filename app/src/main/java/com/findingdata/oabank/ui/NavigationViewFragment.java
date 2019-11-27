package com.findingdata.oabank.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.FilterListAdapter;
import com.findingdata.oabank.base.BaseFragment;
import com.findingdata.oabank.entity.City;
import com.findingdata.oabank.entity.EventBusMessage;
import com.findingdata.oabank.entity.FilterTypeEntity;
import com.findingdata.oabank.entity.FilterValueEntity;
import com.findingdata.oabank.entity.LoanType;
import com.findingdata.oabank.entity.ObjectType;
import com.findingdata.oabank.utils.SharedPreferencesManage;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Loong on 2019/11/25.
 * Version: 1.0
 * Describe: 自定义NavigationView
 */
@ContentView(R.layout.navigation_view)
public class NavigationViewFragment extends BaseFragment {
    @ViewInject(R.id.nav_rlv_loan)
    private RecyclerView nav_rlv_loan;
    @ViewInject(R.id.nav_rlv_type)
    private RecyclerView nav_rlv_type;
    @ViewInject(R.id.nav_rlv_city)
    private RecyclerView nav_rlv_city;

    private List<FilterTypeEntity> loanData=new ArrayList<>();
    private FilterListAdapter loanAdapter;
    private List<FilterTypeEntity> typeData=new ArrayList<>();
    private FilterListAdapter typeAdapter;
    private List<FilterTypeEntity> cityData=new ArrayList<>();
    private FilterListAdapter cityAdapter;
    private FilterValueEntity filterValue;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        filterValue=SharedPreferencesManage.getFilterValueEntity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nav_rlv_loan.setLayoutManager(new GridLayoutManager(context,3));
        //注册RecyclerView点击
        nav_rlv_loan.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                refrreshData(0,position);
            }
        });

        loanAdapter=new FilterListAdapter(context,R.layout.item_filter_list,loanData);
        nav_rlv_loan.setAdapter(loanAdapter);

        nav_rlv_type.setLayoutManager(new GridLayoutManager(context,3));
        //注册RecyclerView点击
        nav_rlv_type.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                refrreshData(1,position);
            }
        });

        typeAdapter=new FilterListAdapter(context,R.layout.item_filter_list,typeData);
        nav_rlv_type.setAdapter(typeAdapter);

        nav_rlv_city.setLayoutManager(new GridLayoutManager(context,3));
        //注册RecyclerView点击
        nav_rlv_city.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                refrreshData(2,position);
            }
        });

        cityAdapter=new FilterListAdapter(context,R.layout.item_filter_list,cityData);
        nav_rlv_city.setAdapter(cityAdapter);
        intData();
    }
    //初始化数据
    private void intData(){
        loanData.clear();
        for(LoanType type:LoanType.values()){
            if(type.getType().equals(filterValue.getLoanValue())){
                loanData.add(new FilterTypeEntity(type.getType(),type.getChs(),true));
            }else{
                loanData.add(new FilterTypeEntity(type.getType(),type.getChs(),false));
            }
        }
        loanAdapter.notifyDataSetChanged();

        typeData.clear();
        for(ObjectType type:ObjectType.values()){
            if(type.getType().equals(filterValue.getTypeValue())){
                typeData.add(new FilterTypeEntity(type.getType(),type.getChs(),true));
            }else{
                typeData.add(new FilterTypeEntity(type.getType(),type.getChs(),false));
            }
        }
        typeAdapter.notifyDataSetChanged();

        cityData.clear();
        for(City type:City.values()){
            if(type.getType().equals(filterValue.getCityValue())){
                cityData.add(new FilterTypeEntity(type.getType(),type.getChs(),true));
            }else{
                cityData.add(new FilterTypeEntity(type.getType(),type.getChs(),false));
            }
        }
        cityAdapter.notifyDataSetChanged();
    }
    //刷新项目过滤
    private void refrreshData(int type,int position){
        switch (type){
            case 0:
                if(!loanData.get(position).isCheck()){
                    loanData.get(position).setCheck(true);
                    for (int i = 0; i < loanData.size(); i++) {
                        if(i!=position&&loanData.get(i).isCheck()){
                            loanData.get(i).setCheck(false);
                        }
                    }
                    loanAdapter.notifyDataSetChanged();
                }
                break;
            case 1:
                if(!typeData.get(position).isCheck()){
                    typeData.get(position).setCheck(true);
                    for (int i = 0; i < typeData.size(); i++) {
                        if(i!=position&&typeData.get(i).isCheck()){
                            typeData.get(i).setCheck(false);
                        }
                    }
                    typeAdapter.notifyDataSetChanged();
                }
                break;
            case 2:
                if(!cityData.get(position).isCheck()){
                    cityData.get(position).setCheck(true);
                    for (int i = 0; i < cityData.size(); i++) {
                        if(i!=position&&cityData.get(i).isCheck()){
                            cityData.get(i).setCheck(false);
                        }
                    }
                    cityAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
    //获取项目过滤选择的值
    private FilterValueEntity getFilterValueEntity(){
        FilterValueEntity filterValueEntity=new FilterValueEntity("0","0","0");
        for (int i = 0; i < loanData.size(); i++) {
            if(loanData.get(i).isCheck()){
                filterValueEntity.setLoanValue(loanData.get(i).getType());
            }
        }
        for (int i = 0; i < typeData.size(); i++) {
            if(typeData.get(i).isCheck()){
                filterValueEntity.setTypeValue(typeData.get(i).getType());
            }
        }
        for (int i = 0; i < cityData.size(); i++) {
            if(cityData.get(i).isCheck()){
                filterValueEntity.setCityValue(cityData.get(i).getType());
            }
        }
        return filterValueEntity;
    }

    @Event({R.id.nav_tv_cancel,R.id.nav_tv_submit})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.nav_tv_cancel:
                //通知Main页面关闭项目过滤抽屉
                EventBus.getDefault().post(new EventBusMessage<>("NavClose"));
                intData();
                break;
            case R.id.nav_tv_submit:
                filterValue=getFilterValueEntity();
                //通知Main页面关闭项目过滤抽屉
                EventBus.getDefault().post(new EventBusMessage<>("NavClose"));
                //存储项目过滤的值
                SharedPreferencesManage.setFilterValueEntity(filterValue);
                //通知列表页面刷新
                EventBus.getDefault().post(new EventBusMessage<>("ProjectFilter"));
                break;
        }
    }
}
