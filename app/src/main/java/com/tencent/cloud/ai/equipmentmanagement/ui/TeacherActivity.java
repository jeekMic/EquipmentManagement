package com.tencent.cloud.ai.equipmentmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.tencent.cloud.ai.equipmentmanagement.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TeacherActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();
    String url = "https://wwww.baidu.com";
    private  OkHttpClient okHttpClient;
    private  Request request;
    private  Call call;
    private TextView tv_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        tv_info = findViewById(R.id.tv_info);

        //OkHttp 三部曲
        okHttpClient = new OkHttpClient();
        request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        call = okHttpClient.newCall(request);
        getData();
    }

    public void getData(){
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getLocalizedMessage()+" "+e.getMessage());
                tv_info.setText("onFailure: http://www.baidu.com");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());

                tv_info.setText("访问百度成功");
            }
        });
    }
}