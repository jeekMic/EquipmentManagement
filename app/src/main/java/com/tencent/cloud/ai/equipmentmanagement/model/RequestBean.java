package com.tencent.cloud.ai.equipmentmanagement.model;

import okhttp3.Response;

public class RequestBean {
    public  int tag;
    public Response response;
    public RequestBean(int tag, Response response){
        this.tag = tag;
        this.response = response;
    }
}
