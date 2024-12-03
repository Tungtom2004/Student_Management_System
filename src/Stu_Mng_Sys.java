package stu_mng_sys;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Stu_Mng_Sys extends JFrame {
    private final studentWindow stWindow;
    private final classesWindow clWindow;
    private final subjectWindow suWindow;
    private final scoresWindow scWindow;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Stu_Mng_Sys::new); //Giúp chương trình chạy ổn định trên đa luồng
    }

    public Stu_Mng_Sys() {
        super("Student Management System");

        //Tạo cửa sổ cho tất cả các chức năng
        stWindow = new studentWindow(this);
        clWindow = new classesWindow(this);
        suWindow = new subjectWindow(this);
        scWindow = new scoresWindow(this);

        //Tạo frame cho chương trình chính
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setLayout(null);

        //Tạo trang chủ
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 1280, 720);
        add(mainPanel);

        //Tạo nội dung trong trang chủ
        JLabel mainLabel = new JLabel("Student Management System");
        mainLabel.setFont(new Font("Arial", Font.BOLD, 60));
        mainLabel.setBounds(230, 80, 1000, 100);
        mainPanel.add(mainLabel);

        JButton studentButton = new JButton("Student Management");
        studentButton.setFont(new Font("Arial", Font.BOLD, 25));
        studentButton.setBounds(160, 280, 400, 100);
        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stWindow.setVisible(true); //Hiển thị cửa sổ Student Management
            }
        });
        mainPanel.add(studentButton);

        JButton classesButton = new JButton("Classes Management");
        classesButton.setFont(new Font("Arial", Font.BOLD, 25));
        classesButton.setBounds(720, 280, 400, 100);
        classesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clWindow.setVisible(true); //Hiển thị cửa sổ Classes Management
            }
        });
        mainPanel.add(classesButton);

        JButton subjectButton = new JButton("Subject Management");
        subjectButton.setFont(new Font("Arial", Font.BOLD, 25));
        subjectButton.setBounds(160, 450, 400, 100);
        subjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suWindow.setVisible(true); //Hiển thị cửa sổ Subject Management
            }
        });
        mainPanel.add(subjectButton);

        JButton scoresButton = new JButton("Scores Management");
        scoresButton.setFont(new Font("Arial", Font.BOLD, 25));
        scoresButton.setBounds(720, 450, 400, 100);
        scoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scWindow.setVisible(true); //Hiển thị cửa sổ Scores Management
            }
        });
        mainPanel.add(scoresButton);
    }
}