package com.tencent.cloud.ai.equipmentmanagement.model;

import java.util.List;

public class TimeTable {

    private String msg;
    private Integer code;
    private List<CourseDTO> course;

    public static class CourseDTO {
        private Integer weekNum;
        private Integer dayTimes;
        private Integer week;
        private Integer teacherId;
        private String classroomName;
        private String courseName;
        private String createTime;
        private String updateTime;
        private String username;
        private String realname;

        public Integer getWeekNum() {
            return weekNum;
        }

        public void setWeekNum(Integer weekNum) {
            this.weekNum = weekNum;
        }

        public Integer getDayTimes() {
            return dayTimes;
        }

        public void setDayTimes(Integer dayTimes) {
            this.dayTimes = dayTimes;
        }

        public Integer getWeek() {
            return week;
        }

        public void setWeek(Integer week) {
            this.week = week;
        }

        public Integer getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(Integer teacherId) {
            this.teacherId = teacherId;
        }

        public String getClassroomName() {
            return classroomName;
        }

        public void setClassroomName(String classroomName) {
            this.classroomName = classroomName;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }

    public TimeTable(String msg, Integer code, List<CourseDTO> course) {
        this.msg = msg;
        this.code = code;
        this.course = course;
    }

    public TimeTable() {
    }

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

    public List<CourseDTO> getCourse() {
        return course;
    }

    public void setCourse(List<CourseDTO> course) {
        this.course = course;
    }
}
