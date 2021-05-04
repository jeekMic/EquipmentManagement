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

import com.tencent.cloud.ai.equipmentmanagement.R;
import com.tencent.cloud.ai.equipmentmanagement.base.BaseActivity;
import com.tencent.cloud.ai.equipmentmanagement.custom.TimeTableView;
import com.tencent.cloud.ai.equipmentmanagement.listener.ClassListener;
import com.tencent.cloud.ai.equipmentmanagement.model.TimeTableModel;

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
    private LinearLayout t_sy_application;
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


        mTimaTableView.setTimeTable(mList);
        mTimaTableView.setListener(this);
    }

    public void initView(){
        addclass = findViewById(R.id.add_class);
        use_require = findViewById(R.id.use_require);
        mTimaTableView = (TimeTableView) findViewById(R.id.t_main_timetable_ly);
        t_main_scroview = findViewById(R.id.t_main_scrollview);
        t_sy_application = findViewById(R.id.t_sy_application);

        addclass.setOnClickListener(this);
        addList();
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
                Log.d(TAG, "onResponse: " + response.body().string());

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_class:
                Toast.makeText(this, "排课", Toast.LENGTH_SHORT).show();
                t_main_scroview.setVisibility(View.VISIBLE);
                t_sy_application.setVisibility(View.GONE);
                break;
            case R.id.use_require:
                Toast.makeText(this, "耗材申请", Toast.LENGTH_SHORT).show();
                t_main_scroview.setVisibility(View.GONE);
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
        mList.add(new TimeTableModel(0, 1, 2, 2, "8:20", "10:10", "test1",
                "王老师", "1", "2-13"));
//        mList.add(new TimeTableModel(0, 3, 4, 1, "8:20", "10:10", "test1",
//                "李老师", "2", "2-13"));
//        mList.add(new TimeTableModel(0, 6, 7, 1, "8:20", "10:10", "test1",
//                "王", "3", "2-13"));
//
//
//        mList.add(new TimeTableModel(0, 6, 7, 2, "8:20", "10:10", "test1",
//                "老师1", "4", "2-13"));
//        mList.add(new TimeTableModel(0, 8, 9, 2, "8:20", "10:10", "test1",
//                "老师2", "5", "2-13"));
//
//        mList.add(new TimeTableModel(0, 1, 2, 3, "8:20", "10:10", "test1",
//                "老师3", "6", "2-13"));
//
//        mList.add(new TimeTableModel(0, 6, 7, 3, "8:20", "10:10", "test1",
//                "老师4", "7", "2-13"));
//        mList.add(new TimeTableModel(0, 8, 9, 4, "8:20", "10:10", "test1",
//                "老师5", "9", "2-13"));
//        mList.add(new TimeTableModel(0, 3, 5, 4, "8:20", "10:10", "test1",
//                "老师4", "8", "2-13"));
//        mList.add(new TimeTableModel(0, 6, 8, 5, "8:20", "10:10", "test1",
//                "老师7", "11", "2-13"));
//        mList.add(new TimeTableModel(0, 3, 5, 5, "8:20", "10:10", "test1",
//                "老师6", "10", "2-13"));

    }

    /**
     * 空白部分的点击事件，在这里添加课程
     * @param week
     * @param clum
     */
    @Override
    public void onSelectClass(int week, int clum) {

        if (clum==1 || clum==2){
            mList.add(new TimeTableModel(0, 1, 2, week, "8:20", "10:10", "test8",
                    "李老师", "2", "2-13"));
        }
        if (clum==3 || clum==4 || clum ==5){
            mList.add(new TimeTableModel(0, 3, 5, week, "8:20", "10:10", "test1",
                    "李老师", "2", "2-13"));
        }

        if (clum==6 || clum==7){
            mList.add(new TimeTableModel(0, 6, 7, week, "8:20", "10:10", "test3",
                    "李老师", "2", "2-13"));
        }

        if (clum==8 || clum==9){
            mList.add(new TimeTableModel(0, 8, 9, week, "8:20", "10:10", "test4",
                    "李老师", "2", "2-13"));
        }


        if (clum==10 || clum==11 || clum==12){
            mList.add(new TimeTableModel(0, 10, 12, week, "8:20", "10:10", "test5",
                    "李老师", "2", "2-13"));
        }
        Log.d(TAG, "onSelectClass: "+week+" "+clum);
        mTimaTableView.removeAllViews();
        mTimaTableView.setTimeTable(mList);
    }
}