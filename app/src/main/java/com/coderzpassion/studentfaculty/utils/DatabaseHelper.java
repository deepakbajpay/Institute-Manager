package com.coderzpassion.studentfaculty.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coderzpassion.studentfaculty.model.DatesheetItem;
import com.coderzpassion.studentfaculty.model.Faculty;
import com.coderzpassion.studentfaculty.model.Marks;
import com.coderzpassion.studentfaculty.model.Student;
import com.coderzpassion.studentfaculty.model.TimeTableItem;

import java.util.ArrayList;


/**
 * Created by coderzpassion on 03/10/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Db Version
    private static final int Db_Version = 1;
    //Db Name
    private static final String Db_Name = "studentfaculty";
    private static final String Table_Student = "student";
    private static final String Table_Faculty = "faculty";
    private static final String Table_HOD = "HOD";
    private static final String Table_CollegeHead = "College_Head";
    private static final String Table_Attendence = "attendence";
    private static final String Table_Announcements = "announcements";
    private static final String Table_Marks = "marks";
    private static final String Table_Datesheet = "datesheet";
    private static final String Table_TimeTable = "timetable";
    private static Context context;
    //Creating student Columns
    private String id = "id";
    private String name = "name";
    private String rollno = "roll";
    private String email = "email";
    private String password = "password";
    private String department = "department";
    private String batch = "batch";
    private String contact = "contact";
    private String Create_Table_Student = "CREATE TABLE " + Table_Student + "(" + id
            + " INTEGER primary key autoincrement ," + name + " TEXT," + rollno +
            " TEXT," + email + " TEXT,"
            + password + " TEXT," + department + " TEXT," + batch + " TEXT," + contact + " TEXT" + ")";

    // Creating faculty Columns
    private String facultyid = "id";
    private String facultyname = "name";
    private String facultyemail = "email";
    private String facultypass = "pass";
    private String facultysubject = "subject";
    private String facultydepartment = "department";
    private String facultyContact = "contact";
    private String Create_Table_Faculty = "CREATE TABLE " + Table_Faculty + "(" + facultyid
            + " INTEGER primary key autoincrement ,"
            + facultyname + " TEXT,"
            + facultyemail + " TEXT,"
            + facultypass + " TEXT,"
            + facultysubject + " TEXT,"
            + facultydepartment + " TEXT,"
            + facultyContact + " TEXT" + ")";

    // Creating hod Columns
    private String hodid = "id";
    private String hodname = "name";
    private String hodemail = "email";
    private String hodpass = "pass";
    private String hoddepartment = "department";
    private String hodContact = "contact";
    private String Create_Table_HOD = "CREATE TABLE " + Table_HOD + "(" + hodid
            + " INTEGER primary key autoincrement ,"
            + hodname + " TEXT,"
            + hodemail + " TEXT,"
            + hodContact + " TEXT,"
            + hodpass + " TEXT,"
            + hoddepartment + " TEXT"
            + ")";

    private String collegeHeadid = "id";
    private String collegeHeadname = "name";
    private String collegeHeademail = "email";
    private String collegeHeadpass = "pass";
    private String collegeHeadContact = "contact";
    private String Create_Table_CollegeHead = "CREATE TABLE " + Table_CollegeHead + "(" + collegeHeadid
            + " INTEGER primary key autoincrement ,"
            + collegeHeadname + " TEXT,"
            + collegeHeademail + " TEXT,"
            + collegeHeadContact + " TEXT,"
            + collegeHeadpass + " TEXT"
            + ")";


    // favourite table columns
    private String attendence_id = "id";
    private String attendence_total = "total";
    private String attendence_obtained = "obtained";
    private String attendence_subject = "subject";
    private String attendence_teacher = "teacher";
    private String attendence_to_roll = "roll";
    String Create_Table_Attendence = "CREATE TABLE " + Table_Attendence + "(" + attendence_id
            + " INTEGER primary key autoincrement ,"
            + attendence_total + " TEXT,"
            + attendence_obtained + " TEXT,"
            + attendence_subject + " TEXT,"
            + attendence_to_roll + " TEXT,"
            + attendence_teacher + " TEXT"
           + ")";
    // favourite table columns
    private String announcements_id = "id";
    private String announcements_by = "announce_by";
    private String announcements_text = "text";
    String Create_Table_Announcement = "CREATE TABLE " + Table_Announcements + "(" + announcements_id
            + " INTEGER primary key autoincrement ,"
            + announcements_by + " TEXT,"
            + announcements_text + " TEXT" + ")";
    private String marks_id = "id";
    private String marks_total = "total";
    private String marks_obtained = "obtained";
    private String marks_by = "marksby";
    private String marks_to = "marksto";
    private String marks_subject = "markssubject";
    private String marks_mst = "mst";
    String Create_Table_Marks = "CREATE TABLE " + Table_Marks + "(" + marks_id
            + " INTEGER primary key autoincrement ,"
            + marks_total + " TEXT,"
            + marks_obtained + " TEXT,"
            + marks_by + " TEXT,"
            + marks_subject + " TEXT,"
            + marks_to + " TEXT,"
            + marks_mst + " TEXT" + ")";

    private String datesheet_id = "id";
    private String datesheet_department = "department";
    private String datesheet_batch = "batch";
    private String datesheet_subject = "subject";
    private String datesheet_date = "date";
    private String datesheet_time = "time";

    String Create_Table_Datesheet = "CREATE TABLE " + Table_Datesheet + "(" + datesheet_id
            + " INTEGER primary key autoincrement ,"
            + datesheet_department + " TEXT,"
            + datesheet_batch + " TEXT,"
            + datesheet_subject + " TEXT,"
            + datesheet_date + " TEXT,"
            + datesheet_time + " TEXT" + ")";

    private String timetable_id = "id";
    private String timetable_department = "department";
    private String timetable_batch = "batch";
    private String timetable_path = "subject";

    String Create_Table_Timetable = "CREATE TABLE " + Table_TimeTable + "(" + timetable_id
            + " INTEGER primary key autoincrement ,"
            + timetable_department + " TEXT,"
            + timetable_batch + " TEXT,"
            + timetable_path + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, Db_Name, null, Db_Version);
        DatabaseHelper.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Student);
        db.execSQL(Create_Table_Faculty);
        db.execSQL(Create_Table_Announcement);
        db.execSQL(Create_Table_Attendence);
        db.execSQL(Create_Table_Marks);
        db.execSQL(Create_Table_Datesheet);
        db.execSQL(Create_Table_Timetable);
        db.execSQL(Create_Table_HOD);
        db.execSQL(Create_Table_CollegeHead);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Create_Table_Student);
        db.execSQL("DROP TABLE IF EXISTS " + Create_Table_Faculty);
        db.execSQL("DROP TABLE IF EXISTS " + Create_Table_Announcement);
        db.execSQL("DROP TABLE IF EXISTS " + Create_Table_Attendence);
        db.execSQL("DROP TABLE IF EXISTS " + Create_Table_Marks);
        db.execSQL("DROP TABLE IF EXISTS " + Create_Table_Datesheet);
        db.execSQL("DROP TABLE IF EXISTS " + Create_Table_Timetable);
        db.execSQL("DROP TABLE IF EXISTS " + Create_Table_HOD);
        db.execSQL("DROP TABLE IF EXISTS " + Create_Table_CollegeHead);
        //create the table again
        onCreate(db);
    }

    public long addStudent(Student productDetail) {
        long i = -1;
        // getting db instance for writing the contact
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(name, productDetail.getName());
        cv.put(rollno, productDetail.getRollno());
        cv.put(email, productDetail.getEmail());
        cv.put(department, productDetail.getDepartment());
        cv.put(batch, productDetail.getBatch());
        cv.put(password, productDetail.getPassword());
        cv.put(contact, productDetail.getContact());

        // check if exists earlier
        String selectQuery = "SELECT * FROM " + Table_Student + " WHERE " + email + "=?";
        Cursor c = db.rawQuery(selectQuery, new String[]{productDetail.getEmail()});

        if (c.getCount() > 0) {
            cv.put(id, Integer.parseInt(productDetail.getId()));
            i = db.update(Table_Student, cv, email + " = ?", new String[]{productDetail.getEmail()});
        } else {
            i = db.insert(Table_Student, null, cv);
        }
        //inserting row

        //close the database to avoid any leak
        db.close();

        return i;
    }

    public Student getStudentDetails() {
        String selectAllContact = "SELECT * FROM " + Table_Student + " Where email = '" + SharedPrefs.getString(DatabaseHelper.context, SharedPrefs.email) + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        Student student = new Student();
        if (cursor.moveToFirst()) {
            student.setId("" + cursor.getInt(0));
            student.setName(cursor.getString(1));
            student.setRollno(cursor.getString(2));
            student.setEmail(cursor.getString(3));
            student.setPassword(cursor.getString(4));
            student.setDepartment(cursor.getString(5));
            student.setBatch(cursor.getString(6));
            student.setContact(cursor.getString(7));
        }
        return student;
    }

    public Student getStudentDetailsByRollNo(String rollno0) {
        String selectAllContact = "SELECT * FROM " + Table_Student + " Where roll = '" + rollno + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);

        String selectQuery = "SELECT * FROM " + Table_Student + " WHERE " + rollno + "=?";
        Cursor c = db.rawQuery(selectQuery, new String[]{rollno0});

        // move cursor to first and use loop to add all the contacts to list
        Student student = new Student();
        if (c.moveToFirst()) {
            student.setId("" + c.getInt(0));
            student.setName(c.getString(1));
            student.setRollno(c.getString(2));
            student.setEmail(c.getString(3));
            student.setPassword(c.getString(4));
            student.setDepartment(c.getString(5));
            student.setBatch(c.getString(6));
            student.setContact(c.getString(7));
            System.out.println("DatabaseHelper.getStudentDetailsByRollNo " + c.getColumnNames());

        }
        return student;
    }

    public long addFaculty(Faculty productDetail) {
        long i = -1;
        // getting db instance for writing the contact
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(facultyname, productDetail.getName());
        cv.put(facultyemail, productDetail.getEmail());
        cv.put(facultypass, productDetail.getPassword());
        cv.put(facultysubject, productDetail.getSubject());
        cv.put(facultydepartment, productDetail.getDepartment());
        cv.put(facultyContact, productDetail.getContact());
        String selectQuery = "SELECT * FROM " + Table_Faculty + " WHERE " + facultyemail + "=?";
        Cursor c = db.rawQuery(selectQuery, new String[]{productDetail.getEmail()});

        if (c.getCount() > 0) {
            cv.put(facultyid, Integer.parseInt(productDetail.getId()));
            i = db.update(Table_Faculty, cv, facultyemail + " = ?", new String[]{productDetail.getEmail()});
        } else {
            i = db.insert(Table_Faculty, null, cv);
        }

        //close the database to avoid any leak
        db.close();

        return i;
    }

    public Faculty getFacultyDetails() {
        String selectAllContact = "SELECT * FROM " + Table_Faculty + " Where email = '" + SharedPrefs.getString(DatabaseHelper.context, SharedPrefs.email) + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        Faculty faculty = new Faculty();

        if (cursor.moveToFirst()) {
            faculty.setId("" + cursor.getInt(0));
            faculty.setName(cursor.getString(1));
            faculty.setEmail(cursor.getString(2));
            faculty.setPassword(cursor.getString(3));
            faculty.setSubject(cursor.getString(4));
            faculty.setDepartment(cursor.getString(5));
            faculty.setContact(cursor.getString(6));

        }
        return faculty;
    }

    public long addHod(Faculty productDetail) {
        long i = -1;
        // getting db instance for writing the contact
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(hodname, productDetail.getName());
        cv.put(hodemail, productDetail.getEmail());
        cv.put(hodpass, productDetail.getPassword());
        cv.put(hoddepartment, productDetail.getDepartment());
        cv.put(hodContact, productDetail.getContact());
        String selectQuery = "SELECT * FROM " + Table_HOD + " WHERE " + hodemail + "=?";
        Cursor c = db.rawQuery(selectQuery, new String[]{productDetail.getEmail()});

        if (c.getCount() > 0) {
            cv.put(hodid, Integer.parseInt(productDetail.getId()));
            i = db.update(Table_HOD, cv, hodemail + " = ?", new String[]{productDetail.getEmail()});
        } else {
            i = db.insert(Table_HOD, null, cv);
        }

        //close the database to avoid any leak
        db.close();

        return i;
    }


    public Faculty getHodDetails() {
        String selectAllContact = "SELECT * FROM " + Table_HOD + " Where email = '" + SharedPrefs.getString(DatabaseHelper.context, SharedPrefs.email) + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        Faculty faculty = new Faculty();

        if (cursor.moveToFirst()) {
            faculty.setId("" + cursor.getInt(0));
            faculty.setName(cursor.getString(1));
            faculty.setEmail(cursor.getString(2));
            faculty.setContact(cursor.getString(3));
            faculty.setPassword(cursor.getString(4));
            faculty.setDepartment(cursor.getString(5));

        }
        return faculty;
    }

    public ArrayList<Faculty> getHODs() {
        String selectAllContact = "SELECT * FROM " + Table_HOD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Faculty> hods = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Faculty faculty = new Faculty();
                faculty.setId("" + cursor.getInt(0));
                faculty.setName(cursor.getString(1));
                faculty.setEmail(cursor.getString(2));
                faculty.setContact(cursor.getString(3));
                faculty.setPassword(cursor.getString(4));
                faculty.setDepartment(cursor.getString(5));
                hods.add(faculty);
            }while (cursor.moveToNext());

        }
        return hods;
    }

    public ArrayList<Faculty> getHodByDepartment(String departmentname) {
        String selectAllContact = "SELECT * FROM " + Table_HOD + " WHERE "+ hoddepartment + " = '"+departmentname+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Faculty> hods = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Faculty faculty = new Faculty();
                faculty.setId("" + cursor.getInt(0));
                faculty.setName(cursor.getString(1));
                faculty.setEmail(cursor.getString(2));
                faculty.setContact(cursor.getString(3));
                faculty.setPassword(cursor.getString(4));
                faculty.setDepartment(cursor.getString(5));
                hods.add(faculty);
            }while (cursor.moveToNext());

        }
        return hods;
    }




    public long addCollegeHead(Faculty productDetail) {
        long i = -1;
        // getting db instance for writing the contact
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(collegeHeadname, productDetail.getName());
        cv.put(collegeHeademail, productDetail.getEmail());
        cv.put(collegeHeadpass, productDetail.getPassword());
        cv.put(collegeHeadContact, productDetail.getContact());
        String selectQuery = "SELECT * FROM " + Table_CollegeHead + " WHERE " + collegeHeademail + "=?";
        Cursor c = db.rawQuery(selectQuery, new String[]{productDetail.getEmail()});

        if (c.moveToFirst()) {
            int collegeHeadId = c.getInt(0);
            i = db.update(Table_CollegeHead, cv, collegeHeadid + " = "+collegeHeadId, null);
        } else {
            i = db.insert(Table_CollegeHead, null, cv);
        }

        //close the database to avoid any leak
        db.close();

        return i;
    }


    public Faculty getCollegeHeadDetails() {
        String selectAllContact = "SELECT * FROM " + Table_CollegeHead + " Where email = '" + SharedPrefs.getString(DatabaseHelper.context, SharedPrefs.email) + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        Faculty faculty = new Faculty();

        if (cursor.moveToFirst()) {
            faculty.setId("" + cursor.getInt(0));
            faculty.setName(cursor.getString(1));
            faculty.setEmail(cursor.getString(2));
            faculty.setContact(cursor.getString(3));
            faculty.setPassword(cursor.getString(4));

        }
        return faculty;
    }

    public Faculty getCollegeHeadInfo() {
        String selectAllContact = "SELECT * FROM " + Table_CollegeHead;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        Faculty faculty = new Faculty();

        if (cursor.moveToFirst()) {
            faculty.setId("" + cursor.getInt(0));
            faculty.setName(cursor.getString(1));
            faculty.setEmail(cursor.getString(2));
            faculty.setContact(cursor.getString(3));
            faculty.setPassword(cursor.getString(4));

        }
        return faculty;
    }





    public long addMarks(Marks productDetail) {
        long i = -1;
        // getting db instance for writing the contact
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(marks_total, productDetail.getTotal_marks());
        cv.put(marks_obtained, productDetail.getMarks_obtained());
        cv.put(marks_by, productDetail.getMarks_by());
        cv.put(marks_to, productDetail.getMarks_to());
        cv.put(marks_subject, productDetail.getSubject());
        cv.put(marks_mst, productDetail.getMst());

//        String selectQuery = "SELECT * FROM "+Table_Marks+" WHERE "+marks_to+"=?";
//        Cursor c = db.rawQuery(selectQuery, new String[]{productDetail.getMarks_to()});


        // array of columns to fetch
        String[] columns = {
                marks_id
        };
        SQLiteDatabase db2 = this.getReadableDatabase();
        // selection criteria

        String selectQuery = "SELECT *" + " FROM " + Table_Marks + " WHERE "
                + marks_to + " = '" + productDetail.getMarks_to() + "' AND " + marks_subject + " = '" + productDetail.getSubject()
                + "' AND " + marks_mst + " = '" + productDetail.getMst() + "'";
        Cursor cursor = db2.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            System.out.println("DatabaseHelper.addMarks " + id);
            i = db.update(Table_Marks, cv, marks_id + " = " + id, null);
        }
        else {
            i = db.insert(Table_Marks, null, cv);
        }

        //close the database to avoid any leak
        db.close();
        db2.close();

        return i;
    }

    public void deleteStudentMarks(String rollno) {
        //Open the database
        SQLiteDatabase database = this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + Table_Marks + " WHERE " + marks_id + "= '" + rollno + "'");

        //Close the database
        database.close();
    }

    public long addAnnoucement(String productDetail) {
        long i = -1;
        // getting db instance for writing the contact
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(announcements_by, SharedPrefs.getString(DatabaseHelper.context, SharedPrefs.email));
        cv.put(announcements_text, productDetail);

        //inserting row
        i = db.insert(Table_Announcements, null, cv);
        //close the database to avoid any leak
        db.close();

        return i;
    }


    public long addAttendance(Marks productDetail) {
        long i = -1;
        // getting db instance for writing the contact
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(attendence_total, productDetail.getTotal_marks());
        cv.put(attendence_obtained, productDetail.getMarks_obtained());
        cv.put(attendence_teacher, productDetail.getMarks_by());
        cv.put(attendence_to_roll, productDetail.getMarks_to());
        cv.put(attendence_subject, productDetail.getSubject());

//        String selectQuery = "SELECT * FROM "+Table_Attendence+" WHERE "+attendence_to_roll+"=?";
//        Cursor c = db.rawQuery(selectQuery, new String[]{productDetail.getMarks_to()});

        // array of columns to fetch
        String[] columns = {
                attendence_to_roll
        };
        SQLiteDatabase db2 = this.getReadableDatabase();
        // selection criteria
        String selection = attendence_to_roll + " = ?" + " AND " + attendence_subject + " = ?";

        // selection arguments
        String selectQuery = "SELECT *" + " FROM " + Table_Attendence + " WHERE "
                + attendence_to_roll + " = '" + productDetail.getMarks_to() + "' AND " + attendence_subject + " = '" + productDetail.getSubject() + "'";
        Cursor cursor = db2.rawQuery(selectQuery, null);

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
       /* Cursor cursor = db2.query(Table_Attendence, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

        if (cursor.getCount() > 0) {
            cv.put(attendence_id, Integer.parseInt(productDetail.getId()));
            i = db.update(Table_Attendence, cv, attendence_id + " = ?", new String[]{String.valueOf(productDetail.getId())});
        } else {*/
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            System.out.println("DatabaseHelper.addMarks " + id);
            i = db.update(Table_Attendence, cv, attendence_id + " = " + id, null);
        }else{
            i = db.insert(Table_Attendence, null, cv);
        }

        //close the database to avoid any leak
        db.close();

        return i;
    }

    public void deleteStudentAttendance(String rollno) {
        //Open the database
        SQLiteDatabase database = this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + Table_Attendence + " WHERE " + attendence_id + "= '" + rollno + "'");

        //Close the database
        database.close();
    }

    public void deleteTeacher(String email) {
        //Open the database
        SQLiteDatabase database = this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + Table_Faculty + " WHERE " + facultyemail + "= '" + email + "'");

        //Close the database
        database.close();
    }

    public ArrayList<Marks> getAllMarks(String mdepartment, String mbatch, String mmst, String msubject) {
        String selectAllContact;
        if ("Select Department".equalsIgnoreCase(mdepartment))
            mdepartment = null;
        if ("Select Batch".equalsIgnoreCase(mbatch))
            mbatch = null;

        if (mdepartment != null && mbatch != null) {
            selectAllContact = "SELECT * FROM " + Table_Marks + " INNER JOIN " + Table_Student + " ON " + Table_Student + "." + rollno + "=" + Table_Marks + "." + marks_to
                    + " WHERE " + Table_Student + "." + department + "='" + mdepartment + "' AND " + Table_Student + "." + batch + "='" + mbatch + "' AND "
                    + Table_Marks + "." + marks_mst + "='" + mmst + "'";
        } else if (mdepartment != null) {
            selectAllContact = "SELECT * FROM " + Table_Marks + " INNER JOIN " + Table_Student + " ON " + Table_Student + "." + rollno + "=" + Table_Marks + "." + marks_to
                    + " WHERE " + Table_Student + "." + department + "='" + mdepartment + "' AND "
                    + Table_Marks + "." + marks_mst + "='" + mmst +"'";
        } else if (mbatch != null) {
            selectAllContact = "SELECT * FROM " + Table_Marks + " INNER JOIN " + Table_Student + " ON " + Table_Student + "." + rollno + "=" + Table_Marks + "." + marks_to
                    + " WHERE " + Table_Student + "." + batch + "='" + mbatch + "' AND "
                    + Table_Marks + "." + marks_mst + "='" + mmst + "'";
        } else {
            selectAllContact = "SELECT * FROM " + Table_Marks + " WHERE " + marks_mst + "='" + mmst + "'";
        }

//        String selectAllContact = "SELECT * FROM " + Table_Marks;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setMarks_obtained(cursor.getString(2));
                address.setSubject(cursor.getString(4));
                address.setMarks_to(cursor.getString(5));
                address.setMst(cursor.getString(6));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Marks> getMarksByDepartmentForAdmin(String departmentName) {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM (SELECT *, " + Table_Student + "." + contact + " FROM " + Table_Marks + " INNER JOIN " + Table_Student + " ON "
                + Table_Marks + "." + marks_id + "=" + Table_Student + "." + rollno + ") WHERE " + department + "='" + departmentName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setMarks_obtained(cursor.getString(2));
                address.setSubject(cursor.getString(4));
                address.setMarks_to(cursor.getString(5));
                address.setMst(cursor.getString(6));
                address.setContact(cursor.getString(7));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Marks> getMarksByBatchForAdmin(String batchName) {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM (SELECT *, " + Table_Student + "." + contact + " FROM " + Table_Marks + " INNER JOIN " + Table_Student + " ON "
                + Table_Marks + "." + marks_id + "=" + Table_Student + "." + rollno + ") WHERE " + batch + "='" + batchName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setMarks_obtained(cursor.getString(2));
                address.setSubject(cursor.getString(4));
                address.setMarks_to(cursor.getString(5));
                address.setMst(cursor.getString(6));
                address.setContact(cursor.getString(7));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Marks> getMyMarks(String roll) {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM " + Table_Marks + " Where marksto = '" + roll + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setMarks_obtained(cursor.getString(2));
                address.setSubject(cursor.getString(4));
                address.setMarks_to(cursor.getString(5));
                address.setMst(cursor.getString(6));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Marks> getAllAttendance(String mdepartment, String mbatch, String msubject) {
        String selectAllContact;
        if ("Select Department".equalsIgnoreCase(mdepartment))
            mdepartment = null;
        if ("Select Batch".equalsIgnoreCase(mbatch))
            mbatch = null;

        if (mdepartment != null && mbatch != null) {
            selectAllContact = "SELECT * FROM " + Table_Attendence + " INNER JOIN " + Table_Student + " ON " + Table_Student + "." + rollno + "=" + Table_Attendence + "." + attendence_to_roll
                    + " WHERE " + Table_Student + "." + department + "='" + mdepartment + "' AND " + Table_Student + "." + batch + "='" + mbatch+"'";
        } else if (mdepartment != null) {
            selectAllContact = "SELECT * FROM " + Table_Attendence + " INNER JOIN " + Table_Student + " ON " + Table_Student + "." + rollno + "=" + Table_Attendence + "." + attendence_to_roll
                    + " WHERE " + Table_Student + "." + department + "='" + mdepartment + "'";
        } else if (mbatch != null) {
            selectAllContact = "SELECT * FROM " + Table_Attendence + " INNER JOIN " + Table_Student + " ON " + Table_Student + "." + rollno + "=" + Table_Attendence + "." + attendence_to_roll
                    + " WHERE " + Table_Student + "." + batch + "='" + mbatch + "'";
        } else {
            selectAllContact = "SELECT * FROM " + Table_Attendence;
        }
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
//        String selectAllContact = "SELECT * FROM " + Table_Attendence + " Where teacher = '" + SharedPrefs.getString(DatabaseHelper.context, SharedPrefs.email) + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setMarks_obtained(cursor.getString(2));
                address.setSubject(cursor.getString(3));
                address.setMarks_to(cursor.getString(4));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Marks> getAllAttendanceForAdmin() {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM " + Table_Attendence;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setMarks_obtained(cursor.getString(2));
                address.setSubject(cursor.getString(3));
                address.setMarks_to(cursor.getString(4));


                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Marks> getAttendanceByDepartmentForAdmin(String departmentName) {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM (SELECT *, " + Table_Student + "." + contact + " FROM " + Table_Attendence + " INNER JOIN " + Table_Student + " ON "
                + Table_Attendence + "." + attendence_id + "=" + Table_Student + "." + rollno + ") WHERE " + department + "='" + departmentName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setMarks_obtained(cursor.getString(2));
                address.setSubject(cursor.getString(3));
                address.setMarks_to(cursor.getString(4));
                address.setContact(cursor.getString(6));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Marks> getAttendanceByBatchForAdmin(String batchName) {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM (SELECT *, " + Table_Student + "." + contact + " FROM " + Table_Attendence + " INNER JOIN " + Table_Student + " ON "
                + Table_Attendence + "." + attendence_id + "=" + Table_Student + "." + rollno + ") WHERE " + batch + "='" + batchName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setMarks_obtained(cursor.getString(2));
                address.setSubject(cursor.getString(3));
                address.setMarks_to(cursor.getString(4));
                address.setContact(cursor.getString(6));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Marks> getMyAttendance(String roll) {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM " + Table_Attendence + " Where roll = '" + roll + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setMarks_obtained(cursor.getString(2));
                address.setSubject(cursor.getString(3));
                address.setMarks_to(cursor.getString(4));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Marks> getMyAnnoucements() {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM " + Table_Announcements + " Where announce_by = '" + SharedPrefs.getString(DatabaseHelper.context, SharedPrefs.email) + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setSubject(cursor.getString(2));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Marks> getAllAnnoucements() {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM " + Table_Announcements;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Marks> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Marks address = new Marks();
                address.setId("" + cursor.getInt(0));
                address.setTotal_marks(cursor.getString(1));
                address.setSubject(cursor.getString(2));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Faculty> getAllTeachersForAdmin() {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM " + Table_Faculty;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Faculty> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Faculty address = new Faculty();
                address.setId("" + cursor.getInt(0));
                address.setName(cursor.getString(1));
                address.setEmail(cursor.getString(2));
                address.setPassword(cursor.getString(3));
                address.setSubject(cursor.getString(4));
                address.setDepartment(cursor.getString(5));
                address.setContact(cursor.getString(6));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Faculty> getTeachersByDepartmentForAdmin(String departmentName) {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM " + Table_Faculty + " WHERE " + facultydepartment + "='" + departmentName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Faculty> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Faculty address = new Faculty();
                address.setId("" + cursor.getInt(0));
                address.setName(cursor.getString(1));
                address.setEmail(cursor.getString(2));
                address.setPassword(cursor.getString(3));
                address.setSubject(cursor.getString(4));
                address.setDepartment(cursor.getString(5));
                address.setContact(cursor.getString(6));
                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }

    public ArrayList<Faculty> getTeachersBySubjectForAdmin(String subjectName) {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM " + Table_Faculty + " WHERE " + facultysubject + "='" + subjectName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Faculty> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Faculty address = new Faculty();
                address.setId("" + cursor.getInt(0));
                address.setName(cursor.getString(1));
                address.setEmail(cursor.getString(2));
                address.setPassword(cursor.getString(3));
                address.setSubject(cursor.getString(4));
                address.setDepartment(cursor.getString(5));
                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }


    public ArrayList<Student> getAllStudents() {
        // List<Contact> contactList=new ArrayList<Contact>();
        //query to select all contacts from table
        String selectAllContact = "SELECT * FROM " + Table_Student;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllContact, null);
        // move cursor to first and use loop to add all the contacts to list
        ArrayList<Student> allItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Student address = new Student();
                address.setId("" + cursor.getInt(0));
                address.setName(cursor.getString(1));
                address.setRollno(cursor.getString(2));
                address.setEmail(cursor.getString(3));
//                address.setPassword(cursor.getString(4));
                address.setDepartment(cursor.getString(5));
                address.setBatch(cursor.getString(6));
                address.setContact(cursor.getString(7));

                allItems.add(address);
            } while (cursor.moveToNext());
        }
        return allItems;
    }


    public boolean checkUser(String emaill, String passwordd) {

        // array of columns to fetch
        String[] columns = {
                name, rollno
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = email + " = ?" + " AND " + password + " = ?";

        // selection arguments
        String[] selectionArgs = {emaill, passwordd};

        // query user table with conditions
        Cursor cursor = db.query(Table_Student, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();


        if (cursorCount > 0) {
            cursor.moveToFirst();
            SharedPrefs.setStringData(context, SharedPrefs.roll, cursor.getString(1));
            SharedPrefs.setStringData(context, SharedPrefs.name, cursor.getString(0));
            cursor.close();
            db.close();
            return true;
        }

        cursor.close();
        db.close();
        return false;
    }

    public boolean checkFaculty(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                facultyname, facultysubject, department
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = facultyemail + " = ?" + " AND " + facultypass + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(Table_Faculty, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();


        if (cursorCount > 0) {
            cursor.moveToFirst();
            SharedPrefs.setStringData(context, SharedPrefs.subject, cursor.getString(1));
            SharedPrefs.setStringData(context, SharedPrefs.name, cursor.getString(0));
            SharedPrefs.setStringData(context, SharedPrefs.department, cursor.getString(2));

            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean checkHod(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                hodname, hoddepartment
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = hodemail + " = ?" + " AND " + hodpass + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(Table_HOD, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();


        if (cursorCount > 0) {
            cursor.moveToFirst();
            SharedPrefs.setStringData(context, SharedPrefs.department, cursor.getString(1));
            SharedPrefs.setStringData(context, SharedPrefs.name, cursor.getString(0));

            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean checkCollegeHead(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                collegeHeadname
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = collegeHeademail + " = ?" + " AND " + collegeHeadpass + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(Table_CollegeHead, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();


        if (cursorCount > 0) {
            cursor.moveToFirst();
            SharedPrefs.setStringData(context, SharedPrefs.name, cursor.getString(0));

            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }


    public long createDatesheet(DatesheetItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long insertResult = 0;
        cv.put(datesheet_department, item.getDepartment());
        cv.put(datesheet_batch, item.getBatch());
        cv.put(datesheet_subject, item.getSubject());
        cv.put(datesheet_date, item.getDate());
        cv.put(datesheet_time, item.getTime());

        SQLiteDatabase dbRead = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Table_Datesheet + " WHERE " + datesheet_department + " ='" + department + "' AND " + datesheet_batch + " ='" + batch + "' AND " + datesheet_subject + "='" + item.getSubject() + "'";
        Cursor cursor = dbRead.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0)
            insertResult = db.insert(Table_Datesheet, null, cv);
        else
            insertResult = db.update(Table_Datesheet, cv, datesheet_department + "= ? AND " + datesheet_batch + "=? AND " + datesheet_subject + "=?"
                    , new String[]{item.getDepartment(), item.getBatch(), item.getSubject()});
        return insertResult;
    }

    public ArrayList<DatesheetItem> getDateSheet(String department, String batch) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Table_Datesheet + " WHERE " + datesheet_department + " ='" + department + "' AND " + datesheet_batch + " ='" + batch + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<DatesheetItem> datesheetItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                DatesheetItem datesheetItem = new DatesheetItem();
                datesheetItem.setDepartment(cursor.getString(1));
                datesheetItem.setBatch(cursor.getString(2));
                datesheetItem.setSubject(cursor.getString(3));
                datesheetItem.setDate(cursor.getString(4));
                datesheetItem.setTime(cursor.getString(5));
                datesheetItems.add(datesheetItem);
            } while (cursor.moveToNext());
        }
        return datesheetItems;
    }

    public long addTimeTable(TimeTableItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(timetable_department, item.getDepartment());
        cv.put(timetable_batch, item.getBatch());
        cv.put(timetable_path, item.getPath());
        long insertResult;
        String path = getTimeTable(item.getDepartment(), item.getBatch());
        if (path == null) {
            insertResult = db.insert(Table_TimeTable, null, cv);
        } else
            insertResult = db.update(Table_TimeTable, cv, timetable_department + "=? AND " + timetable_batch + "=?", new String[]{item.getDepartment(), item.getBatch()});
        return insertResult;
    }

    public String getTimeTable(String department, String batch) {
        String path = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Table_TimeTable + " WHERE " + timetable_department + " ='" + department + "' AND " + timetable_batch + " ='" + batch + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            path = cursor.getString(3);
        }
        return path;
    }
}
