import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private JTextField txtFullName;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JFrame loginFrame;

    public RegisterFrame(JFrame loginFrame) {
        this.loginFrame = loginFrame;

        setTitle("Hospital Management System - Register");
        setSize(440, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(40, 167, 69)); // friendly green header
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Staff Registration");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Create a new hospital staff account");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(230, 255, 235));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(3));
        headerPanel.add(subtitleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30),
                BorderFactory.createLineBorder(new Color(225, 229, 235), 1)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblName = new JLabel("Full Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(lblName, gbc);

        gbc.gridy++;
        txtFullName = new JTextField(15);
        txtFullName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFullName.setPreferredSize(new Dimension(280, 32));
        formPanel.add(txtFullName, gbc);

        // Username
        gbc.gridy++;
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(lblUser, gbc);

        gbc.gridy++;
        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(280, 32));
        formPanel.add(txtUsername, gbc);

        // Password
        gbc.gridy++;
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(lblPass, gbc);

        gbc.gridy++;
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(280, 32));
        formPanel.add(txtPassword, gbc);

        // Confirm Password
        gbc.gridy++;
        JLabel lblConfirm = new JLabel("Confirm Password:");
        lblConfirm.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(lblConfirm, gbc);

        gbc.gridy++;
        txtConfirmPassword = new JPasswordField(15);
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtConfirmPassword.setPreferredSize(new Dimension(280, 32));
        formPanel.add(txtConfirmPassword, gbc);

        // Register Button
        gbc.gridy++;
        gbc.insets = new Insets(14, 10, 5, 10);
        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setBackground(new Color(40, 167, 69));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setPreferredSize(new Dimension(280, 36));
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnRegister, gbc);

        // Back Button
        gbc.gridy++;
        gbc.insets = new Insets(4, 10, 10, 10);
        JButton btnBack = new JButton("Back to Login");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnBack.setBackground(new Color(240, 243, 246));
        btnBack.setFocusPainted(false);
        btnBack.setPreferredSize(new Dimension(280, 32));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnBack, gbc);

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        centerWrapper.setBackground(new Color(245, 247, 250));
        centerWrapper.add(formPanel);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        add(mainPanel);

        // Actions
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToLogin();
            }
        });
    }

    private void handleRegister() {
        String fullName = txtFullName.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirm = new String(txtConfirmPassword.getPassword());

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match. Please re-enter.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 4 characters long.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = DataManager.registerUser(username, password, fullName);
        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Registration successful! You can now sign in with your credentials.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            goBackToLogin();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username already exists. Please choose a different username.",
                    "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBackToLogin() {
        if (loginFrame != null) {
            loginFrame.setVisible(true);
        } else {
            new LoginFrame().setVisible(true);
        }
        dispose();
    }
}
