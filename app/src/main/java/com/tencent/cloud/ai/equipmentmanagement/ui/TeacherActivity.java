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
import com.tencent.cloud.ai.equipmentmanagement.listener.DXHttpListener;
import com.tencent.cloud.ai.equipmentmanagement.model.ClassInfo;
import com.tencent.cloud.ai.equipmentmanagement.model.CreateUserDialog;
import com.tencent.cloud.ai.equipmentmanagement.model.RequestBean;
import com.tencent.cloud.ai.equipmentmanagement.model.TimeTableModel;
import com.tencent.cloud.ai.equipmentmanagement.utils.DXHttpManager;
import com.tencent.cloud.ai.equipmentmanagement.utils.TestString;
import com.tencent.cloud.ai.equipmentmanagement.utils.UrlUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TeacherActivity extends BaseActivity implements View.OnClickListener, ClassListener, DXHttpListener {

    public final String TAG = this.getClass().getSimpleName();
    String url_1 = UrlUtil.url + "/course/getAllCourse?week_num=1";
    String url_2 = UrlUtil.url + "/course/getAllCourse?week_num=2";
    String url_3 = UrlUtil.url + "/course/getAllCourse?week_num=3";
    String url_4 = UrlUtil.url + "/course/getAllCourse?week_num=4";
    String url_5 = UrlUtil.url + "/course/getAllCourse?week_num=5";
    String url_6 = UrlUtil.url + "/course/getAllCourse?week_num=6";
    String url_7 = UrlUtil.url + "/course/getAllCourse?week_num=7";
    private OkHttpClient okHttpClient;
    private Request request;
    private Call call;
    private Button addclass, use_require;
    private ScrollView t_main_scroview;
    private LinearLayout t_sy_application, ll_class;
    private List<TimeTableModel> mList;
    private TimeTableView mTimaTableView;
    private int day = 0;

    @Override
    public void myOnCreate() {
        setContentView(R.layout.activity_teacher);
        mList = new ArrayList<>();
        initView();

        //OkHttp ?????????
//        okHttpClient = new OkHttpClient();
//        request = new Request.Builder()
//                .url(url)
//                .get()//????????????GET?????????????????????
//                .build();
//        call = okHttpClient.newCall(request);
//        getData();
        Toast.makeText(this, "??????", Toast.LENGTH_SHORT).show();

        DXHttpManager.getmInstance().sendGetRequest(url_1, null, 100, this);
        DXHttpManager.getmInstance().sendGetRequest(url_2, null, 100, this);
        DXHttpManager.getmInstance().sendGetRequest(url_3, null, 100, this);
        DXHttpManager.getmInstance().sendGetRequest(url_4, null, 100, this);
        DXHttpManager.getmInstance().sendGetRequest(url_5, null, 100, this);
        DXHttpManager.getmInstance().sendGetRequest(url_6, null, 100, this);
        DXHttpManager.getmInstance().sendGetRequest(url_7, null, 100, this);


        mTimaTableView.setListener(this);
    }

    @Override
    public void onSuccess(String bean) throws IOException {
        //??????????????????????????????
        Log.d(TAG, "onResponse: " + bean);
//        String result = TestString.classInfo;//???????????? ??????????????????response.body().string()
//        Log.e("hb==: ",bean);
        ++day;
        Gson gson = new Gson();
        ClassInfo info = gson.fromJson(bean, ClassInfo.class);
        for (int i = 0; i < info.getCourse().size(); i++) {
            mList.add(new TimeTableModel(info.getCourse().get(i)));
        }
        if (day == 7) {
            mTimaTableView.setTimeTable(mList);
        }


    }

    @Override
    public void onFailed(int tag, IOException e) {
        Log.e("=======", e.toString());
    }

    public void initView() {
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

    public void getData() {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getLocalizedMessage() + " " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //??????????????????????????????
                Log.d(TAG, "onResponse: " + response.body().string());
                String result = TestString.classInfo;//???????????? ??????????????????response.body().string()
                Log.e("hb==: ", result);
                Gson gson = new Gson();
                ClassInfo info = gson.fromJson(result, ClassInfo.class);
                for (int i = 0; i < info.getCourse().size(); i++) {
                    mList.add(new TimeTableModel(info.getCourse().get(i)));
                }
                mTimaTableView.setTimeTable(mList);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_class:
                Toast.makeText(this, "??????", Toast.LENGTH_SHORT).show();
                t_main_scroview.setVisibility(View.VISIBLE);
                ll_class.setVisibility(View.VISIBLE);
                t_sy_application.setVisibility(View.GONE);
                break;
            case R.id.use_require:
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
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
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert mInputMethodManager != null;
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    private void addList() {
        mList.add(new TimeTableModel(0, 1, 1, 2, "8:20", "10:10", "test1",
                "?????????", "1", "2-13"));
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param week
     * @param clum
     */
    @Override
    public void onSelectClass(int week, int clum) {
        showEditDialog();
        mList.add(new TimeTableModel(0, clum, clum, week, "8:20", "10:10", "test1",
                "?????????", "2", "2-13"));

        Log.d(TAG, "onSelectClass: " + week + " " + clum);
        mTimaTableView.removeAllViews();
        mTimaTableView.setTimeTable(mList);
    }
    CreateUserDialog createUserDialog;
    public void showEditDialog() {
        createUserDialog = new CreateUserDialog(this,R.style.Transparent ,onClickListener);
        createUserDialog.show();
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_save:

                    String name = createUserDialog.text_name.getText().toString().trim();
                    String mobile = createUserDialog.text_mobile.getText().toString().trim();
                    String info = createUserDialog.text_info.getText().toString().trim();

                    System.out.println(name+"??????"+mobile+"??????"+info);
                    break;
            }
        }
    };
}