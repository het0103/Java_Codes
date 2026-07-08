//Practical 2 (b) :  Implement Student information system using JDBC


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentInformation {

    JFrame frame;

    JTextField txtName, txtEnroll, txtBranch, txtCgpa, txtSemester;
    JButton btnSubmit;

    Connection conn;

    StudentInformation() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/StudentInformation",
                    "root",
                    "123456");

            JOptionPane.showMessageDialog(null,
                    "Database Connected Successfully");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
                    "Database Connection Failed!\n" + e.getMessage());

            e.printStackTrace();
        }

        frame = new JFrame("Student Information");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 2, 5, 5));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new JLabel("Student Name"));
        txtName = new JTextField();
        frame.add(txtName);

        frame.add(new JLabel("Enrollment Number"));
        txtEnroll = new JTextField();
        frame.add(txtEnroll);

        frame.add(new JLabel("Branch"));
        txtBranch = new JTextField();
        frame.add(txtBranch);

        frame.add(new JLabel("CGPA"));
        txtCgpa = new JTextField();
        frame.add(txtCgpa);

        frame.add(new JLabel("Semester"));
        txtSemester = new JTextField();
        frame.add(txtSemester);

        frame.add(new JLabel(""));
        btnSubmit = new JButton("Submit");
        frame.add(btnSubmit);

        btnSubmit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    String sql = "INSERT INTO Student_Information VALUES (?,?,?,?,?)";

                    PreparedStatement pst = conn.prepareStatement(sql);

                    pst.setString(1, txtName.getText());
                    pst.setString(2, txtEnroll.getText());
                    pst.setString(3, txtBranch.getText());
                    pst.setString(4, txtCgpa.getText());
                    pst.setString(5, txtSemester.getText());

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(frame,
                            "Record Inserted Successfully");

                    pst.close();

                    txtName.setText("");
                    txtEnroll.setText("");
                    txtBranch.setText("");
                    txtCgpa.setText("");
                    txtSemester.setText("");

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(frame,
                            ex.getMessage());

                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {

        new StudentInformation();
    }
}