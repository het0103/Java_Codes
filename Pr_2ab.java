import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentInformation {

    JFrame frame;

    JTextField txtEnroll, txtName;
    JCheckBox cbJava, cbPython, cbWeb;
    JRadioButton rbStudy, rbPlacement;
    JToggleButton toggleRelocate;
    JComboBox<String> comboCity;
    JButton btnSubmit;

    Connection conn;

    StudentInformation() {

        // Database Connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/college",
                    "root",
                    "123456");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Database Connection Failed");
        }

        // Frame
        frame = new JFrame("Student Information");
        frame.setSize(500, 350);
        frame.setLayout(new GridLayout(7,2,5,5));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Enrollment
        frame.add(new JLabel("Enrollment No"));
        txtEnroll = new JTextField();
        frame.add(txtEnroll);

        // Name
        frame.add(new JLabel("Student Name"));
        txtName = new JTextField();
        frame.add(txtName);

        // Skills
        frame.add(new JLabel("Skills"));

        JPanel skillPanel = new JPanel();
        cbJava = new JCheckBox("Java");
        cbPython = new JCheckBox("Python");
        cbWeb = new JCheckBox("Web");

        skillPanel.add(cbJava);
        skillPanel.add(cbPython);
        skillPanel.add(cbWeb);

        frame.add(skillPanel);

        // Career
        frame.add(new JLabel("Career Choice"));

        JPanel radioPanel = new JPanel();

        rbStudy = new JRadioButton("Further Study");
        rbPlacement = new JRadioButton("Placement");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbStudy);
        bg.add(rbPlacement);

        radioPanel.add(rbStudy);
        radioPanel.add(rbPlacement);

        frame.add(radioPanel);

        // Relocate
        frame.add(new JLabel("Ready to Relocate"));

        toggleRelocate = new JToggleButton("No");

        toggleRelocate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(toggleRelocate.isSelected())
                    toggleRelocate.setText("Yes");
                else
                    toggleRelocate.setText("No");
            }
        });

        frame.add(toggleRelocate);

        // City
        frame.add(new JLabel("City"));

        String city[] = {"Ahmedabad","Surat","Vadodara","Rajkot"};

        comboCity = new JComboBox<>(city);

        frame.add(comboCity);

        // Button
        frame.add(new JLabel(""));

        btnSubmit = new JButton("Submit");
        frame.add(btnSubmit);

        // Submit Button Action
        btnSubmit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    String skills = "";

                    if(cbJava.isSelected())
                        skills += "Java ";

                    if(cbPython.isSelected())
                        skills += "Python ";

                    if(cbWeb.isSelected())
                        skills += "Web ";

                    String career = "";

                    if(rbStudy.isSelected())
                        career = "Further Study";
                    else if(rbPlacement.isSelected())
                        career = "Placement";

                    String relocate;

                    if(toggleRelocate.isSelected())
                        relocate = "Yes";
                    else
                        relocate = "No";

                    String sql = "INSERT INTO Student_Details VALUES(?,?,?,?,?,?)";

                    PreparedStatement pst = conn.prepareStatement(sql);

                    pst.setString(1, txtEnroll.getText());
                    pst.setString(2, txtName.getText());
                    pst.setString(3, skills);
                    pst.setString(4, career);
                    pst.setString(5, relocate);
                    pst.setString(6, comboCity.getSelectedItem().toString());

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(frame,
                            "Record Inserted Successfully");

                    pst.close();

                }
                catch(Exception ex) {

                    JOptionPane.showMessageDialog(frame,
                            ex.getMessage());

                }

            }

        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {

        new StudentInformation();

    }
}