package stu_mng_sys;

import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentID, fullName, DoB, gender, address, email, phoneNo, classID, major;
    private static int index = 1;

    Student(String fullName, String DoB, String gender, String address, String phoneNo, String classID, String major) {
        this.major = major;
        String[] date = DoB.trim().split("/");
        this.DoB = String.format("%02d/%02d/%04d", Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        this.studentID = setStudentID(major, DoB);
        this.fullName = nameNormalize(fullName);
        this.gender = gender;
        this.address = address;
        this.email = setEmail(studentID, fullName);
        this.phoneNo = phoneNo;
        this.classID = classID;
    }

    Student(String studentID, String fullName, String DoB, String gender, String address, String phoneNo, String classID, String major) {
        this.studentID = studentID;
        this.fullName = fullName;
        this.DoB = DoB;
        this.gender = gender;
        this.address = address;
        this.email = setEmail(studentID, fullName);
        this.phoneNo = phoneNo;
        this.classID = classID;
        this.major = major;
    }

    private static String nameNormalize(String fullName) {
        String[] words = fullName.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {  // Skip empty strings
                sb.append(Character.toUpperCase(w.charAt(0)));
                sb.append(w.substring(1).toLowerCase());
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public String setStudentID(String major, String DoB) {
        String[] date = DoB.trim().split("/");
        String yearID = Integer.toString((Integer.parseInt(date[2]) + 18)).substring(2, 4);
        String[] majorName = major.trim().split(" ");
        String majorID = "";
        for (int i = 0; i <= 1; i++) majorID += majorName[i].charAt(0);
        return ("B" + yearID + "DC" + majorID + String.format("%04d", index++)).toUpperCase();
    }

    public String setEmail(String studentID, String fullName) {
        String[] name = fullName.trim().split(" ");
        String userName = name[name.length - 1];
        for (int i = 0; i < name.length - 1; i++) {
            userName += name[i].charAt(0);
        }
        return (userName + "." + studentID + "@stu.ptit.edu.vn").toLowerCase();
    }
    
    
    
    public String getStudentID() {
        return studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDoB() {
        return DoB;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getClassID() {
        return classID;
    }

    public String getMajor() {
        return major;
    }
}