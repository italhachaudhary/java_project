import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RecordDeletion extends JFrame {
    private Connection connection;
    private JTable table;

    public RecordDeletion(Connection connection) {
        super("Record Deletion");
        this.connection = connection;

        JPanel panel = new JPanel(new BorderLayout());

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT userid, password FROM users");

            // Create a table model to hold the data
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("User ID");
            model.addColumn("Password");

            // Populate the table model with data from the ResultSet
            while (resultSet.next()) {
                String userId = resultSet.getString("userid");
                String password = resultSet.getString("password");
                model.addRow(new Object[] { userId, password });
            }

            // Create the table with the populated model
            table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);

            // Add delete button
            JButton deleteButton = new JButton("Delete Selected Record");
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                        String userIdToDelete = (String) table.getValueAt(selectedRow, 0);
                        deleteRecord(userIdToDelete);
                        model.removeRow(selectedRow); // Remove the row from the table
                    } else {
                        JOptionPane.showMessageDialog(RecordDeletion.this, "Please select a row to delete.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            panel.add(deleteButton, BorderLayout.SOUTH);

            add(panel);

            setSize(400, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the entire application when this frame is closed
            setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error occurred: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRecord(String userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE userid = ?");
            preparedStatement.setString(1, userId);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record deleted successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error occurred while deleting record: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/talha", "root", "talha");
            System.out.println("Connection established successfully.");

            new RecordDeletion(connection);
        } catch (Exception exception) {
            System.out.println("Connection failed: " + exception);
        }
    }
}