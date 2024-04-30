import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class DashboardForm {
    public void dashboard() {
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        try {
            // Establishing connection with MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/talha", "root",
                    "talha");
            System.out.println("Connection established successfully.");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT product_code, product_name, catagory, quantity, price, description FROM products");

            // Create a table model and set a column names
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Product Code");
            model.addColumn("Product Name");
            model.addColumn("Catagory");
            model.addColumn("Quantity");
            model.addColumn("Price");
            model.addColumn("Description");

            // Populate the table model with data from the ResultSet
            while (resultSet.next()) {
                String productCode = resultSet.getString("product_code");
                String productName = resultSet.getString("product_name");
                String catagory = resultSet.getString("catagory");
                String quantity = resultSet.getString("quantity");
                String price = resultSet.getString("price");
                String description = resultSet.getString("description");
                model.addRow(new Object[] { productCode, productName, catagory, quantity, price, description });
            }

            // Create the table with the populated model
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);

            // Add delete button
            JButton deleteButton = new JButton("Delete Selected Record");
            JButton addButton = new JButton("Add New Record");
            JButton updateButton = new JButton("Update Record");
            JButton logoutButton = new JButton("Logout");

            deleteButton.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected
                    String productCodeToDelete = (String) table.getValueAt(selectedRow, 0);
                    try {
                        PreparedStatement preparedStatement = connection
                                .prepareStatement("DELETE FROM products WHERE product_code = ?");
                        preparedStatement.setString(1, productCodeToDelete);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(panel, "Record deleted successfully.");
                        model.removeRow(selectedRow); // Remove the row from the table
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(panel, "Error occurred while deleting record: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please select a row to delete.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            addButton.addActionListener(e -> {
                frame.dispose();
                AddProducts addProductForm = new AddProducts();
                addProductForm.addProducts();
            });

            updateButton.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected
                    String productCodeToUpdate = (String) table.getValueAt(selectedRow, 0);
                    String productNameToUpdate = (String) table.getValueAt(selectedRow, 1);
                    String catagoryToUpdate = (String) table.getValueAt(selectedRow, 2);
                    String quantityToUpdate = (String) table.getValueAt(selectedRow, 3);
                    String priceToUpdate = (String) table.getValueAt(selectedRow, 4);
                    String descriptionToUpdate = (String) table.getValueAt(selectedRow, 5);

                    frame.dispose();
                    UpdateForm updateProductForm = new UpdateForm();
                    updateProductForm.updateProducts(productCodeToUpdate, productNameToUpdate, catagoryToUpdate,
                            quantityToUpdate, priceToUpdate, descriptionToUpdate);
                } else {
                    JOptionPane.showMessageDialog(panel, "Please select a row to update.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            });

            logoutButton.addActionListener(e -> {
                frame.dispose();

                LoginForm.main(null);
            });
            // Create a separate panel for the buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(logoutButton);

            panel.add(buttonPanel, BorderLayout.SOUTH);

            frame.setContentPane(panel);
            frame.pack();

        } catch (Exception ex) {
            System.out.println("Connection Failed " + ex);
        }
    }

}
