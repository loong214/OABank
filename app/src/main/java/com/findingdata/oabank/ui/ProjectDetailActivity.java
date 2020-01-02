package com.findingdata.oabank.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.ImagePerviewListAdapter;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.ImageViewInfo;
import com.findingdata.oabank.entity.ProjectEntity;
import com.findingdata.oabank.utils.FilePathUtil;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.PermissionsUtils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.weidgt.NoUnderlineSpan;
import com.findingdata.oabank.weidgt.imagepreview.PreviewBuilder;
import com.findingdata.oabank.weidgt.photopicker.PhotoPicker;
import com.zhihu.matisse.Matisse;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.findingdata.oabank.base.Config.BASE_URL;
import static com.findingdata.oabank.base.Config.PHOTO_DIR_NAME;
import static com.findingdata.oabank.base.Config.SD_APP_DIR_NAME;

/**
 * Created by Loong on 2019/11/27.
 * Version: 1.0
 * Describe: 项目详情Activity
 */
@ContentView(R.layout.activity_project_detail)
public class ProjectDetailActivity extends BaseActivity {
    private static final String[] permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_CHOOSE=0x001;

    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_tv_title;
    @ViewInject(R.id.project_detail_tv_id)
    private TextView project_detail_tv_id;
    @ViewInject(R.id.project_detail_tv_load_type)
    private TextView project_detail_tv_load_type;
    @ViewInject(R.id.project_detail_tv_load_price)
    private TextView project_detail_tv_load_price;
    @ViewInject(R.id.project_detail_tv_loader)
    private TextView project_detail_tv_loader;
    @ViewInject(R.id.project_detail_tv_loader_tel)
    private TextView project_detail_tv_loader_tel;
    @ViewInject(R.id.project_detail_tv_client)
    private TextView project_detail_tv_client;
    @ViewInject(R.id.project_detail_tv_client_tel)
    private TextView project_detail_tv_client_tel;
    @ViewInject(R.id.project_detail_tv_manager)
    private TextView project_detail_tv_manager;
    @ViewInject(R.id.project_detail_tv_manager_tel)
    private TextView project_detail_tv_manager_tel;





    @ViewInject(R.id.rlv_image)
    private RecyclerView rlv_image;

    List<ImageViewInfo> dataList=new ArrayList<>();
    private ImagePerviewListAdapter adapter;
    private GridLayoutManager mGridLayoutManager;
    private int project_id;
    private ProjectEntity projectEntity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_tv_title.setText("项目详情");
        project_id=getIntent().getExtras().getInt("project_id");
        LogUtil.d(getIntent().getExtras().getInt("project_id")+"");

        PermissionsUtils.getInstance().checkPermissions(this, permission, new PermissionsUtils.IPermissionsResult() {
            @Override
            public void success() {
                LogUtil.d("申请权限通过");
            }

            @Override
            public void fail() {
                LogUtil.d("申请权限未通过");
                finish();
            }
        });

