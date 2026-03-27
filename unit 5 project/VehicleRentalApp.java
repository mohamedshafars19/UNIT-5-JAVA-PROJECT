import javax.swing.*;
import java.awt.event.*;

// Interface
interface Vehicle {
    int getRate();
    String getName();
}

// Classes
class Car implements Vehicle {
    public int getRate() { return 1000; }
    public String getName() { return "Car"; }
}

class Bike implements Vehicle {
    public int getRate() { return 500; }
    public String getName() { return "Bike"; }
}

class Truck implements Vehicle {
    public int getRate() { return 2000; }
    public String getName() { return "Truck"; }
}

// Factory
class VehicleFactory {
    public static Vehicle getVehicle(String type) {

        if (type.equalsIgnoreCase("car"))
            return new Car();
        else if (type.equalsIgnoreCase("bike"))
            return new Bike();
        else if (type.equalsIgnoreCase("truck"))
            return new Truck();
        else
            return null;
    }
}

// Main GUI Class
public class VehicleRentalApp {

    JFrame frame;
    JComboBox<String> vehicleBox;
    JTextField daysField;
    JButton rentBtn;
    JLabel resultLabel;

    public VehicleRentalApp() {

        frame = new JFrame("Vehicle Rental System");
        frame.setSize(350, 250);
        frame.setLayout(null);

        // Vehicle selection
        vehicleBox = new JComboBox<>(new String[]{"Car", "Bike", "Truck"});
        vehicleBox.setBounds(50, 30, 200, 30);
        frame.add(vehicleBox);

        // Days input
        daysField = new JTextField();
        daysField.setBounds(50, 70, 200, 30);
        daysField.setBorder(BorderFactory.createTitledBorder("Days"));
        frame.add(daysField);

        // Button
        rentBtn = new JButton("Rent");
        rentBtn.setBounds(100, 110, 120, 30);
        frame.add(rentBtn);

        // Result
        resultLabel = new JLabel("");
        resultLabel.setBounds(30, 150, 300, 30);
        frame.add(resultLabel);

        rentBtn.addActionListener(e -> processRental());

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void processRental() {

        String type = (String) vehicleBox.getSelectedItem();
        Vehicle v = VehicleFactory.getVehicle(type);

        if (v == null) {
            resultLabel.setText("Invalid vehicle");
            return;
        }

        int days;
        try {
            days = Integer.parseInt(daysField.getText());
        } catch (Exception e) {
            resultLabel.setText("Enter valid days");
            return;
        }

        resultLabel.setText("Processing...");

        // Multithreading
        new Thread(() -> {
            try {
                Thread.sleep(2000); // simulate delay

                int total = v.getRate() * days;

                String result = "Total: ₹" + total + " (" + v.getName() + ")";

                SwingUtilities.invokeLater(() -> {
                    resultLabel.setText(result);
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        new VehicleRentalApp();
    }
}