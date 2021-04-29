package com.tencent.cloud.ai.equipmentmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tencent.cloud.ai.equipmentmanagement.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    private EditText et_name;
    private EditText et_psw;
    private Spinner sp_value;
    public static final int RC_PERMISSIONS = 101;
    /**
     * 需要申请的权限
     * @return 数组 permissions
     */
    public String[] getPermissions() {
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        return permissions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        EasyPermissions.requestPermissions(this, "程序运行需要存储权限", RC_PERMISSIONS, getPermissions());
        String[] permissions = getPermissions();
        boolean hasPermissions = EasyPermissions.hasPermissions(this, permissions);
        //这里应该要判断下权限是否都申请了，但是可以简化
    }

    public void onClick(View view) {
        if (et_name.getText().toString() == "" || et_psw.getText().toString() == "") {
            Toast.makeText(this, "哥，请不要写空数据。", Toast.LENGTH_SHORT).show();
        }
        String s = sp_value.getSelectedItem().toString();
        switch (s) {
            case "学生":
                startActivity(new Intent(this, StudentActivity.class));
                break;
            case "管理员":
                startActivity(new Intent(this, AdminActivity.class));
                break;
            case "教师":
                startActivity(new Intent(this, TeacherActivity.class));
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


    //同意权限回调
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }
    //拒绝权限回调
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

}