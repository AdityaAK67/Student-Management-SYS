import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Student {
    String name;
    int rollNumber;
    String course;
    int totalMarks;

    public Student(String name, int rollNumber, String course, int totalMarks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.course = course;
        this.totalMarks = totalMarks;
    }

    @Override
    public String toString() {
        return name + "," + rollNumber + "," + course + "," + totalMarks;
    }
}

class Node {
    Student data;
    Node next;

    public Node(Student data) {
        this.data = data;
        this.next = null;
    }
}

class StudentRecordManagementSystem {
    private Node head;

    public DefaultTableModel getTableModel() {
        String[] columnNames = {"Name", "Roll Number", "Course", "Total Marks"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        Node current = head;
        while (current != null) {
            Student student = current.data;
            Object[] rowData = {student.name, student.rollNumber, student.course, student.totalMarks};
            model.addRow(rowData);
            current = current.next;
        }

        return model;
    }

    private boolean recordExists(int rollNumber) {
        Node current = head;
        while (current != null) {
            if (current.data.rollNumber == rollNumber) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void createRecord() {
        String name = JOptionPane.showInputDialog(null, "Enter student name:");
        int rollNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter roll number:"));
        String course = JOptionPane.showInputDialog(null, "Enter course:");
        int totalMarks = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter total marks:"));

        if (recordExists(rollNumber)) {
            JOptionPane.showMessageDialog(null, "Record with roll number " + rollNumber + " already exists.");
            return;
        }

        Student newStudent = new Student(name, rollNumber, course, totalMarks);
        Node newNode = new Node(newStudent);
        newNode.next = head;
        head = newNode;

        JOptionPane.showMessageDialog(null, "Record created successfully.");
    }

    public void searchRecord() {
        int rollNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter roll number to search:"));

        Node current = head;
        while (current != null) {
            if (current.data.rollNumber == rollNumber) {
                JOptionPane.showMessageDialog(null, "Record found:\n" + current.data);
                return;
            }
            current = current.next;
        }

        JOptionPane.showMessageDialog(null, "Record not found for roll number: " + rollNumber);
    }

    public void deleteRecord() {
        int rollNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter roll number to delete:"));

        Node current = head;
        Node prev = null;

        while (current != null) {
            if (current.data.rollNumber == rollNumber) {
                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
                JOptionPane.showMessageDialog(null, "Record deleted successfully.");
                return;
            }
            prev = current;
            current = current.next;
        }

        JOptionPane.showMessageDialog(null, "Record not found for roll number: " + rollNumber);
    }
}

public class StudentRecord extends JFrame {

    private StudentRecordManagementSystem recordSystem;
    private JTable table;

    public StudentRecord() {
        recordSystem = new StudentRecordManagementSystem();
        createGUI();
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Student Record Management System");
        setSize(600, 400);

        JPanel panel = new JPanel(new BorderLayout());

        // Create table and set data
        table = new JTable(recordSystem.getTableModel());
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JButton createButton = new JButton("Create Record");
        JButton searchButton = new JButton("Search Record");
        JButton deleteButton = new JButton("Delete Record");
        JButton exitButton = new JButton("Exit");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordSystem.createRecord();
                refreshTable();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordSystem.searchRecord();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordSystem.deleteRecord();
                refreshTable();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonsPanel.add(createButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(exitButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void refreshTable() {
        table.setModel(recordSystem.getTableModel());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentRecord();
            }
        });
    }
}
