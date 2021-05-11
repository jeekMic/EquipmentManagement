package com.tencent.cloud.ai.equipmentmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.tencent.cloud.ai.equipmentmanagement.R;
import com.tencent.cloud.ai.equipmentmanagement.custom.TimeTableView;
import com.tencent.cloud.ai.equipmentmanagement.listener.DXHttpListener;
import com.tencent.cloud.ai.equipmentmanagement.model.TimeTable;
import com.tencent.cloud.ai.equipmentmanagement.model.TimeTableModel;
import com.tencent.cloud.ai.equipmentmanagement.utils.DXHttpManager;
import com.tencent.cloud.ai.equipmentmanagement.utils.ScreenshotUtil;
import com.tencent.cloud.ai.equipmentmanagement.utils.UrlUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener, DXHttpListener {
    private TimeTableView mTimaTableView;
    private List<TimeTableModel> mList;
    private Button office_fair,use_info,bt_query;
    private ScrollView main_scrollview;
    private LinearLayout sy_application,ll_select_admin;
    private Spinner select_classroom,select_week;
    String url_default = UrlUtil.url + "/course/getAllCourse?week_num=1&classRoom_num=101";
    String url_base = UrlUtil.url + "/course/getAllCourse?";
    private String class_room = "10";
    private String class_room2 = "20";
    private String class_last = "10";
    private int week = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mList = new ArrayList<TimeTableModel>();
        mTimaTableView = (TimeTableView) findViewById(R.id.main_timetable_ly);
        ll_select_admin = (LinearLayout) findViewById(R.id.ll_select_admin);
        main_scrollview = findViewById(R.id.main_scrollview);
        sy_application = findViewById(R.id.sy_application);
        bt_query = findViewById(R.id.bt_query);

        select_classroom = findViewById(R.id.select_classroom);
        select_week = findViewById(R.id.select_week);


        office_fair = findViewById(R.id.office_fair);
        use_info = findViewById(R.id.use_info);



        office_fair.setOnClickListener(this);
        use_info.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        select_classroom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i==0)return;
                    if (i<=9){
                        class_last = class_room+i;
                    }else{
                        class_last = class_room+(i-9);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        select_week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0)return;
                week = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //addList();
        select_classroom.setSelection(1,true);
        select_week.setSelection(1,true);
        DXHttpManager.getmInstance().sendGetRequest(url_default, null, 100, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void addList() {
        mList.add(new TimeTableModel(0, 1, 2, 1, "8:20", "10:10", "test1",
                "王老师", "1", "2-13"));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_screenshot:
                break;
        }
        return true;
    }

    /**
     * 按钮的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.use_info:
                main_scrollview.setVisibility(View.VISIBLE);
                ll_select_admin.setVisibility(View.VISIBLE);
                sy_application.setVisibility(View.GONE);
                break;
            case R.id.office_fair:
                main_scrollview.setVisibility(View.GONE);
                ll_select_admin.setVisibility(View.GONE);
                sy_application.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_query://查询 week_num=1&classRoom_num=301
                String url = url_base+"week_num="+week+"&classRoom_num="+class_last;
                DXHttpManager.getmInstance().sendGetRequest(url, null, 100, this);
                break;
            default:
                break;

        }
    }

    @Override
    public void onSuccess(String bean) throws IOException {
        Gson gson = new Gson();
        TimeTable timeTable = gson.fromJson(bean, TimeTable.class);
        for (int i = 0; i < timeTable.getCourse().size(); i++) {
            mList.add(new TimeTableModel(timeTable.getCourse().get(i)));
        }
        mTimaTableView.removeAllViews();
        mTimaTableView.setTimeTable(mList);

    }

    @Override
    public void onFailed(int tag, IOException e) {

    }
}