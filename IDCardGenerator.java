import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IDCardGenerator extends JFrame implements ActionListener {
    private JTextField fullNameField, idNumberField, addressField, departmentField, contactNumberField;
    private JButton generateButton, downloadButton, uploadImageButton;
    private ImageIcon personImage;

    public IDCardGenerator() {
        setTitle("ID Card Generator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 2, 10, 10));

        mainPanel.add(new JLabel("Full Name:"));
        fullNameField = new JTextField();
        mainPanel.add(fullNameField);

        mainPanel.add(new JLabel("ID Number:"));
        idNumberField = new JTextField();
        mainPanel.add(idNumberField);

        mainPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        mainPanel.add(addressField);

        mainPanel.add(new JLabel("Department:"));
        departmentField = new JTextField();
        mainPanel.add(departmentField);

        mainPanel.add(new JLabel("Contact Number:"));
        contactNumberField = new JTextField();
        mainPanel.add(contactNumberField);

        mainPanel.add(new JLabel("Upload Image:"));
        uploadImageButton = new JButton("Upload Image");
        uploadImageButton.addActionListener(this);
        mainPanel.add(uploadImageButton);

        generateButton = new JButton("Generate ID Card");
        generateButton.addActionListener(this);
        mainPanel.add(generateButton);

        downloadButton = new JButton("Download ID Card");
        downloadButton.addActionListener(this);
        downloadButton.setEnabled(false);
        mainPanel.add(downloadButton);

        add(mainPanel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            String fullName = fullNameField.getText();
            String idNumber = idNumberField.getText();
            String address = addressField.getText();
            String department = departmentField.getText();
            String contactNumber = contactNumberField.getText();

            if (!fullName.isEmpty() && !idNumber.isEmpty() && !address.isEmpty() && !department.isEmpty() && !contactNumber.isEmpty() && personImage != null) {
                // Generate ID Card
                String idCard = generateIDCard(fullName, idNumber, address, department, contactNumber);
                new IDCardDisplay(idCard).setVisible(true); // Open new frame to display ID card
                downloadButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter all details and upload an image.");
            }
        } else if (e.getSource() == uploadImageButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                personImage = new ImageIcon(selectedFile.getAbsolutePath());
            }
        } else if (e.getSource() == downloadButton) {
            // Download ID Card as text file
            String fullName = fullNameField.getText();
            String idNumber = idNumberField.getText();
            String address = addressField.getText();
            String department = departmentField.getText();
            String contactNumber = contactNumberField.getText();

            if (!fullName.isEmpty() && !idNumber.isEmpty() && !address.isEmpty() && !department.isEmpty() && !contactNumber.isEmpty() && personImage != null) {
                String idCardDetails = "Full Name: " + fullName + "\n" +
                                       "ID Number: " + idNumber + "\n" +
                                       "Address: " + address + "\n" +
                                       "Department: " + department + "\n" +
                                       "Contact Number: " + contactNumber;

                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                        writer.write(idCardDetails);
                        JOptionPane.showMessageDialog(this, "ID Card details saved successfully!");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error saving ID Card details!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please generate ID Card before downloading details.");
            }
        }
    }

    private String generateIDCard(String fullName, String idNumber, String address, String department, String contactNumber) {
        StringBuilder idCard = new StringBuilder();
        idCard.append("<h1>ID Card</h1>");
        idCard.append("<p><b>Full Name:</b> ").append(fullName).append("</p>");
        idCard.append("<p><b>ID Number:</b> ").append(idNumber).append("</p>");
        idCard.append("<p><b>Address:</b> ").append(address).append("</p>");
        idCard.append("<p><b>Department:</b> ").append(department).append("</p>");
        idCard.append("<p><b>Contact Number:</b> ").append(contactNumber).append("</p>");
        return idCard.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new IDCardGenerator().setVisible(true);
        });
    }
}

class IDCardDisplay extends JFrame {
    public IDCardDisplay(String idCard) {
        setTitle("Generated ID Card");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel idCardLabel = new JLabel("<html>" + idCard + "</html>");
        add(idCardLabel, BorderLayout.CENTER);
    }
}
