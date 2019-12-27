package com.findingdata.oabank.utils.http;

import android.util.Log;

import com.findingdata.oabank.entity.TokenEntity;
import com.findingdata.oabank.utils.SharedPreferencesManage;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.Date;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: XUtils 请求回调
 */
public class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType> {

    @Override
    public void onSuccess(ResultType result) {
        Log.e("XUtil","onSuccess");
        try {
            JSONObject jsonObject=new JSONObject(result.toString());
            if(jsonObject.getBoolean("Status")){
                TokenEntity tokenEntity=SharedPreferencesManage.getToken();
                if(tokenEntity!=null){
                    tokenEntity.setExpireTime(new Date().getTime()+10*60*1000-2000);
                    SharedPreferencesManage.setToken(tokenEntity);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        Log.e("XUtil","onError:"+ex.getMessage());
    }

    @Override
    public void onCancelled(CancelledException cex) {
        Log.e("XUtil","onCancelled");
    }

    @Override
    public void onFinished() {
        Log.e("XUtil","onFinish");
    }


}
