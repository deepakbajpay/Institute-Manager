package com.coderzpassion.studentfaculty.model;

/**
 * Created by coderzpassion on 14/05/17.
 */

public class Marks {

    private String id;
    private String total_marks;
    private String marks_obtained;
    private String marks_by;
    private String marks_to;
    private String subject;
    private String contact;
    private String mst;

    public String getMst() {
        return mst;
    }

    public void setMst(String mst) {
        this.mst = mst;
    }

    public Marks()
    {

    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Marks(String id, String total_marks, String marks_obtained, String marks_by, String marks_to, String s, String contact)
    {
        this.id=id;
        this.total_marks=total_marks;
        this.marks_obtained=marks_obtained;
        this.marks_by=marks_by;
        this.marks_to=marks_to;
        this.subject=s;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(String total_marks) {
        this.total_marks = total_marks;
    }

    public String getMarks_obtained() {
        return marks_obtained;
    }

    public void setMarks_obtained(String marks_obtained) {
        this.marks_obtained = marks_obtained;
    }

    public String getMarks_by() {
        return marks_by;
    }

    public void setMarks_by(String marks_by) {
        this.marks_by = marks_by;
    }

    public String getMarks_to() {
        return marks_to;
    }

    public void setMarks_to(String marks_to) {
        this.marks_to = marks_to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
