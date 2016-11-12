// Programmer: Nate Sobol
// Last Modified: 10/25/16

package boolaboolaregistration;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class StuDat {
    int selection = 0, f = 0;
    final String driver = "com.mysql.jdbc.Driver";
    
    // Components
    JLabel label = new JLabel();
    JLabel socialLabel = new JLabel("SSN:");
    JLabel searchedStudentLabel = new JLabel();
    JLabel summaryLabel = new JLabel(" ");
    JLabel creditsLabel = new JLabel("Credits:");
    JLabel costLabel = new JLabel("Cost: ");
    JLabel totalcost = new JLabel("$ 0");
    JLabel totalcredits = new JLabel("0");
    JLabel infoLabel = new JLabel("Student Information");
    JPanel panel = new JPanel();
    JLabel courses = new JLabel();
    JLabel totalLabel = new JLabel("Total: ");
    JTextField socialField = new JTextField();
    JButton searchButton = new JButton("Find Student");
    JButton registerButton = new JButton("Register");
    JLabel degreeType = new JLabel();
    JLabel yy = new JLabel();
    final JList cList = new JList();
    static final String database = "jdbc:mysql://localhost:3306/boolabooladb?useSSL=false";
    final JList courseSelection = new JList();
    JScrollPane scrollPane = new JScrollPane(courseSelection);
    DefaultListModel dlm1 = new DefaultListModel();
    DefaultListModel dlm2 = new DefaultListModel();
    
    public StuDat(JFrame window) {
        degreeType.setVisible(false); yy.setVisible(false);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int j = 2, d = 1, totalCostL = 0, costL = 0, course = 0, course2 = 0, numCourse = 0, numLine = 0;
                String nLine = null;
                String[] courseStr1 = new String[200]; String[] courseStr2 = new String[200];
                try {
                    String line = null;
                    Class.forName(driver);
                    
                    Connection connect = DriverManager.getConnection(database, "natesobol", "password");
                    Statement connectStatement = connect.createStatement();
                    ResultSet result = connectStatement.executeQuery("SELECT ssn, count, dd, tt FROM studentlist ORDER BY count ");
                    ResultSetMetaData mData = result.getMetaData();
                    int columnNumber = mData.getColumnCount();
                    dlm1.clear();result.beforeFirst();
                    
                    while (result.next()) {
                        if (!(result.getObject(2).toString().charAt(0) != 'N')) { numCourse += 150; } 
                        else { course2++; }
                        line = (String) result.getObject(d);
                        if (socialField.getText().equals(line)) {
                            line = null;
                            for (int i = j; i <= i; i++) { line += (String) result.getObject(i) + "    "; }
                            if (line.charAt(0) != 'N') { courseStr1[course++] = line; } 
                            else if (f == d){ line += "= $ 150.00"; }
                            courseStr2[numLine++] = line;
                            }
                            line = null;    
                        }
                   
                    for (int i = 0; i < courseStr1.length; i++) {
                        if (courseStr1[i] == null) { break; }
                        if (f == 1) { courseStr1[i] += "=$" + costL; }
                        dlm2.addElement(courseStr1[i]);
                    }

                    for (String courseStr21 : courseStr2) {
                        if (courseStr21 != null) { dlm2.addElement(courseStr21); }
                    }
                    
                    cList.setModel(dlm2);
                    totalCostL = 5+ course2* costL + numCourse; course2 *= j+1;
                    totalcost.setText(Integer.toString(totalCostL));
                    totalcredits.setText(Integer.toString(course2));
                    
                    try {
                        boolean doesExist = false;
                        String nextL = null;
                        
                        mData = result.getMetaData();
                        columnNumber = mData.getColumnCount();
                        Class.forName(driver);
                        connect = DriverManager.getConnection(database, "natesobol", "password");
                        connectStatement = connect.createStatement();
                        result = connectStatement.executeQuery("SELECT Name, SS, associate, yy FROM registerstudent");

                        while (result.next()) {
                            for (int i = 0; i < columnNumber; i++) {
                                nextL = null;
                                nextL = (String) result.getObject(i);
                                if (socialField.getText().equals(line)) {
                                    searchedStudentLabel.setText((String) result.getObject(i--));
                                    if (f == j) {
                                        degreeType.setText((String) result.getObject(i++));
                                        line = (String) result.getObject(i+j);
                                        if(line.equals("choose year")){ yy.setText(nLine); }
                                    }
                                    doesExist = true;
                                    return;
                                }
                            }
                        }
                        closeConnection(connect, connectStatement, result);
                    } 
                    catch (SQLException i) {
                    System.out.println("SQL Exception " + i);
                    } 
                    catch (ClassNotFoundException i) {
                    System.out.println("Class Not Found Exception " + i);
                    }
                    closeConnection(connect, connectStatement, result);
                } 
                catch (SQLException i) {
                    System.out.println("SQL Exception " + i);
                } 
                catch (ClassNotFoundException i) {
                    System.out.println("Class Not Found Exception " + i );
                }
            }
        });
        
        registerButton.setBounds(220, 510, 130, 20);
        summaryLabel.setBounds(140, 460, 85, 20);
        costLabel.setBounds(140, 480, 85, 20);
        courses.setBounds(25, 170, 350, 10);
        scrollPane.setBounds(25, 190, 390, 180);
        totalLabel.setBounds(140, 380, 85, 20);
        creditsLabel.setBounds(200, 380, 45, 20);
        infoLabel.setBounds(200, 40, 230, 20);
        socialField.setBounds(200, 105, 200, 20);
        socialLabel.setBounds(25, 105, 100, 20);
        searchButton.setBounds(410, 105, 150, 20);
        searchedStudentLabel.setBounds(200, 130, 210, 20);
        label.setBounds(25, 175, 350, 10);
        scrollPane.setBounds(25, 200, 400, 250);
        totalcost.setBounds(180, 480, 85, 20);
        creditsLabel.setBounds(240, 480, 45, 20);
        degreeType.setBounds(25, 142, 300, 20); 
        yy.setBounds(290, 165, 120, 20);
        
        panel.add(scrollPane);
        window.add(scrollPane);
        window.add(courses);
        window.add(socialLabel);
        window.add(socialField);
        window.add(searchButton);
        window.add(searchedStudentLabel);
        window.add(degreeType);
        window.add(yy);
        window.add(costLabel);
        window.add(creditsLabel);
        window.add(totalLabel);
        window.add(totalcost);
        window.add(totalcredits);
        window.add(infoLabel);
    }
    
    public void closeConnection(Connection connect, Statement cs, ResultSet result) throws SQLException{
        connect.close();
        cs.close();
        result.close();
    }
    
    public void setVisible(boolean isVisible) {
        scrollPane.setVisible(isVisible);
        creditsLabel.setVisible(isVisible);
        costLabel.setVisible(isVisible);
        totalLabel.setVisible(isVisible);
        totalcredits.setVisible(isVisible);
        totalcost.setVisible(isVisible);
        infoLabel.setVisible(isVisible);
        courses.setVisible(isVisible);
        socialField.setVisible(isVisible);
        socialLabel.setVisible(isVisible);
        searchButton.setVisible(isVisible);
        searchedStudentLabel.setVisible(isVisible);
    }

    public void defualt() {
        totalcost.setText(null);
        totalcredits.setText(null);
        searchedStudentLabel.setText(null);
        socialField.setText(null);
        cList.setModel(new DefaultListModel());
        searchButton.setVisible(true);
        degreeType.setText(null);
        yy.setText(null);
    }
}
