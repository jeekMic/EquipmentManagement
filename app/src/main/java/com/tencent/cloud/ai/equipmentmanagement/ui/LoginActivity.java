package com.tencent.cloud.ai.equipmentmanagement.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.cloud.ai.equipmentmanagement.R;
import com.tencent.cloud.ai.equipmentmanagement.form.LoginForm;
import com.tencent.cloud.ai.equipmentmanagement.utils.UrlUtil;

import org.angmarch.views.NiceSpinner;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener {
    private NiceSpinner niceSpinner;
    List<String> spinnerData = new LinkedList<>(Arrays.asList("管理员", "教师"));
    private EditText login_input_username;
    private EditText login_input_password;
    private NiceSpinner nice_spinner;
    private Button login_btn;
    public static final int RC_PERMISSIONS = 101;
    public int idcard = 0;
    Integer userType = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EasyPermissions.requestPermissions(this, "程序运行需要存储权限", RC_PERMISSIONS, getPermissions());
        String[] permissions = getPermissions();
        boolean hasPermissions = EasyPermissions.hasPermissions(this, permissions);

        initView();


    }

    private void initView() {
        niceSpinner = findViewById(R.id.nice_spinner);
        niceSpinner.attachDataSource(spinnerData);
        niceSpinner.setBackgroundResource(R.drawable.textview_round_border);
        niceSpinner.setTextColor(Color.WHITE);
        niceSpinner.setTextSize(13);
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idcard = position;
                if (idcard==0){
                    userType = -1;
                }else{
                    userType = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        login_input_username = findViewById(R.id.login_input_username);
        login_input_password = findViewById(R.id.login_input_password);
        nice_spinner= findViewById(R.id.nice_spinner);
        login_btn= findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
    }

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

    //同意权限回调
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }
    //拒绝权限回调
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
        if (idcard==0){
            if (login_input_username.getText().toString().equals("admin") && login_input_password.getText().toString().equals("admin")) {
                startActivity(new Intent(this, AdminActivity.class));
            }else{
                Toast.makeText(this, "未找到此账号", Toast.LENGTH_SHORT).show();
            }
        }else{
            LoginForm loginForm = new LoginForm().setUsername(login_input_username.getText().toString())
                    .setPassword(login_input_password.getText().toString())
                    .setUserType(userType);
            postLoginForm(loginForm);//提交表单及跳转
        }
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
                Toast.makeText(LoginActivity.this, "网络异常或者用户名未找到", Toast.LENGTH_LONG);
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
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, TeacherActivity.class));
                            Looper.loop();
                        } else {//学生
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, StudentActivity.class));
                            Looper.loop();
                        }
                    }
                }
            }
        });
    }
}