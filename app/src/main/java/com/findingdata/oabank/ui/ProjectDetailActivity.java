package com.findingdata.oabank.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.utils.PermissionsUtils;
import com.findingdata.oabank.utils.Utils;
import com.findingdata.oabank.weidgt.photopicker.GifSizeFilter;
import com.findingdata.oabank.weidgt.photopicker.GlideEngine;
import com.findingdata.oabank.weidgt.photopicker.PhotoPicker;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Loong on 2019/11/27.
 * Version: 1.0
 * Describe: 项目详情Activity
 */
@ContentView(R.layout.activity_project_detail)
public class ProjectDetailActivity extends BaseActivity {
    private static final String[] permiss={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_CHOOSE=0x001;

    public static final String SD_APP_DIR_NAME = "TestDir"; //存储程序在外部SD卡上的根目录的名字
    public static final String PHOTO_DIR_NAME = "photo";    //存储照片在根目录下的文件夹名字

    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_tv_title;
    @ViewInject(R.id.project_detail_tv_id)
    private TextView project_detail_tv_id;
    @ViewInject(R.id.project_detail_iv_test)
    private ImageView project_detail_iv_test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_tv_title.setText("项目详情");
        project_detail_tv_id.setText(getIntent().getExtras().getInt("project_id")+"");

        PermissionsUtils.getInstance().chekPermissions(this, permiss, new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermissons() {
                LogUtil.d("申请权限通过");
            }

            @Override
            public void forbitPermissons() {
                LogUtil.d("申请权限未通过");
                finish();
            }
        });
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
                String _Path = getPathByUri(_Uri);
                File _File = new File(_Path);
                System.out.println("压缩前图片大小->" + _File.length() / 1024 + "k");
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


    private void compress(List<String> list)
    {
        String _Path = getImagesPath();
        System.out.println("_Path->" + _Path);
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
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        showToast(" 压缩开始前调用，可以在方法内启动 loading UI");
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        showToast(" 压缩成功后调用，返回压缩后的图片文件");
                        Glide.with(ProjectDetailActivity.this).load(file).into(project_detail_iv_test);

                        LogUtil.d("压缩后图片大小->" + file.length() / 1024 + "k");
                        LogUtil.d("getAbsolutePath->" + file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        e.printStackTrace();
                    }
                }).launch();
    }

    private String getImagesPath()
    {
        return createPathIfNotExist("/" + SD_APP_DIR_NAME + "/" + PHOTO_DIR_NAME);
    }
    private String createPathIfNotExist(String pPath)
    {
        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
        if (!sdExist) {
            LogUtil.d("SD卡不存在");
            return null;
        }
        String _AbsolutePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + pPath;
        System.out.println("dbDir->" + _AbsolutePath);
        File dirFile = new File(_AbsolutePath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs())
            {
                LogUtil.d("文件夹创建失败");
                return null;
            }
        }
        return _AbsolutePath;
    }
    /**
     * 通过Uri获取文件路径
     * @param pUri
     * @return
     */
    public String getPathByUri(Uri pUri)
    {
//      pUri.getPath()
//      拍照后输出：  /mq_external_cache/storage/emulated/0/Pictures/JPEG_20190326_225011.jpg
//      选择照片后的输出：  /external/images/media/52325
        String _Path = pUri.getPath();

        if (_Path.endsWith(".jpg"))
        {
            System.out.println("path-->" + subPath(_Path));
            return subPath(_Path);
        }
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(pUri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }


    private String subPath(String pPath)
    {
        String[] array = pPath.split("/");
        return pPath.substring(array[1].length() + 1, pPath.length());
    }
}