        rlv_image.setLayoutManager(mGridLayoutManager=new GridLayoutManager(this,3));
        adapter=new ImagePerviewListAdapter(this,dataList);
        rlv_image.setAdapter(adapter);
        rlv_image.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                computeBoundsBackward(mGridLayoutManager.findFirstVisibleItemPosition());
                PreviewBuilder.from(ProjectDetailActivity.this)
                        .setImgs(adapter.getData())
                        .setCurrentIndex(position)
                        .setSingleFling(true)
                        .setType(PreviewBuilder.IndicatorType.Number)
                        .start();
            }
        });
        getData();
    }

    private void initView(){
        NoUnderlineSpan mNoUnderlineSpan = new NoUnderlineSpan();
        project_detail_tv_id.setText(projectEntity.getPROJECT_ID()+"");
        project_detail_tv_load_type.setText(projectEntity.getLOAN_TYPE_CHS());
        project_detail_tv_load_price.setText(projectEntity.getLOAN_AMOUNT()+"");
        project_detail_tv_loader.setText(projectEntity.getBORROWER());
        if (!TextUtils.isEmpty(projectEntity.getBORROWER_PHONE())) {
            project_detail_tv_loader_tel.setText(projectEntity.getBORROWER_PHONE());
        } else {
            project_detail_tv_loader_tel.setVisibility(View.GONE);
        }
        if (project_detail_tv_loader_tel.getText() instanceof Spannable) {
            Spannable s = (Spannable) project_detail_tv_loader_tel.getText();
            s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
        }
        project_detail_tv_client.setText(projectEntity.getCONTACT_PERSON());
        if (!TextUtils.isEmpty(projectEntity.getCONTACT_PHONE())) {
            project_detail_tv_client_tel.setText(projectEntity.getCONTACT_PHONE());
        } else {
            project_detail_tv_client_tel.setVisibility(View.GONE);
        }
        if (project_detail_tv_client_tel.getText() instanceof Spannable) {
            Spannable s = (Spannable) project_detail_tv_client_tel.getText();
            s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
        }
        project_detail_tv_manager.setText(projectEntity.getBCM_NAME());
        if (!TextUtils.isEmpty(projectEntity.getBCM_PHONE())) {
            project_detail_tv_manager_tel.setText(projectEntity.getBCM_PHONE());
        } else {
            project_detail_tv_manager_tel.setVisibility(View.GONE);
        }
        if (project_detail_tv_manager_tel.getText() instanceof Spannable) {
            Spannable s = (Spannable) project_detail_tv_manager_tel.getText();
            s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
        }

    }

    private void getData(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,String> requestMap=new HashMap<>();
        requestMap.put("project_id",project_id+"");
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<ProjectEntity> entity= JsonParse.parse(result,ProjectEntity.class);
                if(entity.isStatus()){
                    projectEntity=entity.getResult();
                    initView();
                }else{
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
        sendRequsest(requestParam,true);
    }

    //计算返回的边界
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < adapter.getItemCount(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView imageView = itemView.findViewById(R.id.iiv_item_image_preview);
                imageView.getGlobalVisibleRect(bounds);
            }
            adapter.getItem(i).setBounds(bounds);
        }
    }

    @Event({R.id.toolbar_btn_back,R.id.project_detail_tv_test1,R.id.project_detail_tv_test2,R.id.project_detail_tv_test3})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.project_detail_tv_test1:
                PhotoPicker.pick(this,REQUEST_CODE_CHOOSE);
                break;
            case R.id.project_detail_tv_test2:
                PhotoPicker.pick(this,10,REQUEST_CODE_CHOOSE);
                break;
            case R.id.project_detail_tv_test3:
                PhotoPicker.pick(this,10,true,REQUEST_CODE_CHOOSE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CHOOSE&&resultCode==RESULT_OK){
            //图片路径 同样视频地址也是这个 根据requestCode
            List<Uri> pathList = Matisse.obtainResult(data);
            List<String> _List = new ArrayList<>();
            for (Uri _Uri : pathList)
            {
                String _Path = FilePathUtil.getPathByUri(this,_Uri);
                File _File = new File(_Path);
                LogUtil.d("压缩前图片大小->" + _File.length() / 1024 + "k");
                _List.add(_Path);
            }
            compress(_List);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    //压缩图片
    private void compress(List<String> list){
        String _Path = FilePathUtil.createPathIfNotExist("/" + SD_APP_DIR_NAME + "/" + PHOTO_DIR_NAME);
        LogUtil.d("_Path->" + _Path);
        Luban.with(this)
                .load(list)
                .ignoreBy(100)
                .setTargetDir(_Path)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LogUtil.d(" 压缩开始前调用，可以在方法内启动 loading UI");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtil.d(" 压缩成功后调用，返回压缩后的图片文件");
                        dataList.add(new ImageViewInfo(file.getAbsolutePath()));
                        adapter.notifyDataSetChanged();
                        LogUtil.d("压缩后图片大小->" + file.length() / 1024 + "k");
                        LogUtil.d("getAbsolutePath->" + file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }).launch();
    }
}
