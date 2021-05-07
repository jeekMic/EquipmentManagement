package com.tencent.cloud.ai.equipmentmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.cloud.ai.equipmentmanagement.R;
import com.tencent.cloud.ai.equipmentmanagement.base.BaseActivity;
import com.tencent.cloud.ai.equipmentmanagement.custom.TimeTableView;
import com.tencent.cloud.ai.equipmentmanagement.listener.ClassListener;
import com.tencent.cloud.ai.equipmentmanagement.model.ClassInfo;
import com.tencent.cloud.ai.equipmentmanagement.model.TimeTableModel;
import com.tencent.cloud.ai.equipmentmanagement.utils.TestString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TeacherActivity extends BaseActivity implements View.OnClickListener, ClassListener {

    public final String TAG = this.getClass().getSimpleName();
    String url = "https://wwww.baidu.com";
    private  OkHttpClient okHttpClient;
    private  Request request;
    private  Call call;
    private Button addclass, use_require;
    private ScrollView t_main_scroview;
    private LinearLayout t_sy_application,ll_class;
    private List<TimeTableModel> mList;
    private TimeTableView mTimaTableView;

    @Override
    public void myOnCreate() {
        setContentView(R.layout.activity_teacher);
        mList = new ArrayList<>();
        initView();

        //OkHttp 三部曲
        okHttpClient = new OkHttpClient();
        request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        call = okHttpClient.newCall(request);
        getData();
        Toast.makeText(this, "排课", Toast.LENGTH_SHORT).show();



        mTimaTableView.setListener(this);
    }

    public void initView(){
        addclass = findViewById(R.id.add_class);
        use_require = findViewById(R.id.use_require);
        mTimaTableView = (TimeTableView) findViewById(R.id.t_main_timetable_ly);
        t_main_scroview = findViewById(R.id.t_main_scrollview);
        t_sy_application = findViewById(R.id.t_sy_application);
        ll_class = findViewById(R.id.ll_class);

        addclass.setOnClickListener(this);
       // addList();
        use_require = findViewById(R.id.use_require);
        use_require.setOnClickListener(this);
    }

    public void getData(){
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getLocalizedMessage()+" "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //这里模拟访问数据成功
                Log.d(TAG, "onResponse: " + response.body().string());
                String result = TestString.classInfo;//模拟数据 真实数据为：response.body().string()
                Log.e("hb==: ",result);
                Gson gson  = new Gson();
                ClassInfo info = gson.fromJson(result, ClassInfo.class);
                for (int i=0;i<info.getCourse().size();i++){
                    mList.add(new TimeTableModel(info.getCourse().get(i)));
                }
                mTimaTableView.setTimeTable(mList);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_class:
                Toast.makeText(this, "排课", Toast.LENGTH_SHORT).show();
                t_main_scroview.setVisibility(View.VISIBLE);
                ll_class.setVisibility(View.VISIBLE);
                t_sy_application.setVisibility(View.GONE);
                break;
            case R.id.use_require:
                Toast.makeText(this, "耗材申请", Toast.LENGTH_SHORT).show();
                t_main_scroview.setVisibility(View.GONE);
                ll_class.setVisibility(View.GONE);
                t_sy_application.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()){
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert mInputMethodManager != null;
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
        }
        return super.onTouchEvent(event);
    }

    private void addList() {
        mList.add(new TimeTableModel(0, 1, 1, 2, "8:20", "10:10", "test1",
                "王老师", "1", "2-13"));
    }

    /**
     * 空白部分的点击事件，在这里添加课程
     * @param week
     * @param clum
     */
    @Override
    public void onSelectClass(int week, int clum) {
        mList.add(new TimeTableModel(0, clum, clum, week, "8:20", "10:10", "test1",
                "李老师", "2", "2-13"));

        Log.d(TAG, "onSelectClass: "+week+" "+clum);
        mTimaTableView.removeAllViews();
        mTimaTableView.setTimeTable(mList);
    }
}