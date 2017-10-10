package com.coderzpassion.studentfaculty.model;

/**
 * Created by bajpa on 23-Sep-17.
 */

public class DatesheetItem {

    private String department;
    private String batch;
    private String subject;
    private String date;
    private String time;

    public DatesheetItem() {
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DatesheetItem(String department, String batch, String subject, String date, String time) {
        this.department = department;
        this.batch = batch;
        this.subject = subject;
        this.date = date;
        this.time = time;
    }


}
