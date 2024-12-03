package stu_mng_sys;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class classesWindow extends JFrame {
    private final JLabel classesLabel = new JLabel("Classes Management");
    private final JButton addClassButton = new JButton("Add New Class");
    private final JButton viewClassesListButton = new JButton("View Classes List");
    private final JTable classTable = new JTable(new DefaultTableModel(new Object[][]{}, new Object[]{"Student ID", "Full Name", "Date of Birth", "Email", "Phone Number"}));
    private final JLabel classFilterLabel = new JLabel("Search for Class ID");
    private final JTextField classFilterTextField = new JTextField();
    private final JButton classFilterButton = new JButton("Search");
    private final JFrame newClassFormFrame = new JFrame("Add New Class");
    private final JFrame viewClassesListFormFrame = new JFrame("Classes List");
    public ArrayList<Classes> classesArrayList = new ArrayList<>();
    private File classesFile = new File("classes.in");
    private final Stu_Mng_Sys mainApp;

    public classesWindow(Stu_Mng_Sys mainApp) {
        //Tạo cửa sổ
        super("Classes Management");
        this.mainApp = mainApp;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        //Tạo nội dung trong cửa sổ này
        classesLabel.setFont(new Font("Arial", Font.BOLD, 40));
        classesLabel.setBounds(440, 50, 500, 60);
        add(classesLabel);

        //Nút thêm mới lớp học
        addClassButton.setBounds(50, 150, 200, 30);
        addClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newClassFormFrame.isVisible()) {
                    newClassFormFrame.setVisible(true); //Hiển thị cửa sổ thêm mới lớp học
                    addNewClass();
                }
            }
        });
        add(addClassButton);

        //Bảng thông tin sinh viên của lớp
        JScrollPane studentScrollPane = new JScrollPane(classTable);
        add(studentScrollPane);
        studentScrollPane.setBounds(40, 200, 1200, 420);

        //Tải toàn bộ dữ liệu từ file vào mảng classesArrayList khi cửa sổ hoạt động
        if (classesFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(classesFile))){
                classesArrayList = (ArrayList<Classes>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //Bộ lọc lớp
        classFilterLabel.setFont(new Font("Arial", Font.BOLD, 15));
        classFilterLabel.setBounds(350, 150, 200, 30);
        add(classFilterLabel);

        classFilterTextField.setBounds(530, 150, 200, 30);
        add(classFilterTextField);

        classFilterButton.setBounds(750, 150, 200, 30);
        classFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Tìm tất cả các bản ghi (danh sách sinh viên thuộc lớp này) khớp với mã lớp đã cho
                DefaultTableModel model = (DefaultTableModel) classTable.getModel();
                model.setRowCount(0);
                String text = classFilterTextField.getText();
                for (Classes classes : classesArrayList) {
                    if (text.equals(classes.getClassID())) {
                        for (Student student : classes.getStudentList()) {
                            String studentID = student.getStudentID();
                            String fullName = student.getFullName();
                            String DoB = student.getDoB();
                            String email = student.getEmail();
                            String phoneNo = student.getPhoneNo();
                            model.addRow(new Object[]{studentID, fullName, DoB, email, phoneNo});
                        }
                        break;
                    }
                }
            }
        });
        add(classFilterButton);

        //Nút hiển thị danh sách lớp học
        viewClassesListButton.setBounds(1030, 150, 200, 30);
        viewClassesListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!viewClassesListFormFrame.isVisible()) {
                    viewClassesListFormFrame.setVisible(true); //Hiển thị cửa sổ danh sách lớp học
                    viewClassesList();
                }
            }
        });
        add(viewClassesListButton);

        //Cửa sổ thêm mới lớp học
        newClassFormFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newClassFormFrame.setSize(640, 480);
        newClassFormFrame.setLayout(null);
        newClassFormFrame.setLocationRelativeTo(null);
        newClassFormFrame.setResizable(false);

        //Cửa sổ danh sách lớp học
        viewClassesListFormFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewClassesListFormFrame.setSize(640, 480);
        viewClassesListFormFrame.setLayout(null);
        viewClassesListFormFrame.setLocationRelativeTo(null);
        viewClassesListFormFrame.setResizable(false);
    }

    public void addNewClass() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setSize(640, 480);
        newClassFormFrame.add(formPanel);

        JLabel newStudentFormLabel = new JLabel("Add New Class");
        newStudentFormLabel.setFont(new Font("Arial", Font.BOLD, 30));
        newStudentFormLabel.setBounds(210, 20, 300, 40);
        formPanel.add(newStudentFormLabel);

        JLabel classIDLabel = new JLabel("Class ID:");
        classIDLabel.setFont(new Font("Arial", Font.BOLD, 15));
        classIDLabel.setBounds(100, 180, 100, 30);
        formPanel.add(classIDLabel);

        JLabel majorLabel = new JLabel("Major:");
        majorLabel.setFont(new Font("Arial", Font.BOLD, 15));
        majorLabel.setBounds(100, 230, 100, 30);
        formPanel.add(majorLabel);

        JTextField classIDField = new JTextField();
        classIDField.setBounds(250, 180, 290, 30);
        formPanel.add(classIDField);

        JTextField majorField = new JTextField();
        majorField.setBounds(250, 230, 290, 30);
        formPanel.add(majorField);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 15));
        submitButton.setBounds(220, 380, 200, 30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Khi không có trường trống, lưu thông tin vào file dữ liệu và hiển thị tin nhắn đã thêm thành công.
                //Tất cả các sinh viên có mã lớp đã cho khớp với mã lớp mới sẽ được tự động thêm vào lớp.

                String classID = classIDField.getText().toUpperCase();
                String major = majorField.getText();

                if (classID.isEmpty() || major.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.");
                }

                else {
                    ArrayList<Student> studentArrayList = new ArrayList<>();
                    ArrayList<Student> studentList = new ArrayList<>();
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student.in"))){
                        studentArrayList = (ArrayList<Student>) ois.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    for (Student student : studentArrayList) {
                        if (student.getClassID().equals(classID)) {
                            studentList.add(student);
                        }
                    }
                    Classes newClass = new Classes(classID, major, studentList);
                    classesArrayList.add(newClass);
                    saveDataFromList(classesArrayList);
                    JOptionPane.showMessageDialog(null, "Added successfully.");
                    classIDField.setText("");
                    majorField.setText("");
                }
            }
        });
        formPanel.add(submitButton);
    }

    public void viewClassesList() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setSize(640, 480);
        viewClassesListFormFrame.add(formPanel);

        JLabel viewClassesListFormLabel = new JLabel("Classes List");
        viewClassesListFormLabel.setFont(new Font("Arial", Font.BOLD, 30));
        viewClassesListFormLabel.setBounds(220, 20, 300, 40);
        formPanel.add(viewClassesListFormLabel);

        //Classes list, import from data file
        JTable classesTable = new JTable(new DefaultTableModel(new Object[][]{}, new Object[]{"Class ID", "Major"}));
        JScrollPane classesScrollPane = new JScrollPane(classesTable); //Create classes list table
        classesScrollPane.setBounds(40, 80, 550, 330);
        formPanel.add(classesScrollPane);

        DefaultTableModel model = (DefaultTableModel) classesTable.getModel();
        for (Classes classes : classesArrayList) {
            model.addRow(new Object[]{classes.getClassID(), classes.getMajor()});
        }
    }

    //Hàm này dùng để lưu dữ liệu vào file từ mảng classesArrayList
    public void saveDataFromList(ArrayList<Classes> classesArrayList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(classesFile))){
            oos.writeObject(classesArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}