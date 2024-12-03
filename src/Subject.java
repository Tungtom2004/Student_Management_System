package stu_mng_sys;

import java.io.*;
import java.util.*;

class Subject implements Serializable {
    private static final long serialVersionUID = 1L;
    private String subjectID;
    private String subjectName;
    public ArrayList<Student> studentList;

    Subject(String subjectID, String subjectName, ArrayList<Student> studentList) {
        this.subjectID = subjectID.toUpperCase();
        this.subjectName = subjectName;
        this.studentList = studentList;
    }
    
    public void subjectRegistering(String studentID) { // dang ki mon hoc
        for(Student student:studentList){
            if(!studentList.contains(studentID)){
                studentList.add(student); 
            }
        }
    }
    
     public void subjectCanceling(String studentID) {
        for(Student student:studentList){
            if(student.getStudentID().equals(studentID)){
                studentList.remove(studentID);
            }
        }
    }
    
    public String getSubjectID() {
        return subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }
}