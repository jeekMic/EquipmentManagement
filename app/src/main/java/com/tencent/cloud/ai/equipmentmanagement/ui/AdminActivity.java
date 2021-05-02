package com.tencent.cloud.ai.equipmentmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.tencent.cloud.ai.equipmentmanagement.R;
import com.tencent.cloud.ai.equipmentmanagement.custom.TimeTableView;
import com.tencent.cloud.ai.equipmentmanagement.model.TimeTableModel;
import com.tencent.cloud.ai.equipmentmanagement.utils.ScreenshotUtil;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    private TimeTableView mTimaTableView;
    private List<TimeTableModel> mList;
    private Button office_fair,use_info;
    private ScrollView main_scrollview;
    private LinearLayout sy_application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mList = new ArrayList<TimeTableModel>();
        mTimaTableView = (TimeTableView) findViewById(R.id.main_timetable_ly);
        main_scrollview = findViewById(R.id.main_scrollview);
        sy_application = findViewById(R.id.sy_application);


        office_fair = findViewById(R.id.office_fair);
        use_info = findViewById(R.id.use_info);



        office_fair.setOnClickListener(this);
        use_info.setOnClickListener(this);


        addList();
        mTimaTableView.setTimeTable(mList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void addList() {
        mList.add(new TimeTableModel(0, 1, 2, 1, "8:20", "10:10", "test1",
                "王老师", "1", "2-13"));
        mList.add(new TimeTableModel(0, 3, 4, 1, "8:20", "10:10", "test1",
                "李老师", "2", "2-13"));
        mList.add(new TimeTableModel(0, 6, 7, 1, "8:20", "10:10", "test1",
                "王", "3", "2-13"));


        mList.add(new TimeTableModel(0, 6, 7, 2, "8:20", "10:10", "test1",
                "老师1", "4", "2-13"));
        mList.add(new TimeTableModel(0, 8, 9, 2, "8:20", "10:10", "test1",
                "老师2", "5", "2-13"));

        mList.add(new TimeTableModel(0, 1, 2, 3, "8:20", "10:10", "test1",
                "老师3", "6", "2-13"));

        mList.add(new TimeTableModel(0, 6, 7, 3, "8:20", "10:10", "test1",
                "老师4", "7", "2-13"));
        mList.add(new TimeTableModel(0, 8, 9, 4, "8:20", "10:10", "test1",
                "老师5", "9", "2-13"));
        mList.add(new TimeTableModel(0, 3, 5, 4, "8:20", "10:10", "test1",
                "老师4", "8", "2-13"));
        mList.add(new TimeTableModel(0, 6, 8, 5, "8:20", "10:10", "test1",
                "老师7", "11", "2-13"));
        mList.add(new TimeTableModel(0, 3, 5, 5, "8:20", "10:10", "test1",
                "老师6", "10", "2-13"));

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
                sy_application.setVisibility(View.GONE);
                break;
            case R.id.office_fair:
                main_scrollview.setVisibility(View.GONE);
                sy_application.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }
}