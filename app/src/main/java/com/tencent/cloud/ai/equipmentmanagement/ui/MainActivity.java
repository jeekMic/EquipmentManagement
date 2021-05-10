package com.tencent.cloud.ai.equipmentmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tencent.cloud.ai.equipmentmanagement.R;
import com.tencent.cloud.ai.equipmentmanagement.form.LoginForm;
import com.tencent.cloud.ai.equipmentmanagement.utils.ToastUtils;
import com.tencent.cloud.ai.equipmentmanagement.utils.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
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
        et_name = findViewById(R.id.et_name);
        et_psw = findViewById(R.id.et_psw);
        sp_value = findViewById(R.id.sp_value);
    }

    public void onClick(View view) {
        if (et_name.getText().toString() == "" || et_psw.getText().toString() == "") {
            Toast.makeText(this, "哥，请不要写空数据。", Toast.LENGTH_SHORT).show();
        } else {
            Integer userType = null;
            String s = sp_value.getSelectedItem().toString();
            switch (s) {
                case "学生":
                    userType = 0;
//                    startActivity(new Intent(this, StudentActivity.class));
                    break;
                case "管理员":
                    userType = -1;
//                    startActivity(new Intent(this, AdminActivity.class));
                    break;
                case "教师":
                    userType = 1;
//                    startActivity(new Intent(this, TeacherActivity.class));
                    break;
                default:
                    break;
            }
            if (userType == -1) {//写死的管理员
                if (et_name.getText().toString().equals("admin") && et_psw.getText().toString().equals("admin")) {
                    startActivity(new Intent(this, AdminActivity.class));
                }
            } else {
                LoginForm loginForm = new LoginForm().setUsername(et_name.getText().toString())
                        .setPassword(et_psw.getText().toString())
                        .setUserType(userType);
                postLoginForm(loginForm);//提交表单及跳转
            }
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

    private void postLoginForm(LoginForm loginForm) {
        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        String json = "{" +
                "\"username\"" + ":" + "\"" + loginForm.getUsername() + "\"" + "," +
                "\"password\"" + ":" + "\"" + loginForm.getPassword() + "\"" + "," +
                "\"userType\"" + ":" + "\"" + loginForm.getUserType() + "\"" +
                "}";
        RequestBody requestBody = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(UrlUtil.url + "/user/login")
                .post(requestBody)
                .build();
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_LONG);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.d("JSON", "onResponse: " + result.indexOf("success", 8) + "\n" + "userType:" + loginForm.getUserType());
                    if (result.indexOf("success", 8) == 8) {
                        if (loginForm.getUserType() == 1) {//教师
                            /**
                             * 子线程使用Toast，前后要加Looper.prepare();和Looper.loop();
                             */
                            Looper.prepare();
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, TeacherActivity.class));
                            Looper.loop();
                        } else {//学生
                            Looper.prepare();
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, StudentActivity.class));
                            Looper.loop();
                        }
                    }
                }
            }
        });
    }
}