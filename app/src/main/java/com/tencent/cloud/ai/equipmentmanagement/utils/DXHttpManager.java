package com.tencent.cloud.ai.equipmentmanagement.utils;

import android.nfc.Tag;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.os.Handler;

import com.tencent.cloud.ai.equipmentmanagement.listener.DXHttpListener;
import com.tencent.cloud.ai.equipmentmanagement.model.RequestBean;
import com.tencent.cloud.ai.equipmentmanagement.model.RequestParams;


public class DXHttpManager {

    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient = null;
    private static OkHttpClient.Builder okHttpClientBuilder;
    private Handler mDeliveryHandler;
    private Request build;

    //单例设计
    private static DXHttpManager mInstance;
    private DXHttpManager() {
        okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS); //链接超时时间
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);   //写入超时时间
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);    //读取超时时间
        okHttpClientBuilder.followRedirects(true);                      //设置重定向 其实默认也是true
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());    //初始化handler
        mOkHttpClient = okHttpClientBuilder.build();                    //初始化okHttpClient
    }
    public static DXHttpManager getmInstance() {
        if (mInstance == null) {
            synchronized (DXHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new DXHttpManager();
                }
            }
        }
        return mInstance;
    }
    /**
     * 发送get请求，带参数直接传入parma，不带参数直接传null
     */
    public void sendGetRequest(String url, RequestParams params, final int tag, final DXHttpListener listener) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                // 将请求参数逐一添加到请求体中
                urlBuilder.append(entry.getKey()).append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }
        Request request = new Request.Builder()
                .url(urlBuilder.substring(0, urlBuilder.length() - 1)) //要把最后的&符号去掉
                .get()
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliveryFailure(call,e,listener,tag);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                deliverySuccessRequest(call,str,listener,tag);
            }
        });

    }

    /**
     * 发送post请求，带参数直接传入parma
     */
    public void sendPostRequest(String  url, RequestParams params, final int tag, final DXHttpListener listener){
        FormBody.Builder mFormBodybuilder = new FormBody.Builder();
        if(params!=null){
            for(Map.Entry<String,String> entry: params.urlParams.entrySet()){
                // 将请求参数逐一添加到请求体中
                mFormBodybuilder.add(entry.getKey(),entry.getValue());
            }
        }
        FormBody mFormBody=mFormBodybuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(mFormBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliveryFailure(call,e,listener,tag);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                deliverySuccessRequest(call,str,listener,tag);
            }
        });
    }

    //请求成功，讲回调切换到主线程
    private  void deliverySuccessRequest(Call call, final String str, final DXHttpListener listener, final int tag) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
//                    String str = response.body().string();
                    listener.onSuccess(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //请求失败，讲回调切换到主线程
    private  void deliveryFailure(Call call, final IOException e, final DXHttpListener listener, final int tag) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailed(tag,e);
            }
        });
    }

    /**
     * 文件上传请求
     * @return
     */
    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");
    public void createMultiPostRequest(String url, RequestParams params, final DXHttpListener listener, final int tag) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {

                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, (String) entry.getValue()));
                }
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliveryFailure(call,e,listener,tag);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                deliverySuccessRequest(call,str,listener,tag);
            }
        });
    }

}
