// Programmer: Nate Sobol
// Title: Boola Boola University Registration
// Class: Advanced JAVA
// Instructor: Professor A. Richmond
// Last Modified: 10/25/16

package boolaboolaregistration;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;

public class mainMethod extends JFrame {
    public mainMethod(){
        JFrame window = new JFrame("Boola Boola University Registration");
        MarticReg matriOption = new MarticReg(window);
        StuDat newStuDat = new StuDat(window);
        TableData tableData = new TableData(window);
        
        window.setLayout(null);
        window.setVisible(true);
        window.setSize(600, 700);
        window.getContentPane().setBackground(Color.YELLOW); 
        newStuDat.setVisible(false);
        tableData.setVisible(false);
        matriOption.setVisible(false);
        
        // setup menubar
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);
        JMenu file = new JMenu("File");
        JMenu reg = new JMenu("Registration");
        JMenu admin = new JMenu("Admission");
        JMenu stu = new JMenu("Student Data");
        
        // Menu items
        JMenuItem nonMatric = new JMenuItem("Non-Marticulated Registration");
        JMenuItem matricReg = new JMenuItem("Matriculated Registration");      
        JMenuItem partTime = new JMenuItem("Part Time Student");
        JMenuItem fullTime = new JMenuItem("Full Time Student");
        JMenuItem nCredit = new JMenuItem("Noncredit");
        JMenuItem courseSchedule = new JMenuItem("Course Schedule");
        JMenuItem receive = new JMenuItem("Receivable");
        JMenuItem quit = new JMenuItem("Quit");
        JMenuItem test = new JMenuItem("Test DB Connection");
        
        // add menus
        menuBar.add(file);
        menuBar.add(admin);
        menuBar.add(reg);
        menuBar.add(stu);
        
        // add menu items
        file.add(quit);
        file.add(test);
        admin.add(nonMatric);
        admin.add(matricReg);
        admin.addSeparator();
        reg.add(partTime);
        reg.add(fullTime);
        reg.add(nCredit);
        stu.add(receive);
        stu.add(courseSchedule);
       
        // Menu Item action listeners
        nonMatric.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newStuDat.setVisible(false);
                tableData.setVisible(false);
                matriOption.setVisible(true);
            }
        });
        
        window.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowClosed(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowClosing(WindowEvent e) { quit.doClick(); }
        });
        
        matricReg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableData.setVisible(false);
                newStuDat.setVisible(false);
                matriOption.setVisible(true);
            }
        });

        fullTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                matriOption.setVisible(false);
                tableData.getDB(window);
                tableData.setVisible(true);
                newStuDat.setVisible(false);
            }
        });

        partTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                matriOption.setVisible(false);
                tableData.getDB(window);
                newStuDat.setVisible(false);
                tableData.setVisible(true);
            }
        });

        nCredit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                matriOption.setVisible(false);
                tableData.getDB(window);
                tableData.setVisible(true);
            }
        });

        receive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                matriOption.setVisible(false);
                newStuDat.setVisible(true);
                tableData.setVisible(false);
                newStuDat.defualt();
            }
        });

        courseSchedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {  
                newStuDat.setVisible(true);
                matriOption.setVisible(false);
                tableData.setVisible(false);
                newStuDat.defualt();
            }
        });
        
        test.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                matriOption.testDB(window);
            }
        });
        
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int exitConfirmation = JOptionPane.showOptionDialog(window, "Do you really want to quit?", "Terminate Program?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (exitConfirmation == 0) {
                    System.exit(0);
                }
            }
        });
    }
    public static void main(String[] args) { new mainMethod(); }
}
