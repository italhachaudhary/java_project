import javax.swing.*;
import java.sql.*;

public class AddProducts {
    public void addProducts() {
        // JFrame
        JFrame frame = new JFrame("Add Products");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);

        // Form
        JLabel PCLabel = new JLabel("Product Code: ");
        JTextField PCField = new JTextField(20);

        JLabel PNLabel = new JLabel("Product Name: ");
        JTextField PNField = new JTextField(20);

        JLabel CALabel = new JLabel("Catagory: ");
        JTextField CAField = new JTextField(20);

        JLabel QALabel = new JLabel("Quantity: ");
        JTextField QAField = new JTextField(20);

        JLabel PRLabel = new JLabel("Price(PKR): ");
        JTextField PRField = new JTextField(20);

        JLabel DELabel = new JLabel("Description: ");
        JTextField DEField = new JTextField(20);

        JButton btn = new JButton("Add Product");
        JButton clear = new JButton("Clear");
        JButton back = new JButton("Back");

        // JPanel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.add(PCLabel);
        formPanel.add(PCField);
        formPanel.add(PNLabel);
        formPanel.add(PNField);
        formPanel.add(CALabel);
        formPanel.add(CAField);
        formPanel.add(QALabel);
        formPanel.add(QAField);
        formPanel.add(PRLabel);
        formPanel.add(PRField);
        formPanel.add(DELabel);
        formPanel.add(DEField);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.add(btn);
        btnPanel.add(clear);
        btnPanel.add(back);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(formPanel);
        panel.add(btnPanel);

        // Adding panel to JFrame
        frame.setContentPane(panel);
        frame.pack();

        // ActionListener
        btn.addActionListener(e -> {
            String productCode = PCField.getText();
            String productName = PNField.getText();
            String catagory = CAField.getText();
            String quantity = QAField.getText();
            String price = PRField.getText();
            String description = DEField.getText();

            // Validation
            if (productCode.isEmpty() || productName.isEmpty() || catagory.isEmpty() || quantity.isEmpty()
                    || price.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all the fields");
                return;
            }

            // Database Connection
            else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/talha", "root",
                            "talha");
                    System.out.println("Connection established successfully.");

                    // Inserting data into database
                    String query = "INSERT INTO products (product_code, product_name, catagory, quantity, price, description) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, productCode);
                    statement.setString(2, productName);
                    statement.setString(3, catagory);
                    statement.setString(4, quantity);
                    statement.setString(5, price);
                    statement.setString(6, description);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Product added successfully");
                    connection.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        clear.addActionListener(e -> {
            PCField.setText("");
            PNField.setText("");
            CAField.setText("");
            QAField.setText("");
            PRField.setText("");
            DEField.setText("");
        });

        back.addActionListener(e -> {
            frame.dispose();
            DashboardForm dashboard = new DashboardForm();
            dashboard.dashboard();
        });

    }
}
