package com.coderzpassion.studentfaculty.model;

/**
 * Created by bajpa on 24-Sep-17.
 */

public class TimeTableItem {
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    String path;
    String department;
    String batch;
}
