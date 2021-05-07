package com.tencent.cloud.ai.equipmentmanagement.model;

import java.util.List;

public class ClassInfo {

    private String msg;
    private Integer code;
    private List<CourseDTO> course;

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

    public static class CourseDTO {
        /**
         * 周数(第几周)
         */
        private Integer weekNum;
        /**
         * 时间(每天第几节课)
         */
        private Integer dayTimes;
        /**
         *  星期(1-星期一，2-星期二...etc..)
         */
        private Integer week;
        /**
         * 教师id
         */
        private Integer teacherId;
        /**
         * 教室名称
         */
        private String classroomName;
        /**
         * 课程名称
         */
        private String courseName;
        /**
         * 创建时间
         */
        private String createTime;
        /**
         * 更新时间
         */
        private String updateTime;
        /**
         * 上课老师
         */
        private String username;

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
    }
}
