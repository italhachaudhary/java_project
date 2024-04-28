import javax.swing.*;

public class DashboardForm {
    public void dashboard() {
        // JFrame
        JFrame frame = new JFrame("Main Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);

    }
}
// // Form
// JButton btn = new JButton("Logout");

// // JPanel
// JPanel panel = new JPanel();
// panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
// panel.add(btn);

// // Adding panel to JFrame
// frame.setContentPane(panel);
// frame.pack();

// // ActionListener
// btn.addActionListener(e -> {
// frame.dispose();
// LoginForm.main(null);
// });