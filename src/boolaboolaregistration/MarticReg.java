// Programmer: Nate Sobol
// Last Modified: 10/25/16

package boolaboolaregistration;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MarticReg {
    int isMatrix = 0;
    String driver = "com.mysql.jdbc.Driver";
    
    String Database = "jdbc:mysql://localhost:3306/bbu";
    JLabel instructLabel = new JLabel("Enter your personal information.");
    JLabel nameLabel = new JLabel("First Name:");
    JTextField nameField = new JTextField();
    JLabel lastreetLabel = new JLabel("Last Name:");
    JTextField lastField = new JTextField();
    JLabel socialLabel = new JLabel("Social Security:");
    JTextField socialField = new JTextField();
    JLabel streetLabel = new JLabel("Street address:");
    JTextField streetField = new JTextField();
    JLabel cityLabel = new JLabel("City:");
    JTextField cField = new JTextField();
    JLabel zipLabel = new JLabel("Zip:");
    JTextField zipField = new JTextField();
    JLabel stateLabel = new JLabel("State:");
    JLabel time = new JLabel();
    JLabel yearLabel = new JLabel("Year:");
    JPanel panel = new JPanel();
    JRadioButton immunizationCheck = new JRadioButton("Immunization");
    JRadioButton hiSchoolCheck = new JRadioButton("High School");
    JRadioButton ascs = new JRadioButton("AS degree in Computer Programming");
    JRadioButton aahu = new JRadioButton("AA degree in Humanities");
    final JButton applyButton = new JButton("Apply");
    JComboBox stateCombo = new JComboBox();
    JComboBox yearCombo = new JComboBox();
    String degreeType = "Non Marticulation";

    public MarticReg(JFrame window) {
        String DATE_FORMAT = "MM/dd/yyyy";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        window.setLayout(null);
        
        String[] yearstateList = {
            "Choose Year", 
            "Freshman(1)", 
            "Sophomore(2)", 
            "Junior(3)", 
            "Senior(4)"
        };
        
        String[] stateList = {
            "Choose State", 
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
            "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", 
            "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", 
            "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", 
            "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
        };

        instructLabel.setBounds(170, 35, 200, 20);
        nameLabel.setBounds(160, 82, 70, 10);
        nameField.setBounds(230, 80, 150, 20);
        lastreetLabel.setBounds(160, 112, 70, 10);
        lastField.setBounds(230, 110, 150, 20);
        socialLabel.setBounds(138, 142, 100, 10);
        socialField.setBounds(230, 140, 150, 20);
        streetLabel.setBounds(137, 202, 100, 15);
        streetField.setBounds(230, 200, 150, 20);
        cityLabel.setBounds(202, 172, 100, 15);
        cField.setBounds(230, 170, 150, 20);
        zipLabel.setBounds(207, 232, 100, 15);
        zipField.setBounds(230, 230, 70, 20);
        stateLabel.setBounds(192, 262, 100, 15);
        stateCombo.setBounds(230, 260, 120, 20);
        yearLabel.setBounds(210, 322, 100, 15);
        yearCombo.setBounds(250, 320, 100, 20);
        immunizationCheck.setBounds(140, 350, 120, 20);
        hiSchoolCheck.setBounds(260, 350, 100, 20);
        ascs.setBounds(140, 370, 250, 20);
        aahu.setBounds(140, 390, 250, 20);
        applyButton.setBounds(230, 420, 100, 20);

        window.add(nameLabel);
        window.add(nameField);
        window.add(lastreetLabel);
        window.add(lastField);
        window.add(streetLabel);
        window.add(streetField);
        window.add(cityLabel);
        window.add(socialLabel);
        window.add(socialField);
        window.add(cField);
        window.add(zipLabel);
        window.add(zipField);
        window.add(yearCombo);
        window.add(yearLabel);
        window.add(immunizationCheck);
        window.add(hiSchoolCheck);
        window.add(stateLabel);
        window.add(stateCombo);
        window.add(instructLabel);
        window.add(aahu);
        window.add(ascs);
        window.add(applyButton);

        for (String stateList1 : stateList) { stateCombo.addItem(stateList1); }
        for (String yearstateList1 : yearstateList) { yearCombo.addItem(yearstateList1); }
        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String errType = null;

                if (nameField.getText().isEmpty()) { errType += "First Name, "; }
                if (lastField.getText().isEmpty()) { errType += "Last Name, "; }
                if (streetField.getText().isEmpty()) { errType += "Street Address, "; }
                if (cField.getText().isEmpty()) { errType += "City, "; }
                if (socialField.getText().isEmpty()) { errType += "Social Security, "; }
                if (zipField.getText().isEmpty()) { errType += "Zip Code, "; }
                if (stateCombo.getSelectedItem().toString().equals("Choose State")) { errType += "choose a location, "; }
                if (!immunizationCheck.isSelected()) { errType += "select immunization, "; }
                
                switch (isMatrix) {
                    case 1:
                        if (yearCombo.getSelectedItem().toString().equals("Select Year")) { errType += "Choose a Year,"; }
                        if (!hiSchoolCheck.isSelected()) { errType += "Check High School diploma,"; }
                        if (ascs.isSelected() == false && aahu.isSelected() == false) { errType += "Choose an Associate,"; }
                }
                if (!(errType.length() == 0)) { JOptionPane.showMessageDialog(window, "Please Enter:\n" + errType); }
                addToDatabase(window);
            }
        });
        
        
        
        aahu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ascs.setSelected(false);
                aahu.setSelected(true);
                degreeType = aahu.getText();
            }
        });

        ascs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ascs.setSelected(true);
                aahu.setSelected(false);
                degreeType = ascs.getText();
            }
        });
        
    }
    
    public void testDB(JFrame window){
        Connection connect;
        try{
            connect = DriverManager.getConnection(Database, "natesobol", "password");
            JOptionPane.showMessageDialog(window, "Connected to DB!");
        } catch(SQLException dbErr){
            JOptionPane.showMessageDialog(window, "Could not connect to the database!");
        } 
    }
    
    public void setVisible(boolean isVisible) {
        instructLabel.setVisible(isVisible);
        yearCombo.setVisible(isVisible);
        yearLabel.setVisible(isVisible);
        hiSchoolCheck.setVisible(isVisible);
        immunizationCheck.setVisible(isVisible);
        aahu.setVisible(isVisible);
        ascs.setVisible(isVisible);
        applyButton.setVisible(isVisible);
        panel.setVisible(isVisible);
        nameLabel.setVisible(isVisible);
        nameField.setVisible(isVisible);
        lastreetLabel.setVisible(isVisible);
        lastField.setVisible(isVisible);
        streetLabel.setVisible(isVisible);
        streetField.setVisible(isVisible);
        socialLabel.setVisible(isVisible);
        socialField.setVisible(isVisible);
        cityLabel.setVisible(isVisible);
        cField.setVisible(isVisible);
        zipLabel.setVisible(isVisible);
        zipField.setVisible(isVisible);
        stateCombo.setVisible(isVisible);
        stateLabel.setVisible(isVisible);
    }
    
    
    public void addToDatabase(JFrame frame){
        try {
            Statement connectStatement;
            Connection connect;
            Class.forName(driver);
            connect = DriverManager.getConnection(Database, "natesobol", "password");
            connectStatement = connect.createStatement();
            connectStatement.executeUpdate("INSERT INTO regStudents(Name, lnLabel, Street, city, ss, zip, State, date, year, associate)VALUES('" + nameField.getText() + ", " + lastField.getText() + ", " + streetField.getText() + ", " + cField.getText() + ", " + socialField.getText() + ", " + zipField.getText() + ", " + stateCombo.getSelectedItem().toString() + ", "  + yearCombo.getSelectedItem().toString() + ", " + degreeType + "')");
            connect.close();
            connectStatement.close();
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf.toString());
        } catch (SQLException dbErr) {
            System.out.println(dbErr.toString());
        }
    }
    
    
}
