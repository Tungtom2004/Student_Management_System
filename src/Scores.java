package stu_mng_sys;

import java.io.*;

class Scores implements Serializable {
    private static final long serialVersionUID = 1L;
    private float attendanceScore, processScore, midtermScore, finalScore, GPA;
    private Student student;
    private Subject subject;

    Scores(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
    }

    Scores(Student student, Subject subject, float attendanceScore, float processScore, float midtermScore, float finalScore) {
        this.student = student;
        this.subject = subject;
        setAttendanceScore(attendanceScore);
        setProcessScore(processScore);
        setMidtermScore(midtermScore);
        setFinalScore(finalScore);
        this.GPA = attendanceScore * 10 / 100 + processScore * 10 / 100 + midtermScore * 20 / 100 + finalScore * 60 / 100;
    }

    public float getAttendanceScore() {
        return attendanceScore;
    }

    public void setAttendanceScore(float attendanceScore) {
        if (0 <= attendanceScore && attendanceScore <= 10) this.attendanceScore = attendanceScore;
    }

    public float getProcessScore() {
        return processScore;
    }

    public void setProcessScore(float processScore) {
        if (0 <= processScore && processScore <= 10) this.processScore = processScore;
    }

    public float getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(float midtermScore) {
        if (0 <= midtermScore && midtermScore <= 10) this.midtermScore = midtermScore;
    }

    public float getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(float finalScore) {
        if (0 <= finalScore && finalScore <= 10) this.finalScore = finalScore;
    }

    public float getGPA() {
        return GPA;
    }

    public void setGPA(float GPA) {
        if (0 <= GPA && GPA <= 10) this.GPA = GPA;
    }

    public Student getStudent() {
        return student;
    }

    public Subject getSubject() {
        return subject;
    }
}