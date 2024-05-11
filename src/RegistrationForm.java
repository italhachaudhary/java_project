import javax.swing.*;
import java.sql.*;

public class RegistrationForm {
    public void register() {

        // JFrame
        JFrame frame = new JFrame("Registration Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);

        // Form
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField(20);
        JLabel usernameLabel = new JLabel("Username: ");
        JTextField userField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email: ");
        JTextField emailField = new JTextField(20);
        JLabel phonenoLabel = new JLabel("Phone: ");
        JTextField phoneField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password: ");
        JPasswordField passwordField = new JPasswordField(20);
        JButton btn = new JButton("Register");

        // JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(usernameLabel);
        panel.add(userField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(phonenoLabel);
        panel.add(phoneField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(btn);

        // Adding panel to JFrame
        frame.setContentPane(panel);
        frame.pack();

        // ActionListener
        btn.addActionListener(e -> {
            String name = nameField.getText();
            String username = userField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = new String(passwordField.getPassword());

            // Validation
            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all the fields");
                return;

            }

            else {
                try {
                    // Establishing connection with MySQL
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/talha", "root",
                            "talha");
                    System.out.println("Connection established successfully.");

                    // Inserting data into MySQL
                    String query = "INSERT INTO register (name, username, email, phoneno, password ) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, username);
                    statement.setString(3, email);
                    statement.setString(4, phone);
                    statement.setString(5, password);
                    statement.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "User registered successfully");
                    LoginForm.main(null);

                } catch (Exception ex) {
                    System.out.println("Connection Failed " + ex);
                }
            }
        });
    }
}