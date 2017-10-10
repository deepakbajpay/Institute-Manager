package com.coderzpassion.studentfaculty.model;

/**
 * Created by coderzpassion on 14/05/17.
 */

public class Faculty {
    private String id;
    private String name;
    private String password;
    private String email;
    private String subject;
    private String department;
    private String contact;

    public String getContact() {
        return contact;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Faculty(String id,String name,String password,String email,String contact,String subject)
    {
        this.id=id;
        this.name=name;
        this.password=password;
        this.email=email;
        this.contact = contact;
        this.subject=subject;
    }

    public Faculty()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
