import javax.swing.*;
import java.awt.event.*;

// Strategy Interface
interface PaymentStrategy {
    String pay(int amount);
}

// Concrete Strategies
class CashPayment implements PaymentStrategy {
    public String pay(int amount) {
        return "Paid ₹" + amount + " using Cash";
    }
}

class CardPayment implements PaymentStrategy {
    public String pay(int amount) {
        return "Paid ₹" + amount + " using Card";
    }
}

class UpiPayment implements PaymentStrategy {
    public String pay(int amount) {
        return "Paid ₹" + amount + " using UPI";
    }
}

// Context Class
class Order {
    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public String placeOrder(int amount) {
        return strategy.pay(amount);
    }
}

// Main GUI Class
public class FoodDeliveryApp {

    JFrame frame;
    JComboBox<String> foodBox, paymentBox;
    JButton orderBtn;
    JLabel resultLabel;

    public FoodDeliveryApp() {

        frame = new JFrame("Food Delivery App");
        frame.setSize(350, 250);
        frame.setLayout(null);

        // Food selection
        foodBox = new JComboBox<>(new String[]{"Pizza - 200", "Burger - 100"});
        foodBox.setBounds(50, 30, 200, 30);
        frame.add(foodBox);

        // Payment selection
        paymentBox = new JComboBox<>(new String[]{"Cash", "Card", "UPI"});
        paymentBox.setBounds(50, 70, 200, 30);
        frame.add(paymentBox);

        // Button
        orderBtn = new JButton("Place Order");
        orderBtn.setBounds(80, 110, 150, 30);
        frame.add(orderBtn);

        // Result
        resultLabel = new JLabel("");
        resultLabel.setBounds(30, 150, 300, 30);
        frame.add(resultLabel);

        // Button Action
        orderBtn.addActionListener(e -> processOrder());

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void processOrder() {

        int amount = (foodBox.getSelectedIndex() == 0) ? 200 : 100;

        Order order = new Order();

        // Strategy selection
        String payment = (String) paymentBox.getSelectedItem();

        if (payment.equals("Cash"))
            order.setStrategy(new CashPayment());
        else if (payment.equals("Card"))
            order.setStrategy(new CardPayment());
        else
            order.setStrategy(new UpiPayment());

        resultLabel.setText("Processing...");

        // Multithreading (simulate delay)
        new Thread(() -> {
            try {
                Thread.sleep(2000); // simulate order processing

                String result = order.placeOrder(amount);

                // Update UI safely
                SwingUtilities.invokeLater(() -> {
                    resultLabel.setText(result);
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        new FoodDeliveryApp();
    }
}