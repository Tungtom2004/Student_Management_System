package stu_mng_sys;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class scoresWindow extends JFrame {
    private final JLabel scoresLabel = new JLabel("Scores Management");
    private final JButton modifyScoresButton = new JButton("Modify Scores");
    private final JTable scoresTable = new JTable(new DefaultTableModel(new Object[][]{}, new Object[]{"Full Name", "Class ID", "Subject ID", "Subject Name", "Attendance Score", "Process Score", "Midterm Score", "Final Score", "GPA"}));
    private final JLabel studentFilterLabel = new JLabel("Search for Student ID");
    private final JTextField studentFilterTextField = new JTextField();
    private final JButton studentFilterButton = new JButton("Search");
    private final JFrame modifyScoresFormFrame = new JFrame("Modify Scores");
    private ArrayList<Scores> scoresArrayList = new ArrayList<>();
    private File scoresFile = new File("scores.in");
    private final Stu_Mng_Sys mainApp;

    public scoresWindow(Stu_Mng_Sys mainApp) {
        //Tạo cửa sổ
        super("Scores Management");
        this.mainApp = mainApp;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        //Tạo nội dung trong cửa sổ này
        scoresLabel.setFont(new Font("Arial", Font.BOLD, 40));
        scoresLabel.setBounds(440, 50, 500, 60);
        add(scoresLabel);

        //Nút sửa điểm
        modifyScoresButton.setBounds(50, 150, 200, 30);
        modifyScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!modifyScoresFormFrame.isVisible()) {
                    modifyScoresFormFrame.setVisible(true); //Cửa sổ sửa điểm
                    modifyScores();
                }
            }
        });
        add(modifyScoresButton);

        //Bảng hiển thị các môn sinh viên đang theo học
        JScrollPane studentScrollPane = new JScrollPane(scoresTable);
        add(studentScrollPane);
        studentScrollPane.setBounds(40, 200, 1200, 420);

        //Tải toàn bộ dữ liệu từ file vào mảng scoresArrayList khi cửa sổ hoạt động
        if (scoresFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(scoresFile))){
                scoresArrayList = (ArrayList<Scores>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //Bộ lọc sinh viên
        studentFilterLabel.setFont(new Font("Arial", Font.BOLD, 15));
        studentFilterLabel.setBounds(350, 150, 200, 30);
        add(studentFilterLabel);

        studentFilterTextField.setBounds(530, 150, 200, 30);
        add(studentFilterTextField);

        studentFilterButton.setBounds(750, 150, 200, 30);
        studentFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Tìm tất cả các bản ghi (danh sách môn học sinh viên này đang theo học) khớp với mã môn học đã cho
                if (scoresFile.exists()) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(scoresFile))){
                        scoresArrayList = (ArrayList<Scores>) ois.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
                DefaultTableModel model = (DefaultTableModel) scoresTable.getModel();
                model.setRowCount(0);
                String text = studentFilterTextField.getText();
                for (Scores scores : scoresArrayList) {
                    if (text.equals(scores.getStudent().getStudentID())) {
                        String fullName = scores.getStudent().getFullName();
                        String classID = scores.getStudent().getClassID();
                        String subjectID = scores.getSubject().getSubjectID();
                        String subjectName = scores.getSubject().getSubjectName();
                        float attendanceScore = scores.getAttendanceScore();
                        float processScore = scores.getProcessScore();
                        float midtermScore = scores.getMidtermScore();
                        float finalScore = scores.getFinalScore();
                        float GPA = scores.getGPA();
                        model.addRow(new Object[]{fullName, classID, subjectID, subjectName, attendanceScore, processScore, midtermScore, finalScore, GPA});
                    }
                }
            }
        });
        add(studentFilterButton);

        //Cửa sổ sửa điểm
        modifyScoresFormFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        modifyScoresFormFrame.setSize(640, 480);
        modifyScoresFormFrame.setLayout(null);
        modifyScoresFormFrame.setLocationRelativeTo(null);
        modifyScoresFormFrame.setResizable(false);
    }

    public void modifyScores() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setSize(640, 480);
        modifyScoresFormFrame.add(formPanel);

        JLabel modifyScoresFormLabel = new JLabel("Modify Student's Scores");
        modifyScoresFormLabel.setFont(new Font("Arial", Font.BOLD, 30));
        modifyScoresFormLabel.setBounds(140, 20, 400, 40);
        formPanel.add(modifyScoresFormLabel);

        JLabel studentIDLabel = new JLabel("Student ID:");
        studentIDLabel.setFont(new Font("Arial", Font.BOLD, 15));
        studentIDLabel.setBounds(100, 80, 150, 30);
        formPanel.add(studentIDLabel);

        JLabel subjectIDLabel = new JLabel("Subject ID:");
        subjectIDLabel.setFont(new Font("Arial", Font.BOLD, 15));
        subjectIDLabel.setBounds(100, 130, 150, 30);
        formPanel.add(subjectIDLabel);

        JLabel attendanceScoreLabel = new JLabel("Attendance Score:");
        attendanceScoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        attendanceScoreLabel.setBounds(100, 180, 150, 30);
        formPanel.add(attendanceScoreLabel);

        JLabel processScoreLabel = new JLabel("Process Score:");
        processScoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        processScoreLabel.setBounds(100, 230, 150, 30);
        formPanel.add(processScoreLabel);

        JLabel midtermScoreLabel = new JLabel("Mid-term Score:");
        midtermScoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        midtermScoreLabel.setBounds(100, 280, 150, 30);
        formPanel.add(midtermScoreLabel);

        JLabel finalScoreLabel = new JLabel("Final Score:");
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        finalScoreLabel.setBounds(100, 330, 150, 30);
        formPanel.add(finalScoreLabel);

        JTextField studentIDField = new JTextField();
        studentIDField.setBounds(250, 80, 140, 30);
        formPanel.add(studentIDField);

        JTextField subjectIDField = new JTextField();
        subjectIDField.setBounds(250, 130, 140, 30);
        formPanel.add(subjectIDField);

        JTextField attendanceScoreField = new JTextField();
        attendanceScoreField.setBounds(250, 180, 290, 30);
        formPanel.add(attendanceScoreField);

        JTextField processScoreField = new JTextField();
        processScoreField.setBounds(250, 230, 290, 30);
        formPanel.add(processScoreField);

        JTextField midtermScoreField = new JTextField();
        midtermScoreField.setBounds(250, 280, 290, 30);
        formPanel.add(midtermScoreField);

        JTextField finalScoreField = new JTextField();
        finalScoreField.setBounds(250, 330, 290, 30);
        formPanel.add(finalScoreField);

        JButton findStudentButton = new JButton("Find Student");
        findStudentButton.setBounds(400, 105, 140, 30);
        findStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Nếu cả mã sinh viên và mã môn học đều tồn tại, hiển toàn bộ thông tin trong các trường
                String studentIDQuery = studentIDField.getText();
                String subjectIDQuery = subjectIDField.getText();
                for (Scores scores : scoresArrayList) {
                    if (scores.getStudent().getStudentID().equals(studentIDQuery) && scores.getSubject().getSubjectID().equals(subjectIDQuery)) {
                        attendanceScoreField.setText(String.valueOf(scores.getAttendanceScore()));
                        processScoreField.setText(String.valueOf(scores.getProcessScore()));
                        midtermScoreField.setText(String.valueOf(scores.getMidtermScore()));
                        finalScoreField.setText(String.valueOf(scores.getFinalScore()));
                        break;
                    }
                    else {
                        attendanceScoreField.setText("");
                        processScoreField.setText("");
                        midtermScoreField.setText("");
                        finalScoreField.setText("");
                    }
                }
            }
        });
        formPanel.add(findStudentButton);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 15));
        submitButton.setBounds(220, 380, 200, 30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Khi không có trường trống, lưu thông tin vào file dữ liệu và hiển thị tin nhắn đã thay đổi thành công.

                String studentIDQuery = studentIDField.getText();
                String subjectIDQuery = subjectIDField.getText();

                for (Scores scores : scoresArrayList) {
                    if (scores.getStudent().getStudentID().equals(studentIDQuery) && scores.getSubject().getSubjectID().equals(subjectIDQuery)) {
                        float attendanceScore = Float.parseFloat(attendanceScoreField.getText());
                        float processScore = Float.parseFloat(processScoreField.getText());
                        float midtermScore = Float.parseFloat(midtermScoreField.getText());
                        float finalScore = Float.parseFloat(finalScoreField.getText());
                        float GPA = attendanceScore * 10 / 100 + processScore * 10 / 100 + midtermScore * 20 / 100 + finalScore * 60 / 100;
                        scores.setAttendanceScore(attendanceScore);
                        scores.setProcessScore(processScore);
                        scores.setMidtermScore(midtermScore);
                        scores.setFinalScore(finalScore);
                        scores.setGPA(GPA);
                        break;
                    }
                }

                saveDataFromList(scoresArrayList);
                JOptionPane.showMessageDialog(null, "Modified successfully.");
                attendanceScoreField.setText("");
                processScoreField.setText("");
                midtermScoreField.setText("");
                finalScoreField.setText("");
            }
        });
        formPanel.add(submitButton);
    }

    //Hàm này dùng để lưu dữ liệu vào file từ mảng scoresArrayList
    public void saveDataFromList(ArrayList<Scores> scoresArrayList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(scoresFile))){
            oos.writeObject(scoresArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}