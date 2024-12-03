package stu_mng_sys;

import java.io.*;
import java.util.*;

class Classes implements Serializable {
    private static final long serialVersionUID = 1L;
    private String classID;
    private String major;
    private ArrayList<Student> studentList;

    Classes(String classID, String major, ArrayList<Student> studentList) {
        this.classID = classID;
        this.major = major;
        this.studentList = studentList;
    }

    public String getClassID() {
        return classID;
    }

    public String getMajor() {
        return major;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }
}