package com.tencent.cloud.ai.equipmentmanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.cloud.ai.equipmentmanagement.R;
import com.tencent.cloud.ai.equipmentmanagement.adapter.MechineAdapter;
import com.tencent.cloud.ai.equipmentmanagement.listener.DXHttpListener;
import com.tencent.cloud.ai.equipmentmanagement.model.Mechine;
import com.tencent.cloud.ai.equipmentmanagement.utils.DXHttpManager;

import java.io.IOException;
import java.lang.reflect.GenericSignatureFormatError;

public class ShowApplyAllActivity extends AppCompatActivity implements DXHttpListener {
    private ListView ls_mechine;
    private MechineAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_apply_all);
        ls_mechine =findViewById(R.id.ls_mechine);

        DXHttpManager.getmInstance().sendGetRequest("http://39.99.253.162/lab/equipment/getAllApply", null, 100, this);
    }

    @Override
    public void onSuccess(String bean) throws IOException {
        Gson gson = new Gson();
        Mechine mechine = gson.fromJson(bean, Mechine.class);
        Log.e("hb=======>> ",""+bean);
        adapter = new MechineAdapter(this,mechine.getAllApply());
        ls_mechine.setAdapter(adapter);
    }

    @Override
    public void onFailed(int tag, IOException e) {

    }
}