package com.tencent.cloud.ai.equipmentmanagement.model;

import java.util.List;

public class Mechine {

    private String msg;
    private Integer code;
    private List<AllApplyDTO> allApply;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<AllApplyDTO> getAllApply() {
        return allApply;
    }

    public void setAllApply(List<AllApplyDTO> allApply) {
        this.allApply = allApply;
    }

    public Mechine() {
    }

    public static class AllApplyDTO {
        private Integer id;
        private Integer teacherId;
        private String realname;
        private String name;
        private String createTime;
        private Integer buyStatus;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(Integer teacherId) {
            this.teacherId = teacherId;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Integer getBuyStatus() {
            return buyStatus;
        }

        public void setBuyStatus(Integer buyStatus) {
            this.buyStatus = buyStatus;
        }
    }
}
