package com.tencent.cloud.ai.equipmentmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tencent.cloud.ai.equipmentmanagement.R;

public class MainActivity extends AppCompatActivity {
    private EditText et_name;
    private EditText et_psw;
    private Spinner sp_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void onClick(View view) {
        if (et_name.getText().toString()=="" || et_psw.getText().toString()==""){
            Toast.makeText(this, "哥，请不要写空数据。", Toast.LENGTH_SHORT).show();
        }
        String s = sp_value.getSelectedItem().toString();
        switch (s){
            case "学生":
                startActivity(new Intent(this,StudentActivity.class));
                break;
            case "管理员":
                startActivity(new Intent(this,AdminActivity.class));
                break;
            case "教师":
                startActivity(new Intent(this,TeacherActivity.class));
                break;
            default:
                break;
        }
    }
    public void initView() {
        et_name = findViewById(R.id.et_name);
        et_psw = findViewById(R.id.et_psw);
        sp_value = findViewById(R.id.sp_value);
    }
}