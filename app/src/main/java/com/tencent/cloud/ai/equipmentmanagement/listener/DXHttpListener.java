package com.tencent.cloud.ai.equipmentmanagement.listener;

import com.tencent.cloud.ai.equipmentmanagement.model.RequestBean;

import java.io.IOException;

public interface DXHttpListener {
    void onSuccess(String bean) throws IOException;
    void onFailed(int tag, IOException e);
}
