// Programmer: Nate Sobol
// Last Modified: 10/25/16

package boolaboolaregistration;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class TableData implements ListSelectionListener {
    
    int selection = 0;
    final String driver = "com.mysql.jdbc.Driver";
    
    JLabel label = new JLabel();
    JLabel socialLabel = new JLabel("Social Securiy Number:");
    JLabel searchedStudentLabel = new JLabel();
    JLabel summaryLabel = new JLabel("Summary");
    JLabel creditsLabel = new JLabel("Credits:");
    JLabel costLabel = new JLabel("Cost:");
    JLabel totalcost = new JLabel("$ 0");
    JLabel totalcredits = new JLabel("0");
    JLabel infoLabel = new JLabel("Student Registration");
    JPanel panel = new JPanel();
    JTextField socialField = new JTextField();
    JButton searchButton = new JButton("Find Student");
    JButton registerButton = new JButton("Register");
    final String database = "jdbc:mysql://localhost:3306/boolabooladb?useSSL=false";
    final JList courseSelection = new JList();
    JScrollPane scrollPane = new JScrollPane(courseSelection);
    DefaultListModel dlm1 = new DefaultListModel();
    final String[] dd = new String[10];
    final String[] tt = new String[10];

    public TableData(JFrame window) {
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String altText = null;
                try {
                    Statement connectStatement;
                    Connection connect;
                    ResultSet result;
                    Class.forName(driver);
                    connect = DriverManager.getConnection(database, "natesobol", "password");
                    connectStatement = connect.createStatement();
                    result = connectStatement.executeQuery("SELECT fName, lName, str, cit, ssn, zip, state, dd, yy, degreeType FROM registerstudents");
                    while (result.next()) {
                        altText += result.getString(5);
                        if (altText.equals(socialField.getText())) {
                            searchedStudentLabel.setText(result.getString(1));
                            return;
                        }
                        altText = null;
                    }
                    JOptionPane.showMessageDialog(window, "No student found");
                    socialField.setText(null);
                    closeConnection(connect, connectStatement, result);
                } catch (ClassNotFoundException cnf) {
                    System.out.println(cnf.toString());
                } catch (SQLException dbErr) {
                    System.out.println(dbErr.toString());
                }
            }
        });

        label.setText
        (" Course #         "
                + "Name                          "
                + "Date          "
                + "Time          "
                + "Room #"
        );

        panel.add(scrollPane);
        summaryLabel.setBounds(140, 460, 85, 20);
        costLabel.setBounds(140, 480, 85, 20);
        totalcost.setBounds(180, 480, 85, 20);
        creditsLabel.setBounds(240, 480, 45, 20);
        totalcredits.setBounds(290, 480, 35, 20);
        searchButton.setBounds(410, 105, 150, 20);
        searchedStudentLabel.setBounds(200, 130, 210, 20);
        courseSelection.addListSelectionListener(this);
        infoLabel.setBounds(200, 40, 230, 20);
        socialField.setBounds(200, 105, 200, 20);
        socialLabel.setBounds(25, 105, 100, 20);
        label.setBounds(25, 175, 350, 10);
        scrollPane.setBounds(25, 200, 400, 250);
        registerButton.setBounds(220, 510, 130, 20);
        
        window.add(creditsLabel);
        window.add(summaryLabel);
        window.add(totalcost);
        window.add(totalcredits);
        window.add(infoLabel);
        window.add(scrollPane);
        window.add(label);
        window.add(socialLabel);
        window.add(socialField);
        window.add(searchButton);
        window.add(searchedStudentLabel);
        window.add(registerButton);
        window.add(costLabel);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int creditNum = Integer.parseInt(totalcredits.getText());
                String errorText = null;
                if (creditNum < 9) { errorText += "Full Time students need at least 9 credits!, "; }   
                else if (creditNum > 0) { errorText += "You can't select a course for credits!, "; }
                else if (creditNum > 6) { errorText += "Part Time students need less than 9 credits!, "; }
                else if (courseSelection.isSelectionEmpty()) { errorText += "You would have to choose\nat least one course\n"; }
                else if (socialField.getText().equals("")) { errorText += "Look for a student"; }
                else if (!errorText.equals("")) {
                    JOptionPane.showMessageDialog(window, "Please address the following errors: " + errorText);
                    return;
                }
                try {
                    Object[] dbinfo = courseSelection.getSelectedValues();
                    String[] iRegDay = new String[15];
                    int j = 3, lineNum = 0, getCourse = 0, setCourse = 0, setCourse2 = 0, getCourse2 = 0;
                    
                    Statement connectStatement;
                    Connection connect;
                    ResultSet result;
                    Class.forName(driver);
                    connect = DriverManager.getConnection(database, "natesobol", "password");
                    connectStatement = connect.createStatement();
                    result = connectStatement.executeQuery("SELECT ssn, dd, tt from studentlist");
                    result.beforeFirst();
                    while (result.next()) {
                        iRegDay[lineNum] = dd[courseSelection.getSelectedIndex()];
                        getCourse = Integer.parseInt(String.valueOf(tt[courseSelection.getSelectedIndex()].charAt(0)));
                        setCourse = Integer.parseInt(String.valueOf(tt[courseSelection.getSelectedIndex()].charAt(5)));
                        setCourse2 = Integer.parseInt(String.valueOf(result.getObject(j).toString().charAt(5)));
                        getCourse2 = Integer.parseInt(String.valueOf(result.getObject(j).toString().charAt(0)));
                        if (socialField.getText().equals(result.getObject(1))) {
                            if (result.getObject(2).toString().equals(iRegDay[lineNum])) {
                                if ((getCourse >= getCourse2 && getCourse < setCourse2) || (getCourse2 >= getCourse && getCourse2 < setCourse)) {
                                    JOptionPane.showMessageDialog(null, "This student has a class in this schedule try again" + errorText);
                                    return;
                                }
                            }
                        }
                        lineNum++;
                    }
                    for (int i = 0; i < dbinfo.length; i++) {
                        int[] getInfo = courseSelection.getSelectedIndices();
                        connectStatement.executeUpdate("INSERT INTO student(ssn, count, dd, tt)dbinfo('" + socialField.getText() + "','" + dbinfo[i].toString().subSequence(0, 7) + 
                                "','" + dd[getInfo[i]] + "','" + tt[getInfo[i]] + "')");
                    }
                    result.close();
                    connect.close();
                    connectStatement.close();
                } 
                catch (SQLException ti) {
                    System.out.println("SQL Exception: " + ti.toString());
                } 
                catch (ClassNotFoundException cE) {
                    System.out.println("Class Not Found Exception: " + cE.toString());
                }
                defualt();
                JOptionPane.showMessageDialog(window, "student has been regitsered!");

            }
        });
    }

    public void closeConnection(Connection connect, Statement cs, ResultSet result) throws SQLException{
        connect.close();
        cs.close();
        result.close();
    }
    
    public void valueChanged(ListSelectionEvent e) {
        JList source = (JList) e.getSource(); 
        String text; 
        Object[] values = source.getSelectedValues();
        int j = 3, creditL = 0, costL = 0;
        switch (selection) {
            case 1:
                for (Object value : values) {
                    text = (String) value;
                    if (text.charAt(0) != 'N') { creditL += j; }
                    costL = (creditL <= 9) ? 305 : 290;
                }
                costL *= creditL / j;
                break;
            case 2:
                for (Object value : values) {
                    text = (String) value;
                    if (text.charAt(0) != 'N') { creditL += j; }
                    costL += 400;
                }
                break;
            default:
                for (Object value : values) {
                    text = (String) value;
                    if (text.charAt(0) == 'N') { costL += 200; }
                }
                break;
        }
        totalcost.setText("$ " + Integer.toString(5 + costL));
        totalcredits.setText(Integer.toString(creditL));
    }

    public void setVisible(boolean isVisible) {
        scrollPane.setVisible(isVisible);
        searchedStudentLabel.setVisible(isVisible);
        registerButton.setVisible(isVisible);
        creditsLabel.setVisible(isVisible);
        costLabel.setVisible(isVisible);
        summaryLabel.setVisible(isVisible);
        totalcredits.setVisible(isVisible);
        totalcost.setVisible(isVisible);
        infoLabel.setVisible(isVisible);
        label.setVisible(isVisible);
        socialField.setVisible(isVisible);
        socialLabel.setVisible(isVisible);
        searchButton.setVisible(isVisible);
        
    }
    public void defualt() {
        socialField.setText("");
        totalcost.setText("$0.00");
        searchedStudentLabel.setText("");
    }
 
    public void getDB(JFrame window) {
        try {
            defualt();
            dlm1.clear();
            int column1, column2, 
                    s = 2, e = 3;
            Statement connectStatement, connectStatement2;
            ResultSet result, result2;
            Connection connect;

            Class.forName(driver);
            connect = DriverManager.getConnection(database, "natesobol", "password");
            connectStatement = connect.createStatement();
            connectStatement2 = connect.createStatement();
            result = connectStatement.executeQuery("SELECT Number, Name, Day, Timein, Room FROM courselist");
            result2 = connectStatement2.executeQuery("SELECT Number, Name, Day, Timein, Room FROM noncourselist");
            column1 = result.getMetaData().getColumnCount();
            column2 = result2.getMetaData().getColumnCount();

            String lVal = null;
            int k = 1, lineCount = 0;

            if (selection != e) {
                while (result.next()) {
                    for (int i = 1; i <= column1; i++) {
                        lVal += result.getObject(i) + " ";
                        if (i == 1) {
                            dd[lineCount] = (String) result.getObject(i + s);
                            tt[lineCount] = (String) result.getObject(i + e);
                        }
                        else if (k == s+e) {
                            dlm1.addElement(lVal);
                            lVal = null;
                            k = 1;
                            continue;
                        }
                        k++;
                    }
                    lineCount++;
                }
            }
            k = 1;lVal = "";
            if (selection == e) {
                while (result2.next()) {
                    for (int j = 1; j <= column2; j++) {
                        lVal += result2.getObject(j) + " ";
                        if (j == 1) {
                            dd[lineCount] = (String) result2.getObject(j + s);
                            tt[lineCount] = (String) result2.getObject(j + e);
                        }
                        else if (k == 4+1) {
                            dlm1.addElement(lVal);
                            lVal = null;
                            k = 1;
                            continue;
                        }
                        ++k;
                    }
                    lineCount++;
                }
            }
            courseSelection.setModel(dlm1);
            closeConnection(connect, connectStatement, result);
            connectStatement2.close();
            result2.close();
        } 
        catch (SQLException error) {
            JOptionPane.showMessageDialog(window, "Database Access Error! \n" + error.toString());
        } 
        catch (ClassNotFoundException cnf) {
            JOptionPane.showMessageDialog(window, cnf.toString());
        }
    }
}
