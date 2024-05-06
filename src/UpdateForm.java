import javax.swing.*;
import java.sql.*;

public class UpdateForm {
    public void updateProducts(String productCodeToUpdate, String productNameToUpdate, String catagoryToUpdate,
            String quantityToUpdate, String priceToUpdate, String descriptionToUpdate) {

        JFrame frame = new JFrame("Update Product");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);

        // Form
        JLabel l1 = new JLabel("Product Code: ");
        JTextField tf1 = new JTextField(22);
        tf1.setEditable(false);
        tf1.setText(productCodeToUpdate);

        JLabel l2 = new JLabel("Product Name: ");
        JTextField tf2 = new JTextField(22);
        tf2.setText(productNameToUpdate);

        JLabel l3 = new JLabel("Catagory: ");
        JTextField tf3 = new JTextField(22);
        tf3.setText(catagoryToUpdate);

        JLabel l4 = new JLabel("Quantity: ");
        JTextField tf4 = new JTextField(22);
        tf4.setText(quantityToUpdate);

        JLabel l5 = new JLabel("Price: ");
        JTextField tf5 = new JTextField(22);
        tf5.setText(priceToUpdate);

        JLabel l6 = new JLabel("Description: ");
        JTextField tf6 = new JTextField(22);
        tf6.setText(descriptionToUpdate);

        JButton btn = new JButton("Update");

        // JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(l1);
        panel.add(tf1);
        panel.add(l2);
        panel.add(tf2);
        panel.add(l3);
        panel.add(tf3);
        panel.add(l4);
        panel.add(tf4);
        panel.add(l5);
        panel.add(tf5);
        panel.add(l6);
        panel.add(tf6);
        panel.add(btn);

        // Adding panel to JFrame
        frame.setContentPane(panel);
        frame.pack();

        // ActionListener
        btn.addActionListener(e -> {
            String productCode = tf1.getText();
            String productName = tf2.getText();
            String catagory = tf3.getText();
            String quantity = tf4.getText();
            String price = tf5.getText();
            String description = tf6.getText();

            try {
                // Establishing connection with MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/talha", "root",
                        "talha");
                System.out.println("Connection established successfully.");

                // Updating data in MySQL
                String query = "UPDATE products SET product_name = ?, catagory = ?, quantity = ?, price = ?, description = ? WHERE product_code = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, productName);
                statement.setString(2, catagory);
                statement.setString(3, quantity);
                statement.setString(4, price);
                statement.setString(5, description);
                statement.setString(6, productCode);
                statement.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Record updated successfully.");
                frame.dispose();
                DashboardForm db = new DashboardForm();
                db.dashboard();

            } catch (Exception ex) { 
                System.out.println("Connection Failed" + ex);
            }
        });

    }
}