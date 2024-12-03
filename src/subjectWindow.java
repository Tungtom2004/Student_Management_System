package stu_mng_sys;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class subjectWindow extends JFrame {
    private final JLabel subjectLabel = new JLabel("Subject Management");
    private final JButton addNewSubjectButton = new JButton("Add New Subject");
    private final JButton newRegistrationButton = new JButton("New/Cancel Registration");
    private final JTable subjectTable = new JTable(new DefaultTableModel(new Object[][]{}, new Object[]{"Student ID", "Full Name", "Class ID", "Subject ID", "Subject Name"}));
    private final JLabel subjectFilterLabel = new JLabel("Search for Subject ID");
    private final JTextField subjectFilterTextField = new JTextField();
    private final JButton subjectFilterButton = new JButton("Search");
    private final JFrame newRegistrationFormFrame = new JFrame("New/Cancel Registration");
    private final JFrame newSubjectFormFrame = new JFrame("Add New Subject");
    private ArrayList<Subject> subjectArrayList = new ArrayList<>();
    private File subjectFile = new File("subject.in");
    private File scoresFile = new File("scores.in");
    private File studentFile = new File("student.in");
    private final Stu_Mng_Sys mainApp;

    public subjectWindow(Stu_Mng_Sys mainApp) {
        //Tạo cửa sổ
        super("Subject Management");
        this.mainApp = mainApp;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        //Tạo nội dung trong cửa sổ này
        subjectLabel.setFont(new Font("Arial", Font.BOLD, 40));
        subjectLabel.setBounds(440, 50, 500, 60);
        add(subjectLabel);

        //Nút thêm/xóa đăng ký môn học
        newRegistrationButton.setBounds(50, 150, 200, 30);
        newRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newRegistrationFormFrame.isVisible()) {
                    newRegistrationFormFrame.setVisible(true); //Hiển thị cửa sổ thêm/xóa đăng ký môn học
                    subjectRegistrationCancellation();
                }
            }
        });
        add(newRegistrationButton);

        //Bảng danh sách sinh viên theo học môn học
        JScrollPane studentScrollPane = new JScrollPane(subjectTable); //Create students' subjects table
        add(studentScrollPane);
        studentScrollPane.setBounds(40, 200, 1200, 420);

        //Tải toàn bộ dữ liệu từ file vào mảng subjectArrayList khi cửa sổ hoạt động
        if (subjectFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(subjectFile))){
                subjectArrayList = (ArrayList<Subject>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //Bộ lọc môn học
        subjectFilterLabel.setFont(new Font("Arial", Font.BOLD, 15));
        subjectFilterLabel.setBounds(350, 150, 200, 30);
        add(subjectFilterLabel);

        subjectFilterTextField.setBounds(530, 150, 200, 30);
        add(subjectFilterTextField);

        subjectFilterButton.setBounds(750, 150, 200, 30);
        subjectFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Tìm tất cả các bản ghi (danh sách sinh viên theo học môn học này) khớp với mã môn học đã cho
                DefaultTableModel model = (DefaultTableModel) subjectTable.getModel();
                model.setRowCount(0);
                String text = subjectFilterTextField.getText();
                for (Subject subject : subjectArrayList) {
                    if (text.equals(subject.getSubjectID())) {
                        for (int i = 1; i < subject.studentList.size(); i++) {
                            String studentID = subject.studentList.get(i).getStudentID();
                            String fullName = subject.studentList.get(i).getFullName();
                            String classID = subject.studentList.get(i).getClassID();
                            model.addRow(new Object[]{studentID, fullName, classID, subject.getSubjectID(), subject.getSubjectName()});
                        }
                        break;
                    }
                }
            }
        });
        add(subjectFilterButton);

        //Nút thêm mới môn học
        addNewSubjectButton.setBounds(1030, 150, 200, 30);
        addNewSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newSubjectFormFrame.isVisible()) {
                    newSubjectFormFrame.setVisible(true); //Show add new subject window
                    addNewSubject();
                }
            }
        });
        add(addNewSubjectButton);

        //Cửa sổ thêm/xóa đăng ký môn học
        newRegistrationFormFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newRegistrationFormFrame.setSize(640, 480);
        newRegistrationFormFrame.setLayout(null);
        newRegistrationFormFrame.setLocationRelativeTo(null);
        newRegistrationFormFrame.setResizable(false);

        //Cửa số thêm mới môn học
        newSubjectFormFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newSubjectFormFrame.setSize(640, 480);
        newSubjectFormFrame.setLayout(null);
        newSubjectFormFrame.setLocationRelativeTo(null);
        newSubjectFormFrame.setResizable(false);
    }

    public void subjectRegistrationCancellation() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setSize(640, 480);
        newRegistrationFormFrame.add(formPanel);

        JLabel newRegistrationFormLabel = new JLabel("New/Cancel Registration");
        newRegistrationFormLabel.setFont(new Font("Arial", Font.BOLD, 30));
        newRegistrationFormLabel.setBounds(140, 20, 400, 40);
        formPanel.add(newRegistrationFormLabel);

        JLabel studentIDLabel = new JLabel("Student ID:");
        studentIDLabel.setFont(new Font("Arial", Font.BOLD, 15));
        studentIDLabel.setBounds(100, 180, 100, 30);
        formPanel.add(studentIDLabel);

        JLabel subjectIDLabel = new JLabel("Subject ID:");
        subjectIDLabel.setFont(new Font("Arial", Font.BOLD, 15));
        subjectIDLabel.setBounds(100, 230, 100, 30);
        formPanel.add(subjectIDLabel);

        JTextField studentIDField = new JTextField();
        studentIDField.setBounds(250, 180, 290, 30);
        formPanel.add(studentIDField);

        JTextField subjectIDField = new JTextField();
        subjectIDField.setBounds(250, 230, 290, 30);
        formPanel.add(subjectIDField);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 15));
        submitButton.setBounds(220, 380, 200, 30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Khi không có trường trống, lưu thông tin vào file dữ liệu và hiển thị tin nhắn đã thêm/xóa đăng ký thành công.
                //Nếu dữ liệu đã tồn tại, xóa đăng ký môn học. Ngược lại, thêm đăng ký môn học.

                String studentIDQuery = studentIDField.getText();
                String subjectIDQuery = subjectIDField.getText();

                if (subjectIDQuery.isEmpty() || studentIDQuery.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.");
                }

                //Tải toàn bộ dữ liệu sinh viên vào một mảng tạm studentArrayList
                ArrayList<Student> studentArrayList = new ArrayList<>();
                if (studentFile.exists()) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(studentFile))){
                        studentArrayList = (ArrayList<Student>) ois.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }

                //Tải toàn bộ dữ liệu điểm vào một mảng tạm scoresArrayList
                ArrayList<Scores> scoresArrayList = new ArrayList<>();
                if (scoresFile.exists()) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(scoresFile))){
                        scoresArrayList = (ArrayList<Scores>) ois.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }

                //Đoạn code sau đây kiểm tra dữ liệu có tồn tại không và thêm/xóa bộ điểm môn học

                for (Subject subject : subjectArrayList) {
                    if (subject.getSubjectID().equals(subjectIDQuery)) {
                        int cancelled = 0;
                        for (Student studentInSubjectList : subject.studentList) {
                            if (studentInSubjectList.getStudentID().equals(studentIDQuery)) {
                                cancelled = 1;
                                subject.studentList.remove(studentInSubjectList);
                                for (Scores scores: scoresArrayList) {
                                    if (scores.getStudent().getStudentID().equals(studentIDQuery) && scores.getSubject().getSubjectID().equals(subjectIDQuery)) {
                                        scoresArrayList.remove(scores);
                                        JOptionPane.showMessageDialog(null, "Cancelled sucessfully.");
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if (cancelled == 0) {
                            for (Student student : studentArrayList) {
                                if (student.getStudentID().equals(studentIDQuery)) {
                                    subject.studentList.add(student);
                                    scoresArrayList.add(new Scores(student, subject));
                                    JOptionPane.showMessageDialog(null, "Registered sucessfully.");
                                }
                            }
                        }
                    }
                }

                //Kết thúc đoạn code

                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(scoresFile))){
                    oos.writeObject(scoresArrayList);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                saveDataFromList(subjectArrayList);
                studentIDField.setText("");
                subjectIDField.setText("");
            }
        });
        formPanel.add(submitButton);
    }

    public void addNewSubject() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setSize(640, 480);
        newSubjectFormFrame.add(formPanel);

        JLabel newSubjectFormLabel = new JLabel("Add New Subject");
        newSubjectFormLabel.setFont(new Font("Arial", Font.BOLD, 30));
        newSubjectFormLabel.setBounds(200, 20, 300, 40);
        formPanel.add(newSubjectFormLabel);

        JLabel subjectNameLabel = new JLabel("Subject Name:");
        subjectNameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        subjectNameLabel.setBounds(100, 180, 150, 30);
        formPanel.add(subjectNameLabel);

        JLabel subjectIDLabel = new JLabel("Subject ID:");
        subjectIDLabel.setFont(new Font("Arial", Font.BOLD, 15));
        subjectIDLabel.setBounds(100, 230, 100, 30);
        formPanel.add(subjectIDLabel);

        JTextField subjectNameField = new JTextField();
        subjectNameField.setBounds(250, 180, 290, 30);
        formPanel.add(subjectNameField);

        JTextField subjectIDField = new JTextField();
        subjectIDField.setBounds(250, 230, 290, 30);
        formPanel.add(subjectIDField);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 15));
        submitButton.setBounds(220, 380, 200, 30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Khi không có trường trống, lưu thông tin vào file dữ liệu và hiển thị tin nhắn đã thêm thành công.

                String subjectID = subjectIDField.getText().toUpperCase();
                String subjectName = subjectNameField.getText();

                if (subjectID.isEmpty() || subjectName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.");
                }

                else {
                    ArrayList<Student> studentArrayList = new ArrayList<>();
                    Student fakeStudent = new Student("B22DCCN0000","John Smith", "01/01/1970", "", "", "", "", "");
                    studentArrayList.add(fakeStudent);
                    Subject newSubject = new Subject(subjectID, subjectName, studentArrayList);
                    subjectArrayList.add(newSubject);
                    saveDataFromList(subjectArrayList);
                    JOptionPane.showMessageDialog(null, "Added successfully.");
                    subjectIDField.setText("");
                    subjectNameField.setText("");
                }
            }
        });
        formPanel.add(submitButton);
    }

    //Hàm này dùng để lưu dữ liệu vào file từ mảng subjectArrayList
    public void saveDataFromList(ArrayList<Subject> subjectArrayList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(subjectFile))){
            oos.writeObject(subjectArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}