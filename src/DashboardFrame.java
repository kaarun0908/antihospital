import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DashboardFrame extends JFrame {
    private String currentUser;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private JLabel lblTotalCount;

    // Form inputs
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtAge;
    private JComboBox<String> cmbGender;
    private JTextField txtDisease;
    private JComboBox<String> cmbDoctor;
    private JTextField txtPhone;
    private JTextField txtSearch;

    public DashboardFrame(String username) {
        this.currentUser = username;

        setTitle("Hospital Management System - Dashboard");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(850, 550));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // 1. Top Navigation Bar
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. Main Content (Left Form + Right Table)
        JPanel contentPanel = new JPanel(new BorderLayout(15, 10));
        contentPanel.setBackground(new Color(245, 247, 250));

        contentPanel.add(createFormPanel(), BorderLayout.WEST);
        contentPanel.add(createTablePanel(), BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Load initial records
        refreshPatientTable();
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(26, 115, 232));
        header.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        // Left title & hospital icon
        JLabel lblHospital = new JLabel("🏥 City Care Hospital - Management Portal");
        lblHospital.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHospital.setForeground(Color.WHITE);
        header.add(lblHospital, BorderLayout.WEST);

        // Right user info and logout
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);

        JLabel lblUser = new JLabel("👤 Logged in: " + currentUser);
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUser.setForeground(new Color(230, 242, 255));
        rightPanel.add(lblUser);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
        rightPanel.add(btnLogout);

        header.add(rightPanel, BorderLayout.EAST);
        return header;
    }

    private JPanel createFormPanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(320, 500));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 229, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblHeading = new JLabel("Admit New Patient");
        lblHeading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeading.setForeground(new Color(33, 37, 41));
        card.add(lblHeading, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridLayout(14, 1, 2, 4));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // ID
        fieldsPanel.add(new JLabel("Patient ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(240, 240, 240));
        fieldsPanel.add(txtId);

        // Name
        fieldsPanel.add(new JLabel("Full Name: *"));
        txtName = new JTextField();
        fieldsPanel.add(txtName);

        // Age
        fieldsPanel.add(new JLabel("Age: *"));
        txtAge = new JTextField();
        fieldsPanel.add(txtAge);

        // Gender
        fieldsPanel.add(new JLabel("Gender:"));
        cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        fieldsPanel.add(cmbGender);

        // Disease
        fieldsPanel.add(new JLabel("Disease / Diagnosis: *"));
        txtDisease = new JTextField();
        fieldsPanel.add(txtDisease);

        // Assigned Doctor
        fieldsPanel.add(new JLabel("Assigned Doctor:"));
        cmbDoctor = new JComboBox<>(new String[]{
                "Dr. Sarah Smith (General)",
                "Dr. Michael Lee (Cardiology)",
                "Dr. Priya Sharma (Pediatrics)",
                "Dr. David Kim (Neurology)",
                "Dr. Emily Chen (Orthopedics)"
        });
        fieldsPanel.add(cmbDoctor);

        // Phone
        fieldsPanel.add(new JLabel("Contact Phone:"));
        txtPhone = new JTextField();
        fieldsPanel.add(txtPhone);

        card.add(fieldsPanel, BorderLayout.CENTER);

        // Buttons
        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        actionPanel.setBackground(Color.WHITE);

        JButton btnAdd = new JButton("Admit Patient");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAdd.setBackground(new Color(40, 167, 69));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> handleAddPatient());

        JButton btnClear = new JButton("Clear Form");
        btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnClear.setBackground(new Color(240, 243, 246));
        btnClear.setFocusPainted(false);
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.addActionListener(e -> clearForm());

        actionPanel.add(btnAdd);
        actionPanel.add(btnClear);
        card.add(actionPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(new Color(245, 247, 250));

        // Top Filter Bar
        JPanel searchBar = new JPanel(new BorderLayout(10, 0));
        searchBar.setBackground(Color.WHITE);
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 229, 235), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JLabel lblSearch = new JLabel("🔍 Search Patients: ");
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchBar.add(lblSearch, BorderLayout.WEST);

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchBar.add(txtSearch, BorderLayout.CENTER);

        lblTotalCount = new JLabel("Total: 0");
        lblTotalCount.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotalCount.setForeground(new Color(26, 115, 232));
        searchBar.add(lblTotalCount, BorderLayout.EAST);

        panel.add(searchBar, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Full Name", "Age", "Gender", "Disease / Diagnosis", "Assigned Doctor", "Contact"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only cells
            }
        };

        patientTable = new JTable(tableModel);
        patientTable.setRowHeight(28);
        patientTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        patientTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        patientTable.getTableHeader().setBackground(new Color(235, 240, 245));
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.setShowGrid(true);
        patientTable.setGridColor(new Color(230, 235, 240));

        rowSorter = new TableRowSorter<>(tableModel);
        patientTable.setRowSorter(rowSorter);

        // Search live filter
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filter(); }

            private void filter() {
                String text = txtSearch.getText().trim();
                if (text.isEmpty()) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(225, 229, 235), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Actions
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomBar.setOpaque(false);

        JButton btnDelete = new JButton("Discharge / Delete Selected");
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> handleDeletePatient());

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRefresh.setBackground(new Color(240, 243, 246));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> refreshPatientTable());

        bottomBar.add(btnRefresh);
        bottomBar.add(btnDelete);
        panel.add(bottomBar, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshPatientTable() {
        tableModel.setRowCount(0);
        List<Patient> list = DataManager.getAllPatients();
        for (Patient p : list) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getAge(),
                    p.getGender(),
                    p.getDisease(),
                    p.getDoctor(),
                    p.getPhone()
            });
        }
        lblTotalCount.setText("Total: " + list.size());
        txtId.setText(DataManager.getNextPatientId());
    }

    private void handleAddPatient() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String age = txtAge.getText().trim();
        String gender = (String) cmbGender.getSelectedItem();
        String disease = txtDisease.getText().trim();
        String doctor = (String) cmbDoctor.getSelectedItem();
        String phone = txtPhone.getText().trim();

        if (name.isEmpty() || age.isEmpty() || disease.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields (Name, Age, Disease).",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int ageVal = Integer.parseInt(age);
            if (ageVal <= 0 || ageVal > 130) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age (1-130).", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Patient newPatient = new Patient(id, name, age, gender, disease, doctor, phone.isEmpty() ? "N/A" : phone);
        DataManager.addPatient(newPatient);

        JOptionPane.showMessageDialog(this,
                "Patient " + name + " (" + id + ") has been successfully admitted!",
                "Patient Admitted", JOptionPane.INFORMATION_MESSAGE);

        clearForm();
        refreshPatientTable();
    }

    private void handleDeletePatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a patient row from the table to discharge/delete.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = patientTable.convertRowIndexToModel(selectedRow);
        String patientId = (String) tableModel.getValueAt(modelRow, 0);
        String patientName = (String) tableModel.getValueAt(modelRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to discharge and remove record for:\n" + patientName + " (" + patientId + ")?",
                "Confirm Discharge",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            DataManager.deletePatient(patientId);
            refreshPatientTable();
            JOptionPane.showMessageDialog(this,
                    "Patient " + patientName + " discharged successfully.",
                    "Discharged", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtAge.setText("");
        txtDisease.setText("");
        txtPhone.setText("");
        cmbGender.setSelectedIndex(0);
        cmbDoctor.setSelectedIndex(0);
        txtId.setText(DataManager.getNextPatientId());
    }
}
