package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup extends JPanel {
    private JTextField userField = new JTextField(20);
    private JPasswordField passField = new JPasswordField(20);
    private JPasswordField confirmPassField = new JPasswordField(20);
    private JButton registerButton = new JButton("REGISTER");
    private JButton backButton = new JButton("BACK TO LOGIN");
    private JLabel messageLabel = new JLabel("");
    private UserManager userManager;

    public Color c5 = new Color(0, 0, 0);
    public Color c6 = new Color(255, 215, 0);

    public Signup(JFrame frame, UserManager manager) {
        this.userManager = manager;
        setLayout(new GridBagLayout());
        setBackground(c5);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("CREATE ACCOUNT");
        titleLabel.setFont(new Font("Sans-serif", Font.BOLD, 40));
        titleLabel.setForeground(c6);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(c6);
        userLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        add(userLabel, gbc);

        userField.setFont(new Font("Sans-serif", Font.PLAIN, 20));
        gbc.gridx = 1; gbc.gridy = 1;
        add(userField, gbc);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(c6);
        passLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 2;
        add(passLabel, gbc);

        passField.setFont(new Font("Sans-serif", Font.PLAIN, 20));
        gbc.gridx = 1; gbc.gridy = 2;
        add(passField, gbc);

        // Confirm Password
        JLabel confirmLabel = new JLabel("Confirm Pass:");
        confirmLabel.setForeground(c6);
        confirmLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 3;
        add(confirmLabel, gbc);

        confirmPassField.setFont(new Font("Sans-serif", Font.PLAIN, 20));
        gbc.gridx = 1; gbc.gridy = 3;
        add(confirmPassField, gbc);

        // Buttons
        styleButton(registerButton);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(registerButton, gbc);

        styleButton(backButton);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(backButton, gbc);

        // Message Label
        messageLabel.setForeground(c6);
        messageLabel.setFont(new Font("Sans-serif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        add(messageLabel, gbc);

        // Action Listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String pass = new String(passField.getPassword());
                String confirmPass = new String(confirmPassField.getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    messageLabel.setText("Username and password cannot be empty.");
                    messageLabel.setForeground(Color.RED);
                    return;
                }

                if (!pass.equals(confirmPass)) {
                    messageLabel.setText("Passwords do not match.");
                    messageLabel.setForeground(Color.RED);
                    return;
                }

                if (userManager.registerUser(user, pass)) {
                    messageLabel.setText("Account created! Please go back to login.");
                    messageLabel.setForeground(Color.GREEN);
                } else {
                    messageLabel.setText("Username already exists.");
                    messageLabel.setForeground(Color.RED);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new Login(frame));
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Sans-serif", Font.BOLD, 20));
        btn.setForeground(c5);
        btn.setBackground(c6);
        btn.setFocusPainted(false);
    }
}
